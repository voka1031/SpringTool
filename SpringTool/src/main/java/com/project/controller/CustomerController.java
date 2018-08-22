package com.project.controller;

import java.util.List;

import javax.annotation.Nullable;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.project.constant.PageConsts;
import com.project.model.Customer;
import com.project.model.IdWrapper;
import com.project.service.GetService;
import com.project.service.TransService;

@Controller
@RequestMapping("customer")
public class CustomerController {

	@Autowired
	private GetService getSvc;

	@Autowired
	private TransService transSvc;

	@Autowired
	private Environment env;

	@RequestMapping(method = RequestMethod.POST, value = "getOneForDisplayAJAX")
	public String getOneForDisplayAJAX(@RequestParam String id, ModelMap model) {
		model.addAttribute("customer", getSvc.getOnePractice(new Integer(id)));
		return "getOneAjax";
	}

	@RequestMapping(method = RequestMethod.POST, value = "getOneForDisplay")
	public String getOneForDisplay(@Valid @ModelAttribute IdWrapper idWrapper, BindingResult result, ModelMap model) {

		if (result.hasErrors())
			model.addAttribute("message", result.getFieldError().getDefaultMessage());

		Customer pVO = getSvc.getOnePractice(new Integer(idWrapper.getId()));

		if (pVO == null)
			model.addAttribute("message", "查無資料");
		else
			model.addAttribute("customer", pVO);

		return PageConsts.MAIN_PAGE;
	}

	@GetMapping("pagingTable/{nth}")
	public String pagingTable(ModelMap model, @PathVariable @Nullable Integer nth) {
		Integer numberPerPage = Integer.parseInt(env.getProperty("practice.numberPerPage"));
		model.addAttribute("list", getSvc.getPaging((nth - 1), numberPerPage));
		return "pagingTable";
	}

	@GetMapping("listAllPaging")
	public String listAllPaging(ModelMap model) {
		Integer numberPerPage = Integer.parseInt(env.getProperty("practice.numberPerPage"));
		Integer nthPage = Integer.parseInt(env.getProperty("practice.defaultStartingPage"));
		Integer listSize = getSvc.getListSize();
		Integer maxPage = (listSize % numberPerPage == 0) ? listSize / numberPerPage : (listSize / numberPerPage) + 1;

		List<Customer> LastPagelist = getSvc.getPaging((maxPage - 1), numberPerPage);
		Integer lackNumber = numberPerPage - LastPagelist.size();

		model.addAttribute("maxPage", maxPage);
		model.addAttribute("list", getSvc.getPaging(nthPage, numberPerPage));
		model.addAttribute("lackNumber", lackNumber);

		return "listAllPaging";
	}

	@GetMapping("listAllDataTable")
	public String listAll_dataTable(ModelMap model) {
		model.addAttribute("list", getSvc.getDemoList());
		return "listAllDataTable";
	}

	@PostMapping("getOneForUpdate")
	public String getOneForUpdate(@RequestParam("id") String id, ModelMap model) {
		model.addAttribute("customer", getSvc.getOnePractice(new Integer(id)));
		return PageConsts.UPDATE_PAGE;
	}

	@GetMapping("add")
	public String addPractice(ModelMap model) {
		model.addAttribute("customer", new Customer());
		return PageConsts.INSERT_PAGE;
	}

	@GetMapping("addJQueryValidate")
	public String addJQueryValidate(ModelMap model) {
		model.addAttribute("customer", new Customer());
		return "addJQueryValidate";
	}

	@PostMapping("insert")
	public String insert(@Valid Customer customer, BindingResult result, ModelMap model) {

		if (result.hasErrors())
			return PageConsts.INSERT_PAGE;

		transSvc.addPractice(customer);
		return listAllPaging(model);
	}

	@PostMapping("update")
	public String update(@Valid Customer customer, BindingResult result, ModelMap model) {

		if (result.hasErrors())
			return PageConsts.UPDATE_PAGE;

		transSvc.updatePractice(customer);
		return listAllPaging(model);
	}

	@PostMapping("delete")
	public String delete(@RequestParam("id") String id, ModelMap model) {
		transSvc.deletePractice(new Integer(id));
		return listAllPaging(model);
	}
	
	@PostMapping(value = "getByGender", produces = "application/json;charset=UTF-8")
	@ResponseBody
	public String getByGender(String gender) {
		return getSvc.getByGenderJson(gender);
	}
}
