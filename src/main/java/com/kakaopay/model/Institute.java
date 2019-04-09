package com.kakaopay.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class Institute {
	
	@Id
	@Column(columnDefinition = "varchar(3)")
	private String institute_code;
	
	@Column(columnDefinition = "varchar(40)")
	private String institute_name;

	public Institute() {
		
	}

	public Institute(String institute_code, String institute_name) {
		super();
		this.institute_code = institute_code;
		this.institute_name = institute_name;
	}

	public String getInstitute_code() {
		return institute_code;
	}

	public void setInstitute_code(String institute_code) {
		this.institute_code = institute_code;
	}

	public String getInstitute_name() {
		return institute_name;
	}

	public void setInstitute_name(String institute_name) {
		this.institute_name = institute_name;
	}
}
