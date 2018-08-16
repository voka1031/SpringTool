package com.project.model;

import java.util.Date;

import org.apache.commons.lang3.builder.ReflectionToStringBuilder;

import com.fasterxml.jackson.annotation.JsonFormat;

public class TestResponse {

	@JsonFormat(pattern = "yyyy/MM/dd HH:mm",timezone = "GMT+8")
	private Date responseDate;

	public TestResponse() {
	}

	public TestResponse(Date responseDate) {
		this.responseDate = responseDate;
	}

	public Date getResponseDate() {
		return responseDate;
	}

	public void setResponseDate(Date responseDate) {
		this.responseDate = responseDate;
	}

	@Override
	public String toString() {
		return ReflectionToStringBuilder.toString(this);
	}

}
