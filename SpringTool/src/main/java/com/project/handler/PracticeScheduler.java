package com.project.handler;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.project.service.GetService;

@Component("routineJob")
public class PracticeScheduler {
	private static final Logger logger = LoggerFactory.getLogger(PracticeScheduler.class);
	
	@Autowired
	private GetService service;
	
	protected void doJob(){
		logger.info("排程測試 - 目前時間  : " + new java.util.Date());	
	}
	
	protected void doJob_2(){
		logger.info("排程測試 - 目前人數 : " + service.getListSize());	
	}
	
}
