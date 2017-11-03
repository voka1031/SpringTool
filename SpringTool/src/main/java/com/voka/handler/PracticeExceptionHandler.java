package com.voka.handler;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.*;

@ControllerAdvice
public class PracticeExceptionHandler {
	
	@ExceptionHandler(NoHandlerFoundException.class)
	public ModelAndView p404_Exception(Exception ex) {
		System.out.println("in p404 @PracticeExceptionHandler");
		ModelAndView model = new ModelAndView("errPage");
		model.addObject("errMsg", "找不到網頁");
		return model;
	}
	
/*	@ExceptionHandler(Exception.class)
	public ModelAndView handleAllException(Exception ex) {
		System.out.println("in @PracticeExceptionHandler");
		ModelAndView model = new ModelAndView("errPage");
		model.addObject("errMsg", ex.getClass());
		return model;
	}*/
}
