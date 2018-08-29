package com.project.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.commons.lang3.StringUtils;

public class MyDateUtils {
	
	/**
	 * SimpleDateFormat yyyyMMdd
	 */
	public static SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");

	/**
	 * @param TWDate YYY年mm月dd日(民國年)
	 * @return yyyymmdd
	 */
	public static String getEDateString(String TWDate) {
		String waitToReplaced = StringUtils.substringBefore(TWDate, "年");
		String wantToReplace = String.valueOf((Integer.parseInt(waitToReplaced) + 1911));
		TWDate = StringUtils.replace(TWDate, waitToReplaced, wantToReplace);
		TWDate = StringUtils.replaceAll(TWDate, "[年月]", "");
		TWDate = StringUtils.substringBefore(TWDate, "日");
		return TWDate;
	}
	
	public static Date getDate(String EDate) throws ParseException {
		return sdf.parse(EDate);
	}
	
	public static void main(String[] args) throws ParseException {
	}

}
