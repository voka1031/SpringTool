package com.voka.service;

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
}
