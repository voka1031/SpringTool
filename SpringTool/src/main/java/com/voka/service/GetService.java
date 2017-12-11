package com.voka.service;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.*;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.voka.annotation.CatchForAOP;
import com.voka.model.*;

@Service
public class GetService {

	@Autowired
	private PracticeDAO_interface dao;

	@Transactional(readOnly = true)
	public String getByGenderJson(String gender) {
		JsonArray array = new JsonArray();

		List<PracticeVO> list = gender.equals("1") ? getAll() : getByGender(gender);

		for (PracticeVO pVO : list) {
			JsonObject genderObj = new JsonObject();
			genderObj.addProperty("name", pVO.getName());
			genderObj.addProperty("id", pVO.getId());
			array.add(genderObj);
		}

		return new Gson().toJson(array);
	}

	@Transactional(readOnly = true)
	public List<PracticeVO> getByGender(String gender) {
		return dao.getByGender(gender);
	}

	@CatchForAOP
	@Transactional(readOnly = true)
	public void excelCheck(MultipartFile multipartFile) throws IOException {
		XSSFWorkbook workbook = null;
		try {
			workbook = new XSSFWorkbook(new BufferedInputStream(multipartFile.getInputStream()));
			XSSFSheet sheet = workbook.getSheetAt(0);
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
	public PracticeVO getOnePractice(Integer id) {
		return dao.findByPrimaryKey(id);
	}

	@CatchForAOP
	@Transactional(readOnly = true)
	public List<PracticeVO> getAll() {
		return dao.getAll();
	}

	@CatchForAOP
	@Transactional(readOnly = true)
	public List<PracticeVO> getDemoList() {
		return dao.getLambdaList();
	}

	@Transactional(readOnly = true)
	public List<PracticeVO> getPaging(Integer nthPage, Integer maxPerPage) {
		return dao.getPaging(nthPage, maxPerPage);
	}

	@CatchForAOP
	@Transactional(readOnly = true)
	public Integer getListSize() {
		return dao.getListSize();
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
			graphic.drawString(randomStr.substring(i , i + 1), 
					fontSize * i + random.nextInt(fontSize / 2),
					fontSize - 3 + random.nextInt(fontSize / 2));
		}

		graphic.dispose();
		return image;
	}
}
