package com.project.handler;

import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.*;

@ControllerAdvice
public class PracticeExceptionHandler {
	
	@ExceptionHandler(NoHandlerFoundException.class)
	public ModelAndView p404_Exception(Exception ex) {
		ModelAndView model = new ModelAndView("errorPage");
		model.addObject("errMsg", "找不到網頁");
		return model;
	}
	
	@ExceptionHandler(Exception.class)
	public ModelAndView handleAllException(Exception ex) {
		ModelAndView model = new ModelAndView("errorPage");
		model.addObject("errMsg", ExceptionUtils.getStackTrace(ex));
		return model;
	}
}
