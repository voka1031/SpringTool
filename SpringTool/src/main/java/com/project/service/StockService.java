package com.project.service;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.nio.channels.Channels;
import java.nio.channels.ReadableByteChannel;
import java.text.SimpleDateFormat;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.apache.commons.lang3.StringUtils;
import org.apache.http.NameValuePair;
import org.apache.http.client.utils.URLEncodedUtils;
import org.apache.http.message.BasicNameValuePair;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.project.model.stock.StockDailyDataInterface;
import com.project.model.stock.StockInfo;
import com.project.model.stock.StockInfoInterface;
import com.project.model.stock.TemplateStockData;
import com.project.util.MyDateUtils;

@Service
public class StockService {

	@Autowired
	private StockDailyDataInterface stockDailyDataRepo;

	@Autowired
	private StockInfoInterface stockInfoRepo;

	public static final String TWSE_URL = "http://www.twse.com.tw/exchangeReport/MI_INDEX?";

	public static final String[] typeArray = { "01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12",
			"13", "14", "15", "16", "17", "18", "20", "23", "0099P" };

	public void execDownload() throws InterruptedException {
		String date = new SimpleDateFormat("yyyyMMdd").format(new java.util.Date());
		for (String type : typeArray)
			downloadCSV(date, type);
	}

	private void downloadCSV(String date, String type) throws InterruptedException {
		System.out.printf("downloadCSV date : %s, type : %s ", date, type);

		List<NameValuePair> params = new LinkedList<NameValuePair>();
		params.add(new BasicNameValuePair("response", "csv"));
		params.add(new BasicNameValuePair("type", type));
		params.add(new BasicNameValuePair("date", date));

		String paramString = URLEncodedUtils.format(params, "UTF-8");
		String url = TWSE_URL + paramString;

		System.out.println("downloading : " + paramString);

		try {
			String csvFileUrl = "/Users/user/Downloads/csv/" + date + "_T" + type + ".csv";

			if (stockDailyDataRepo.isDataExistByTradeDate(type, date)) {
				System.out.println(csvFileUrl + "資料庫已有資料");
			} else {
				System.out.println(csvFileUrl + "資料庫無資料，開始下載");
				TimeUnit.SECONDS.sleep(5);
				downloadUsingNIO(url, csvFileUrl);
				saveData(new BufferedReader(new InputStreamReader(new FileInputStream(csvFileUrl), "Big5")), type);
			}

		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	private static void downloadUsingNIO(String urlStr, String localURL) throws IOException {
		URL url = new URL(urlStr);
		ReadableByteChannel rbc = Channels.newChannel(url.openStream());
		FileOutputStream fos = new FileOutputStream(localURL);
		fos.getChannel().transferFrom(rbc, 0, Long.MAX_VALUE);
		fos.close();
		rbc.close();
	}

	private void saveData(BufferedReader br, String type) throws IOException {

		String line = "";
		String csvSplitBy = "\",\"";
		String tradeDate = "";
		TemplateStockData data = null;

		while ((line = br.readLine()) != null) {
			String[] stock = getTrim(line.split(csvSplitBy));
			if (stock.length == 1 && StringUtils.contains(stock[0], "每日收盤行情") && StringUtils.isEmpty(tradeDate)) {
				tradeDate = MyDateUtils.getEDateString(StringUtils.substringBefore(stock[0], "每"));
			} else if (!StringUtils.equals("證券代號", stock[0]) && stock.length == 16
					&& StringUtils.isNotEmpty(tradeDate)) {
				data = new TemplateStockData(type, tradeDate, getTrim2(stock[0]));
				data.setStockName(stock[1]);
				data.setTradeVolume(stock[2]);
				data.setTransaction(stock[3]);
				data.setTradeValue(stock[4]);
				data.setOpeningPrice(stock[5]);
				data.setHighestPrice(stock[6]);
				data.setLowestPrice(stock[7]);
				data.setClosingPrice(stock[8]);
				data.setDir(stock[9]);
				data.setDiff(stock[10]);
				data.setLastBestBidPrice(stock[11]);
				data.setLastBestBidVolume(stock[12]);
				data.setLastBestAskPrice(stock[13]);
				data.setLastBestAskVolume(stock[14]);
				data.setPriceEarningRatio(stock[15]);
				String typeClass = stockInfoRepo.getClass(type).getName();
				System.out
						.printf("insert in action - typeClass : %s, securityCode : %s ", typeClass, getTrim2(stock[0]))
						.println();
				stockDailyDataRepo.insert(typeClass, data);
			}
		}
		System.out.println("downloading type = " + type + " finished");
	}

	private static String[] getTrim(String[] strArray) {
		String[] newStrArr = new String[strArray.length];
		for (int i = 0; i < strArray.length; i++)
			newStrArr[i] = strArray[i].replaceAll("[,\"]", "");
		return newStrArr;
	}

	private static String getTrim2(String str) {
		return str.replaceAll("[=\"]", "");
	}

	public void csvCheck(MultipartFile multipartFile) {
		try (BufferedReader br = new BufferedReader(new InputStreamReader(multipartFile.getInputStream(), "Big5"))) {
			saveData(br, "24");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public List<? extends TemplateStockData> getStock(String securityCode, String startDate, String endDate) {
		return stockDailyDataRepo.getBySecurityCode(securityCode, startDate, endDate);
	}
	
	public List<? extends TemplateStockData> getStockWithCountdays(String securityCode, String startDate, String endDate, int countdays) {
		return stockDailyDataRepo.getBySecurityCodeAndCountdays(securityCode, startDate, endDate, countdays);
	}

	public StockInfo getStockInfo(String securityCode) {
		return stockInfoRepo.getStockInfo(securityCode);
	}

}
