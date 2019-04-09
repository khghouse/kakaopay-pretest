package com.kakaopay.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.kakaopay.dto.AvgAmountDTO;
import com.kakaopay.dto.SumAmountDTO;
import com.kakaopay.model.Finance;
import com.kakaopay.model.Institute;
import com.kakaopay.repository.FinanceRepository;

@Service
public class FinanceServiceImpl implements FinanceService {
	
	@Autowired
	private FinanceRepository financeRepository;
	
	@Autowired
	private InstituteService instituteService;
	
	public Map<String, String> createFinanceData(List<String[]> list) {
		
		Map<String, String> resultMap = new HashMap<String, String>();
		
		// 금융기관 목록
		List<Finance> financeList = financeRepository.findAll();
		
		if(financeList.size() == 0) {
			
			List<Institute> instituteList = instituteService.getAllInstitute();
			
			// 년도, 월, 기관코드, 지원금액 구조로 데이터 등록
			for(String[] str : list) {
				for(int j = 0 ; j < instituteList.size() ; j++) {
					financeRepository.save(new Finance(Integer.parseInt(str[0]), Integer.parseInt(str[1]), instituteList.get(j), Integer.parseInt(str[j+2].replace(",", ""))));
				}
			}
			resultMap.put("resultMsg", "데이터가 등록되었습니다.");
		} else {
			resultMap.put("resultMsg", "이미 데이터가 등록되어있습니다.");
		}
		
		return resultMap;
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public List<SumAmountDTO> getSumAmountByYear() {
		
		List<Map<String, Integer>> list = financeRepository.getSumAmountByYear();
		List<SumAmountDTO> returnList = new ArrayList<SumAmountDTO>();
		
		// 출력 결과에 맞게 데이터 매핑
		for(Map<String, Integer> map : list) {
			
			List<Map<String, Integer>> detailList = new ArrayList<Map<String, Integer>>();
			
			Map tmpMap = new LinkedHashMap<String, Integer>();
			tmpMap.put("주택도시기금", map.get("HCF"));
			tmpMap.put("국민은행", map.get("KB"));
			tmpMap.put("우리은행", map.get("WR"));
			tmpMap.put("신한은행", map.get("SH"));
			tmpMap.put("한국시티은행", map.get("CT"));
			tmpMap.put("하나은행", map.get("HN"));
			tmpMap.put("농협은행/수협은행", map.get("NH"));
			tmpMap.put("외환은행", map.get("KEB"));
			tmpMap.put("기타은행", map.get("ETC"));
			
			detailList.add(tmpMap);
			
			SumAmountDTO dto = new SumAmountDTO(map.get("YEAR"), map.get("TOTAL_AMOUNT"), detailList);
			returnList.add(dto);
		}
		
		return returnList;
	}
	
	public Map<String, String> getMaxAmountByYear() {
		
		Map<String, String> map = financeRepository.getMaxAmountByYear();
		return map;
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public AvgAmountDTO getAvgAmountByYear(String instituteCode) {
		
		List<Map<String, String>> list = financeRepository.getAvgAmountByYear(instituteCode);
		List<Map<String, String>> supportList = new ArrayList<Map<String, String>>();
		
		// 출력 결과에 맞게 데이터 매핑
		Map tmpMap = new LinkedHashMap<String, Integer>();
		tmpMap.put("YEAR", list.get(0).get("YEAR"));
		tmpMap.put("AMOUNT", list.get(0).get("AMOUNT"));
		supportList.add(tmpMap);
		
		tmpMap = new LinkedHashMap<String, Integer>();
		tmpMap.put("YEAR", list.get(list.size()-1).get("YEAR"));
		tmpMap.put("AMOUNT", list.get(list.size()-1).get("AMOUNT"));
		supportList.add(tmpMap);
		
		AvgAmountDTO dto = new AvgAmountDTO(list.get(0).get("BANK"), supportList);
		
		return dto;
	}
}
