package com.rest.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.voka.model.PracticeVO;
import com.voka.service.GetService;

/**
 * @author WeiNung RestController 會依據輸入的url取出值(可以是物件、list..)後，再以JSON形式輸出.
 */
@RestController
@RequestMapping("rest")
public class RestDemoController {

	@Autowired
	private GetService getSvc;

	@PostMapping("getByGender")
	public List<PracticeVO> getByGender(@RequestParam String gender) {
		return gender.equals("1") ? getSvc.getAll() : getSvc.getByGender(gender);
	}

	@GetMapping("typing/{id}")
	public PracticeVO restTypingResult(@PathVariable String id) {
		return getSvc.getOnePractice(new Integer(id));
	}

	@GetMapping("typing/all")
	public List<PracticeVO> restTypingResultAll() {
		return getSvc.getAll();
	}

}
