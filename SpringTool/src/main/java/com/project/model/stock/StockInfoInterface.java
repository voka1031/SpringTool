package com.project.model.stock;

public interface StockInfoInterface {

	public String getSecurityType(String securityCode);

	Class<?> getTypeClass(String securityCode);

	Class<?> getClass(String securityType);

}
