package com.project.model.stock;

import java.util.List;

public interface StockDailyDataInterface {
	public void insert(String entityName, TemplateStockData data);

	public List<? extends TemplateStockData> getBySecurityCode(String securityCode, String startDate, String endDate);
	
	public boolean isDataExistByTradeDate(String type, String tradeDate);
}
