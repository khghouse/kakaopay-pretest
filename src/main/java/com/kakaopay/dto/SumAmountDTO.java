package com.kakaopay.dto;

import java.util.List;
import java.util.Map;

public class SumAmountDTO {
	
	private int year;
	private int totalAmount;
	private List<Map<String, Integer>> detailAmount;
	
	public SumAmountDTO() {
		
	}
	
	public SumAmountDTO(int year, int totalAmount, List<Map<String, Integer>> detailAmount) {
		super();
		this.year = year;
		this.totalAmount = totalAmount;
		this.detailAmount = detailAmount;
	}
	
	public int getYear() {
		return year;
	}
	
	public int getTotalAmount() {
		return totalAmount;
	}
	
	public List<Map<String, Integer>> getDetailAmount() {
		return detailAmount;
	}
}
