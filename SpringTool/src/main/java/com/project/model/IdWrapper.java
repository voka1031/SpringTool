package com.project.model;

import javax.validation.constraints.Digits;

public class IdWrapper {
	@Digits(integer=4, fraction=0, message="輸入編號需為{integer}位數整數")
	private String id;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}
}
