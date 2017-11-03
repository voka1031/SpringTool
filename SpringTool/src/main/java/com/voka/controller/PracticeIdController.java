package com.voka.controller;

import java.io.IOException;
import javax.validation.*;
import org.slf4j.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import com.voka.model.*;
import com.voka.service.GetService;

@Controller

@RequestMapping("/practice")
public class PracticeIdController {
	private static final Logger logger = LoggerFactory.getLogger(PracticeIdController.class);
	
	@Autowired
	private GetService getSvc;
	
	@GetMapping
	public String entry(ModelMap model) throws IOException{
		logger.info("entry - "+ new java.util.Date());
		return "index";
	}
	
	@GetMapping("test")
	public String entranceOfTestPage(ModelMap model) throws IOException{
		return "test";
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
