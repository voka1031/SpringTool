package com.project.service;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.BufferedInputStream;
import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Random;

import org.apache.commons.lang3.StringUtils;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.project.annotation.CatchForAOP;
import com.project.model.Customer;
import com.project.model.CustomerInterface;
import com.project.model.stock.TemplateStockData;
import com.project.util.MyDateUtils;

@Service
public class GetService {

	@Autowired
	private CustomerInterface customerRepo;

	@Autowired
	private StockService stockService;

	@Transactional(readOnly = true)
	public String getByGenderJson(String gender) {
		JsonArray array = new JsonArray();

		List<Customer> list = gender.equals("1") ? getAll() : getByGender(gender);

		for (Customer pVO : list) {
			JsonObject genderObj = new JsonObject();
			genderObj.addProperty("name", pVO.getName());
			genderObj.addProperty("id", pVO.getId());
			array.add(genderObj);
		}

		return new Gson().toJson(array);
	}

	@Transactional(readOnly = true)
	public List<Customer> getByGender(String gender) {
		return customerRepo.getByGender(gender);
	}

	@CatchForAOP
	@Transactional(readOnly = true)
	public void excelCheck(MultipartFile multipartFile) throws IOException {
		XSSFWorkbook workbook = null;
		try {
			workbook = new XSSFWorkbook(new BufferedInputStream(multipartFile.getInputStream()));
			XSSFSheet sheet = workbook.getSheetAt(0);
			System.out.println("sheet" + sheet);
			Iterator<Row> iterator = sheet.iterator();
			while (iterator.hasNext()) {
				Row row = iterator.next();
				System.out.println(
						"row.number : " + row.getRowNum() + ", row.getLastCellNum() : " + row.getLastCellNum());
				for (int i = 0; i < row.getLastCellNum(); i++)
					System.out.print(row.getCell(i) + ", ");
				System.out.println();
			}
		} finally {
			if (!(workbook == null))
				workbook.close();
		}
	}

	@CatchForAOP
	@Transactional(readOnly = true)
	public Customer getOnePractice(Integer id) {
		return customerRepo.findByPrimaryKey(id);
	}

	@CatchForAOP
	@Transactional(readOnly = true)
	public List<Customer> getAll() {
		return customerRepo.getAll();
	}

	@CatchForAOP
	@Transactional(readOnly = true)
	public List<Customer> getDemoList() {
		return customerRepo.getLambdaList();
	}

	@Transactional(readOnly = true)
	public List<Customer> getPaging(Integer nthPage, Integer maxPerPage) {
		return customerRepo.getPaging(nthPage, maxPerPage);
	}

	@Transactional(readOnly = true)
	public Integer getListSize() {
		return customerRepo.getListSize();
	}

	@CatchForAOP
	@Transactional(readOnly = true)
	public BufferedImage getCaptcha(String randomStr) {
		Random random = new Random();

		int fontSize = 20;
		int imgWidth = randomStr.length() * (fontSize);
		int imgHeight = 30;

		BufferedImage image = new BufferedImage(imgWidth, imgHeight, BufferedImage.TYPE_INT_RGB);// 創建圖像
		Graphics graphic = image.getGraphics(); // 獲取圖形上下文

		// 設定背景色
		graphic.setColor(new Color(random.nextInt(155) + 100, random.nextInt(155) + 100, random.nextInt(155) + 100));
		graphic.fillRect(0, 0, imgWidth, imgHeight);

		// 設定字體及大小
		graphic.setFont(new Font("Times New Roman", Font.BOLD, fontSize));

		// 隨機產生155條干擾線，使圖像中的認證碼不易被其它程序探測到
		for (int i = 0; i < 155; i++) {
			graphic.setColor(
					new Color(random.nextInt(155) + 100, random.nextInt(155) + 100, random.nextInt(155) + 100));
			int x = random.nextInt(imgWidth);
			int y = random.nextInt(imgHeight);
			int xl = random.nextInt(12);
			int yl = random.nextInt(12);
			graphic.drawLine(x, y, x + xl, y + yl);
		}

		// 將認證碼顯示到圖像中
		for (int i = 0; i < randomStr.length(); i++) {
			graphic.setColor(new Color(random.nextInt(123), random.nextInt(123), random.nextInt(123)));
			graphic.drawString(randomStr.substring(i, i + 1),
					fontSize * i + random.nextInt(fontSize / 2),
					fontSize - 3 + random.nextInt(fontSize / 2));
		}

		graphic.dispose();
		return image;
	}

	public List<List<Map<Object, Object>>> getStock(String securityCode, String startDate, String endDate) throws ParseException {

		if (StringUtils.isBlank(securityCode))
			securityCode = "2002";
		
		Map<Object, Object> map = null;
		List<List<Map<Object, Object>>> list = new ArrayList<List<Map<Object, Object>>>();
		List<Map<Object, Object>> dataPoints1 = new ArrayList<Map<Object, Object>>();

		@SuppressWarnings("unchecked")
		List<TemplateStockData> dataList = (List<TemplateStockData>) stockService.getStock(securityCode, startDate, endDate);

		for (TemplateStockData data : dataList) {
			
			Double[] yData;

			try {
				yData = new Double[] { Double.parseDouble(data.getOpeningPrice()),
						Double.parseDouble(data.getHighestPrice()),
						Double.parseDouble(data.getLowestPrice()),
						Double.parseDouble(data.getClosingPrice()) };

				map = new HashMap<>();
				map.put("x", MyDateUtils.sdf.parse(data.getTradeDate()).getTime());
				map.put("y", yData);
				dataPoints1.add(map);

			} catch (NumberFormatException e) {
			}

		}
		list.add(dataPoints1);
		return list;
	}


}
