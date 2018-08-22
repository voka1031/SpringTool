package com.project.controller;

import java.io.IOException;
import java.text.ParseException;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.project.service.GetService;

@Controller
@RequestMapping("finance")
public class FinanceController {

	@Autowired
	private GetService getSvc;

	@GetMapping("stock/{stockId}/{startDate}/{endDate}")
	public String stock(ModelMap model, @PathVariable String stockId,
			@PathVariable String startDate, @PathVariable String endDate)
			throws IOException, ParseException {

		if (StringUtils.isBlank(stockId))
			stockId = "2002";

		List<List<Map<Object, Object>>> canvasjsDataList = getSvc.getStock(stockId, startDate, endDate);
		model.addAttribute("dataPointsList", canvasjsDataList);
		return "stock";
	}
	
	@GetMapping("getStock")
	public String getStock(ModelMap model, @PathVariable String stockId,
			@PathVariable String startDate, @PathVariable String endDate)
					throws IOException, ParseException {
		
		if (StringUtils.isBlank(stockId))
			stockId = "2002";
		
		List<List<Map<Object, Object>>> canvasjsDataList = getSvc.getStock(stockId, startDate, endDate);
		model.addAttribute("dataPointsList", canvasjsDataList);
		return "stock";
	}

}
