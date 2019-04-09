package com.kakaopay.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.kakaopay.model.Institute;
import com.kakaopay.service.InstituteService;

@RestController
public class InstituteController {

	@Autowired
	private InstituteService instituteService;
	
	/**
	 * 		기본문제 2. 주택금융 공급 금융기관(은행) 목록을 출력하는 API 를 개발하세요.
	 * 		@return List<Institute>
	 */
	@GetMapping("/institutes")
	public List<Institute> getAllInstitute() {
		
		return instituteService.getAllInstitute();
	}
}
