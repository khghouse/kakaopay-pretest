package com.kakaopay.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.kakaopay.common.util.ConvertUtil;
import com.kakaopay.dto.AvgAmountDTO;
import com.kakaopay.dto.SumAmountDTO;
import com.kakaopay.service.FinanceService;

@RestController
@RequestMapping("/finance")
public class FinanceController {
	
	@Autowired
	private FinanceService financeService;
	
	@Autowired
	private ConvertUtil convertUtil;
	
	/**
	 * 		기본문제 1. 데이터 파일에서 각 레코드를 데이터베이스에 저장하는 API 개발
	 * 		@return Map<String, String>
	 */
	@PostMapping("/init/data")
	@ResponseBody
	public Map<String, String> createFinalceData() {
		
		// CSV 파일을 파싱한 리스트 객체
		List<String[]> list = convertUtil.convertCSV("2019경력공채_개발_사전과제3_주택금융신용보증_금융기관별_공급현황.csv", "UTF-8");
		
		return financeService.createFinanceData(list);
	}
	
	/**
	 * 		기본문제 3. 년도별 각 금융기관의 지원금액 합계를 출력하는 API 를 개발하세요.
	 * 		@return List<SumAmountDTO>
	 */
	@GetMapping("/year/sum")
	@ResponseBody
	public List<SumAmountDTO> getSumAmountByYear() {
		
		List<SumAmountDTO> list = financeService.getSumAmountByYear();
		
		return list;
	}
	
	/**
	 * 		기본문제 4. 각 년도별 각 기관의 전체 지원금액 중에서 가장 큰 금액의 기관명을 출력하는 API 개발
	 * 		@return Map<String, String>
	 */
	@GetMapping("/year/max")
	@ResponseBody
	public Map<String, String> getMaxAmountByYear() {
		
		Map<String, String> map = financeService.getMaxAmountByYear();
		
		return map;
	}
	
	/**
	 * 		기본문제 5. 전체 년도(2005~2016)에서 외환은행의 지원금액 평균 중에서 가장 작은 금액과 큰 금액을 출력하는 API 개발
	 * 		@param instituteCode
	 * 		@return MinMaxAmountDTO
	 */
	@GetMapping("/year/avg/{institute_code}")
	@ResponseBody
	public AvgAmountDTO getAvgAmountByYear(@PathVariable(value="institute_code") String instituteCode) {
		
		AvgAmountDTO dto = financeService.getAvgAmountByYear(instituteCode);
		
		return dto;
	}
}
