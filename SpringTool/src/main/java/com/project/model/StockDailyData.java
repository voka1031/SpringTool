package com.project.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;

import org.apache.commons.lang3.builder.ReflectionToStringBuilder;

@Entity
public class StockDailyData implements Serializable {
	private static final long serialVersionUID = 1L;

	@EmbeddedId
	private StockDailyDataPK id;

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
	@Column(name = "diff")
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

	public StockDailyData() {
	}

	public StockDailyData(String securityType, String tradeDate, String securityCode) {
		this.id = new StockDailyDataPK(securityType, tradeDate, securityCode);
	}

	public StockDailyDataPK getId() {
		return id;
	}

	public void setId(StockDailyDataPK id) {
		this.id = id;
	}

	public String getStockName() {
		return stockName;
	}

	public void setStockName(String stockName) {
		this.stockName = stockName;
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

	public String getTradeVolume() {
		return tradeVolume;
	}

	public void setTradeVolume(String tradeVolume) {
		this.tradeVolume = tradeVolume;
	}

	public String getDiff() {
		return diff;
	}

	public void setDiff(String diff) {
		this.diff = diff;
	}

}
