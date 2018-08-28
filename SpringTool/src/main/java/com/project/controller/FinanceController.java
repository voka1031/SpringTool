package com.project.controller;

import java.io.IOException;
import java.text.ParseException;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.project.model.stock.StockRequest;
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
		model.addAttribute("dataPointsList", getSvc.getStock(stockId, startDate, endDate));
		return "stock";
	}

	@GetMapping("getStock")
	public String getStock(@Valid  @ModelAttribute StockRequest req, BindingResult result, ModelMap model)
			throws IOException, ParseException {
		System.out.println("req : " + req);
		model.addAttribute("dataPointsList", getSvc.getStock(req.getSecurityCode(), req.getStartDate(), req.getEndDate()));
		return "stock";
	}

}
