package com.rest.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.project.model.Customer;
import com.project.service.GetService;

/**
 * @author WeiNung RestController 會依據輸入的url取出值(可以是物件、list..)後，再以JSON形式輸出.
 */
@RestController
@RequestMapping("rest")
public class RestDemoController {

	@Autowired
	private GetService getSvc;

	@PostMapping("getByGender")
	public List<Customer> getByGender(@RequestParam String gender) {
		return gender.equals("1") ? getSvc.getAll() : getSvc.getByGender(gender);
	}

	@GetMapping("typing/{id}")
	public Customer restTypingResult(@PathVariable String id) {
		return getSvc.getOnePractice(new Integer(id));
	}

	@GetMapping("typing/all")
	public List<Customer> restTypingResultAll() {
		return getSvc.getAll();
	}

}
