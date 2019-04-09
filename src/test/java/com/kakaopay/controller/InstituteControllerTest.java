package com.kakaopay.controller;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.kakaopay.model.Institute;
import com.kakaopay.service.InstituteService;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class InstituteControllerTest {
	
	@Autowired
	private InstituteService instituteService;
	
	@Test
	public void getAllInstituteTest() {
		
		List<Institute> list = instituteService.getAllInstitute();
		
		assertThat(list).isNotNull();
		assertThat(list).size().isEqualTo(9);
	}
}