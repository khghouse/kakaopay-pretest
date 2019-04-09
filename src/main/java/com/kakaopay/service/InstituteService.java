package com.kakaopay.service;

import java.util.List;

import com.kakaopay.model.Institute;

public interface InstituteService {
	
	/**
	 * 금융기관 리스트 조회
	 * @return List<Institute>
	 */
	public List<Institute> getAllInstitute();
}
