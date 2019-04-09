package com.kakaopay.controller;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.kakaopay.common.util.ConvertUtil;
import com.kakaopay.dto.AvgAmountDTO;
import com.kakaopay.dto.SumAmountDTO;
import com.kakaopay.service.FinanceService;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class FinanceControllerTest {
	
	@Autowired
	private FinanceService financeService;
	
	@Autowired
	private ConvertUtil convertUtil;
	
	@Before
	public void initData() throws Exception {
		
		List<String[]> list = convertUtil.convertCSV("2019경력공채_개발_사전과제3_주택금융신용보증_금융기관별_공급현황.csv", "UTF-8");
		
		financeService.createFinanceData(list);
	}
	
	@Test
	public void getSumAmountByYearTest() {

		List<SumAmountDTO> list = financeService.getSumAmountByYear();
		
		assertThat(list).isNotNull();
		assertThat(list).size().isEqualTo(13);
	}
	
	@Test
	public void getMaxAmountByYearTest() {
		
		Map<String, String> map = financeService.getMaxAmountByYear();
		
		assertThat(map).isNotNull();
		assertThat(map).containsEntry("YEAR", "2014");
		// assertThat(map).containsEntry("BANK", "주택도시기금");
	}
	
	@Test
	public void getAvgAmountByYearTest() {
		
		AvgAmountDTO dto = financeService.getAvgAmountByYear("keb");
		
		List<LinkedHashMap<String, String>> expectedList = new ArrayList<LinkedHashMap<String, String>>();
		LinkedHashMap<String, String> tmpMap = new LinkedHashMap<String, String>();
		tmpMap.put("YEAR", "2017");
		tmpMap.put("AMOUNT", "0");
		expectedList.add(tmpMap);
		tmpMap = new LinkedHashMap<String, String>();
		tmpMap.put("YEAR", "2015");
		tmpMap.put("AMOUNT", "1702");
		expectedList.add(tmpMap);
		
		assertThat(dto).isNotNull();
		assertThat(dto).hasFieldOrPropertyWithValue("supportAmount", expectedList);
	}
}
