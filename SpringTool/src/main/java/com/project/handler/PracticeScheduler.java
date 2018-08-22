package com.project.handler;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.project.service.GetService;
import com.project.service.StockService;

@Component("routineJob")
public class PracticeScheduler {
	private static final Logger logger = LoggerFactory.getLogger(PracticeScheduler.class);
	
	@Autowired
	private GetService service;
	
	@Autowired
	private StockService stockService;
	
	protected void doJob(){
		logger.info("排程 - 目前時間  : " + new java.util.Date());	
		logger.info("排程 - 目前人數 : " + service.getListSize());	
	}
	
	protected void dbUpdate(){
		logger.info("排程 - 資料庫更新 : ");	
//		stockService.execDownload();
//		logger.info("finshed download  : " + new java.util.Date());	
	}
	
}
