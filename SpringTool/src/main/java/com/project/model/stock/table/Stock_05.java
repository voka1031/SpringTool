package com.project.model.stock.table;

import javax.persistence.Entity;

import org.apache.commons.lang3.builder.ReflectionToStringBuilder;

import com.project.model.stock.TemplateStockData;

@Entity
public class Stock_05 extends TemplateStockData {

	private static final long serialVersionUID = 1L;

	public Stock_05() {
	}

	public Stock_05(String securityType, String tradeDate, String securityCode) {
		super(securityType, tradeDate, securityCode);
	}

	@Override
	public String toString() {
		return ReflectionToStringBuilder.toString(this);
	}

}