package com.project.controller;

import java.io.IOException;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.project.model.IdWrapper;
import com.project.model.PracticeVO;
import com.project.service.GetService;
import com.project.service.TransService;

@Controller

@RequestMapping("/practice")
public class PracticeIdController {
	private static final Logger logger = LoggerFactory.getLogger(PracticeIdController.class);
	
	@Autowired
	private GetService getSvc;
	
	@Autowired
	private TransService tranSvc;
	
	@Value("${test.content}")
	private String CONTENT;
	
	@GetMapping
	public String entry(ModelMap model) throws IOException{
		logger.info("entry - "+ new java.util.Date());
		System.out.println(CONTENT);
		return "index";
	}
	
	@GetMapping("test")
	public String entranceOfTestPage(ModelMap model) throws IOException{
		return "test";
	}
	
	@GetMapping("tran1")
	public String testTran1(ModelMap model) throws IOException{
		tranSvc.transRequriedTest1();
		return "tran1";
	}
	
	@GetMapping("tran2")
	public String testTran2(ModelMap model) throws IOException{
		tranSvc.transRequriedTest2();
		return "tran2";
	}
	
	@RequestMapping(method = RequestMethod.POST, value = "getOne_For_Display_ajax")
	public String getOne_For_Display_ajax(@RequestParam String id, ModelMap model) {
		model.addAttribute("practiceVO", getSvc.getOnePractice(new Integer(id)));
		return "getOneAjax";
	}
	
	@RequestMapping(method = RequestMethod.POST, value = "getOne_For_Display")
	public String getOne_For_Display(@Valid @ModelAttribute IdWrapper idWrapper,
			BindingResult result, ModelMap model) {
		
		if(result.hasErrors())
			model.addAttribute("message", result.getFieldError().getDefaultMessage());
		
		else{
			PracticeVO pVO = getSvc.getOnePractice(new Integer(idWrapper.getId()));
			
			if (pVO == null)
				model.addAttribute("message", "查無資料");
			else
				model.addAttribute("practiceVO", pVO);
			
		}
		return "index";
	}
}
