package com.kakaopay.dto;

import java.util.List;
import java.util.Map;

public class AvgAmountDTO {

	private String bank;
	private List<Map<String, String>> supportAmount;
	
	public AvgAmountDTO() {

	}
	
	public AvgAmountDTO(String bank, List<Map<String, String>> supportAmount) {
		super();
		this.bank = bank;
		this.supportAmount = supportAmount;
	}

	public String getBank() {
		return bank;
	}

	public List<Map<String, String>> getSupportAmount() {
		return supportAmount;
	}
}
