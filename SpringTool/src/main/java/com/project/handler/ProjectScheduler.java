package com.project.handler;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.project.service.GetService;
import com.project.service.StockService;

@Component("routineJob")
public class ProjectScheduler {
	private static final Logger logger = LoggerFactory.getLogger(ProjectScheduler.class);
	
	@Autowired
	private GetService getService;
	
	@Autowired
	private StockService stockService;
	
	protected void doJob() {
		logger.info("排程 - 目前人數 : {}", getService.getListSize());
	}

	protected void dbUpdate() throws InterruptedException {
		logger.info("排程 - 資料庫更新");
		stockService.execDownload();
		logger.info("排程 - 資料庫更新完成");
	}
	
	protected void doCheck() throws InterruptedException {
		logger.info("排程 - 檢查");
	}
	
}
