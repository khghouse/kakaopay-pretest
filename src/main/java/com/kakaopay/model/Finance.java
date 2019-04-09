package com.kakaopay.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity
public class Finance {
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Long seq;
	private int year;
	private int month;
	@ManyToOne
	@JoinColumn(name="institute_code")
	private Institute institute;
	private int amount;
	
	public Finance() {

	}

	public Finance(int year, int month, Institute institute, int amount) {
		super();
		this.year = year;
		this.month = month;
		this.institute = institute;
		this.amount = amount;
	}

	public Long getSeq() {
		return seq;
	}

	public void setSeq(Long seq) {
		this.seq = seq;
	}

	public int getYear() {
		return year;
	}

	public void setYear(int year) {
		this.year = year;
	}

	public int getMonth() {
		return month;
	}

	public void setMonth(int month) {
		this.month = month;
	}

	public Institute getInstitute() {
		return institute;
	}

	public void setInstitute(Institute institute) {
		this.institute = institute;
	}

	public int getAmount() {
		return amount;
	}

	public void setAmount(int amount) {
		this.amount = amount;
	}
}
