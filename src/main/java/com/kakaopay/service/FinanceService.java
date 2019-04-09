package com.kakaopay.service;

import java.util.List;
import java.util.Map;

import com.kakaopay.dto.AvgAmountDTO;
import com.kakaopay.dto.SumAmountDTO;

public interface FinanceService {
	
	/**
	 * 주택금융 공급 리스트 데이터베이스 저장
	 * @param list
	 * @return Map<String, String>
	 */
	public Map<String, String> createFinanceData(List<String[]> list);
	
	/**
	 * 년도별 각 금융기관의 지원금액 합계 조회
	 * @return List<SumAmountDTO>
	 */
	public List<SumAmountDTO> getSumAmountByYear();
	
	/**
	 * 
	 * 전체 지원금액 중에서 가장 큰 금액의 기관 조회
	 * @return Map<String, String>
	 */
	public Map<String, String> getMaxAmountByYear();
	
	/**
	 * 금융기관 평균 지원금액 최소값, 최대값 조회
	 * @param instituteCode
	 * @return MinMaxAmountDTO
	 */
	public AvgAmountDTO getAvgAmountByYear(String instituteCode);
}
