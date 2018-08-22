package com.project.model;

import java.io.Serializable;

import javax.persistence.Embeddable;

import org.apache.commons.lang3.builder.ReflectionToStringBuilder;

@Embeddable
public class StockDailyDataPK implements Serializable {
	private static final long serialVersionUID = 1L;

	// 產業別
	private String securityType;

	// 交易日期
	private String tradeDate;

	// 證券代號
	private String securityCode;

	public StockDailyDataPK() {
	}

	public StockDailyDataPK(String securityType, String tradeDate, String securityCode) {
		this.securityType = securityType;
		this.tradeDate = tradeDate;
		this.securityCode = securityCode;
	}

	public String getSecurityType() {
		return securityType;
	}

	public void setSecurityType(String securityType) {
		this.securityType = securityType;
	}

	public String getTradeDate() {
		return tradeDate;
	}

	public void setTradeDate(String tradeDate) {
		this.tradeDate = tradeDate;
	}

	public String getSecurityCode() {
		return securityCode;
	}

	public void setSecurityCode(String securityCode) {
		this.securityCode = securityCode;
	}

	@Override
	public String toString() {
		return ReflectionToStringBuilder.toString(this);
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((securityCode == null) ? 0 : securityCode.hashCode());
		result = prime * result + ((securityType == null) ? 0 : securityType.hashCode());
		result = prime * result + ((tradeDate == null) ? 0 : tradeDate.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		StockDailyDataPK other = (StockDailyDataPK) obj;
		if (securityCode == null) {
			if (other.securityCode != null)
				return false;
		} else if (!securityCode.equals(other.securityCode))
			return false;
		if (securityType == null) {
			if (other.securityType != null)
				return false;
		} else if (!securityType.equals(other.securityType))
			return false;
		if (tradeDate == null) {
			if (other.tradeDate != null)
				return false;
		} else if (!tradeDate.equals(other.tradeDate))
			return false;
		return true;
	}

}
