package com.project.model;

import java.util.List;

public interface StockDailyDataInterface {
	public void insert(StockDailyData data);

	public List<StockDailyData> getBySecurityCode(String securityCode, String startDate, String endDate);
}
