package com.project.model.stock;

import java.io.Serializable;

import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

import org.apache.commons.lang3.builder.ReflectionToStringBuilder;

@MappedSuperclass
public class TemplateStockData implements Serializable {

	private static final long serialVersionUID = 1L;

	// 產業別
	@Id
	private String securityType;

	// 交易日期
	@Id
	private String tradeDate;

	// 證券代號
	@Id
	private String securityCode;

	// 證券名稱
	private String stockName;

	// 成交股數
	private String tradeVolume;

	// 成交筆數
	private String transaction;

	// 成交金額
	private String tradeValue;

	// 開盤價
	private String openingPrice;

	// 最高價
	private String highestPrice;

	// 最低價
	private String lowestPrice;

	// 收盤價
	private String closingPrice;

	// 漲跌(+/-)
	private String dir;

	// 漲跌價差
	private String diff;

	// 最後揭示買價
	private String lastBestBidPrice;

	// 最後揭示買量
	private String lastBestBidVolume;

	// 最後揭示賣價
	private String lastBestAskPrice;

	// 最後揭示賣量
	private String lastBestAskVolume;

	// 本益比
	private String priceEarningRatio;

	public TemplateStockData() {
	}

	public TemplateStockData(String securityType, String tradeDate, String securityCode) {
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

	public String getStockName() {
		return stockName;
	}

	public void setStockName(String stockName) {
		this.stockName = stockName;
	}

	public String getTradeVolume() {
		return tradeVolume;
	}

	public void setTradeVolume(String tradeVolume) {
		this.tradeVolume = tradeVolume;
	}

	public String getTransaction() {
		return transaction;
	}

	public void setTransaction(String transaction) {
		this.transaction = transaction;
	}

	public String getTradeValue() {
		return tradeValue;
	}

	public void setTradeValue(String tradeValue) {
		this.tradeValue = tradeValue;
	}

	public String getOpeningPrice() {
		return openingPrice;
	}

	public void setOpeningPrice(String openingPrice) {
		this.openingPrice = openingPrice;
	}

	public String getHighestPrice() {
		return highestPrice;
	}

	public void setHighestPrice(String highestPrice) {
		this.highestPrice = highestPrice;
	}

	public String getLowestPrice() {
		return lowestPrice;
	}

	public void setLowestPrice(String lowestPrice) {
		this.lowestPrice = lowestPrice;
	}

	public String getClosingPrice() {
		return closingPrice;
	}

	public void setClosingPrice(String closingPrice) {
		this.closingPrice = closingPrice;
	}

	public String getDir() {
		return dir;
	}

	public void setDir(String dir) {
		this.dir = dir;
	}

	public String getDiff() {
		return diff;
	}

	public void setDiff(String diff) {
		this.diff = diff;
	}

	public String getLastBestBidPrice() {
		return lastBestBidPrice;
	}

	public void setLastBestBidPrice(String lastBestBidPrice) {
		this.lastBestBidPrice = lastBestBidPrice;
	}

	public String getLastBestBidVolume() {
		return lastBestBidVolume;
	}

	public void setLastBestBidVolume(String lastBestBidVolume) {
		this.lastBestBidVolume = lastBestBidVolume;
	}

	public String getLastBestAskPrice() {
		return lastBestAskPrice;
	}

	public void setLastBestAskPrice(String lastBestAskPrice) {
		this.lastBestAskPrice = lastBestAskPrice;
	}

	public String getLastBestAskVolume() {
		return lastBestAskVolume;
	}

	public void setLastBestAskVolume(String lastBestAskVolume) {
		this.lastBestAskVolume = lastBestAskVolume;
	}

	public String getPriceEarningRatio() {
		return priceEarningRatio;
	}

	public void setPriceEarningRatio(String priceEarningRatio) {
		this.priceEarningRatio = priceEarningRatio;
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
		TemplateStockData other = (TemplateStockData) obj;
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
