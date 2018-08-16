package com.project.controller;

import java.util.Date;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.project.model.TestResponse;


@RestController
@RequestMapping("rest")
public class RestApiController {
	
	@GetMapping("test")
	public TestResponse getRestTest() {
		
		return new TestResponse(new Date());
	}
	

}
