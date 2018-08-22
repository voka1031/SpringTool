package com.project.service;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.nio.channels.Channels;
import java.nio.channels.ReadableByteChannel;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.apache.http.NameValuePair;
import org.apache.http.client.utils.URLEncodedUtils;
import org.apache.http.message.BasicNameValuePair;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.project.model.StockDailyData;
import com.project.model.StockDailyDataInterface;
import com.project.util.MyDateUtils;

@Service
public class StockService {

	@Autowired
	private StockDailyDataInterface stockDailyDataRepo;

	public static final String TWSE_URL = "http://www.twse.com.tw/exchangeReport/MI_INDEX?";

	public static final String[] typeArr = { "01", "02", "03", "04", "05", "06", "07", "08", "09", "10",
			"11", "12", "13", "14", "15", "16", "17", "18", "19", "20",
			"21", "22", "23", "24", "25", "26", "27", "28", "29", "30" };

	public void execDownload() {
		String date = new SimpleDateFormat("yyyyMMdd").format(new Date());
		List<NameValuePair> params;
		File file;
		for (String type : typeArr) {

			params = new LinkedList<NameValuePair>();
			params.add(new BasicNameValuePair("response", "csv"));
			params.add(new BasicNameValuePair("type", type));
			params.add(new BasicNameValuePair("date", date));

			String paramString = URLEncodedUtils.format(params, "UTF-8");
			String url = TWSE_URL + paramString;

			System.out.println(paramString);
			System.out.println("downloading : " + paramString);
			try {
				String csvFile = "/Users/user/Downloads/csv/" + date + "_T" + type + ".csv";
				file = new File(csvFile);

				if (!file.exists()) {
					downloadUsingNIO(url, csvFile);
				} else {
					System.out.println(csvFile + "已存在");
				}
				saveData(new BufferedReader(new InputStreamReader(new FileInputStream(csvFile), "Big5")), type);
				System.out.println("downloading : " + paramString + " finished");
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	private static void downloadUsingNIO(String urlStr, String file) throws IOException {
		URL url = new URL(urlStr);
		ReadableByteChannel rbc = Channels.newChannel(url.openStream());
		FileOutputStream fos = new FileOutputStream(file);
		fos.getChannel().transferFrom(rbc, 0, Long.MAX_VALUE);
		fos.close();
		rbc.close();
	}

	private void saveData(BufferedReader br, String type) throws IOException {

		String line = "";
		String csvSplitBy = "\",\"";
		String tradeDate = "";
		StockDailyData data = null;

		while ((line = br.readLine()) != null) {
			String[] stock = getTrim(line.split(csvSplitBy));
			if (stock.length == 1 && StringUtils.contains(stock[0], "每日收盤行情") && StringUtils.isEmpty(tradeDate)) {
				tradeDate = MyDateUtils.getEDATE(StringUtils.substringBefore(stock[0], "每"));
			} else if (!StringUtils.equals("證券代號", stock[0])
					&& stock.length == 16
					&& StringUtils.isNotEmpty(tradeDate)) {
				data = new StockDailyData(type, tradeDate, stock[0]);
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
				stockDailyDataRepo.insert(data);
			}
		}
	}

	private static String[] getTrim(String[] strArray) {
		String[] newStrArr = new String[strArray.length];
		for (int i = 0; i < strArray.length; i++)
			newStrArr[i] = strArray[i].replaceAll("[,\"]", "");
		return newStrArr;
	}

	public void csvCheck(MultipartFile multipartFile) {
		try (BufferedReader br = new BufferedReader(new InputStreamReader(multipartFile.getInputStream(), "Big5"))) {
			saveData(br, "24");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public List<StockDailyData> getStock(String securityCode, String startDate, String endDate) {
		System.out.println("getStock");
		List<StockDailyData> list = stockDailyDataRepo.getBySecurityCode(securityCode, startDate, endDate);
		for (StockDailyData data : list) {
			System.out.printf("date : %s, id : %s, highPrice : %s, lowPrice : %s, OpenPrice : %s, ClosePrice : %s",
					data.getId().getTradeDate(), data.getId().getSecurityCode(),
					data.getHighestPrice(), data.getLowestPrice(),
					data.getOpeningPrice(), data.getClosingPrice()).println();
		}

		return list;

	}

}
