package com.project.model.stock;

import javax.persistence.Entity;

import org.apache.commons.lang3.builder.ReflectionToStringBuilder;

@Entity
public class StockDailyData extends TemplateStockData {

	private static final long serialVersionUID = 1L;

	public StockDailyData() {
	}

	public StockDailyData(String securityType, String tradeDate, String securityCode) {
		super(securityType, tradeDate, securityCode);
	}

	@Override
	public String toString() {
		return ReflectionToStringBuilder.toString(this);
	}

}
