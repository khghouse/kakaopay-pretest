package com.kakaopay.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.kakaopay.model.Institute;
import com.kakaopay.repository.InstituteRepository;

@Service
public class InstituteServiceImpl implements InstituteService {
	
	@Autowired
	private InstituteRepository instituteRepository;
	
	public List<Institute> getAllInstitute() {
		
		return instituteRepository.findAll();
	}
}
