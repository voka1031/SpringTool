package com.project.constant;

import java.util.*;
import org.json.JSONObject;

public class Constants {

	public static final String CONTENT_TYPE_EXCEL_XLSX = "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet";
	
	// 錯誤訊息
	public static final String QUERY_ATTRIBUTE_ERROR_MESSAGE = "errMsg";
	public static final String QUERY_ATTRIBUTE_ERROR_MESSAGE_CONTENT = "參數更新失敗";

	// 性別
	public static final String GENDER_MAP = "genderMap";
	public static final String GENDER_MAP_KEY = "genderMapKey";
	public static Map<String, String> GENDER_MAP_CONTENT;
	static {
		GENDER_MAP_CONTENT = new LinkedHashMap<String, String>();
		GENDER_MAP_CONTENT.put("M", "男");
		GENDER_MAP_CONTENT.put("F", "女");
		GENDER_MAP_CONTENT = Collections.unmodifiableMap(GENDER_MAP_CONTENT);
	}

	// JSON test
	public static Map<String, String> TEST_MAP_CONTENT;
	static {
		TEST_MAP_CONTENT = new HashMap<String, String>();
		TEST_MAP_CONTENT.put("aa", "台北");
		TEST_MAP_CONTENT.put("bb", "台中");
		TEST_MAP_CONTENT.put("cc", "高雄");
	}
	public static final String TEST_JSON_MAP_NAME = "testJsonMap";
	public static JSONObject TEST_JSON_MAP_CONTENT = new JSONObject(TEST_MAP_CONTENT);

}
