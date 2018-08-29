package com.project.model.stock;

import java.util.List;

public interface StockDailyDataInterface {
	public void insert(String entityName, TemplateStockData data);

	public List<? extends TemplateStockData> getStock(String securityCode, String startDate, String endDate);

	public List<? extends TemplateStockData> getMA(String securityCode, String startDate, String endDate, int countdays);

	public boolean isDataExistByTradeDate(String type, String tradeDate);
}
