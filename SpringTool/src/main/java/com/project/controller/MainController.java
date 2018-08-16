package com.project.controller;

import java.io.IOException;
import java.util.List;

import javax.annotation.Nullable;
import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

import com.project.constant.PageManager;
import com.project.model.IdWrapper;
import com.project.model.PracticeVO;
import com.project.service.GetService;
import com.project.service.TransService;

@Controller
@RequestMapping("/practice")
public class MainController {

	@SuppressWarnings("unused")
	private static final Logger logger = LoggerFactory.getLogger(MainController.class);

	@Autowired
	private GetService getSvc;

	@Autowired
	private TransService transSvc;

	@Autowired
	private Environment env;

	@GetMapping("test")
	public String entranceOfTestPage(ModelMap model) throws IOException {
		return "test";
	}

	@GetMapping("tran1")
	public String testTran1(ModelMap model) throws IOException {
		transSvc.transRequriedTest1();
		return PageManager.MAIN_PAGE;
	}

	@GetMapping("tran2")
	public String testTran2(ModelMap model) throws IOException {
		transSvc.transRequriedTest2();
		return PageManager.MAIN_PAGE;
	}

	@RequestMapping(method = RequestMethod.POST, value = "getOne_For_Display_ajax")
	public String getOne_For_Display_ajax(@RequestParam String id, ModelMap model) {
		model.addAttribute("practiceVO", getSvc.getOnePractice(new Integer(id)));
		return "getOneAjax";
	}

	@RequestMapping(method = RequestMethod.POST, value = "getOne_For_Display")
	public String getOne_For_Display(@Valid @ModelAttribute IdWrapper idWrapper,
			BindingResult result, ModelMap model) {

		if (result.hasErrors())
			model.addAttribute("message", result.getFieldError().getDefaultMessage());

		else {
			PracticeVO pVO = getSvc.getOnePractice(new Integer(idWrapper.getId()));

			if (pVO == null)
				model.addAttribute("message", "查無資料");
			else
				model.addAttribute("practiceVO", pVO);

		}
		return PageManager.MAIN_PAGE;
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

		List<PracticeVO> LastPagelist = getSvc.getPaging((maxPage - 1), numberPerPage);
		Integer lackNumber = numberPerPage - LastPagelist.size();

		model.addAttribute("maxPage", maxPage);
		model.addAttribute("list", getSvc.getPaging(nthPage, numberPerPage));
		model.addAttribute("lackNumber", lackNumber);

		return "listAllPaging";
	}

	@GetMapping("listAll_dataTable")
	public String listAll_dataTable(ModelMap model) {
		model.addAttribute("list", getSvc.getDemoList());
		return "listAll_dataTable";
	}

	@PostMapping("getOne_For_Update")
	public String getOne_For_Update(@RequestParam("id") String id, ModelMap model) {
		model.addAttribute("practiceVO", getSvc.getOnePractice(new Integer(id)));
		return "update";
	}

	@GetMapping("addPractice")
	public String addPractice(ModelMap model) {
		model.addAttribute("practiceVO", new PracticeVO());
		return "add";
	}

	@GetMapping("addPractice_jQueryValidate")
	public String addPractice_JQueryValidate(ModelMap model) {
		model.addAttribute("practiceVO", new PracticeVO());
		return "add_jQueryValidate";
	}

	@PostMapping("insert")
	public String insert(@Valid PracticeVO practiceVO, BindingResult result, ModelMap model) {

		if (result.hasErrors())
			return "add";

		transSvc.addPractice(practiceVO);
		return listAllPaging(model);
	}

	@PostMapping("update")
	public String update(@Valid PracticeVO practiceVO, BindingResult result, ModelMap model) {

		if (result.hasErrors())
			return "update";

		transSvc.updatePractice(practiceVO);
		return listAllPaging(model);
	}

	@PostMapping("delete")
	public String delete(@RequestParam("id") String id, ModelMap model) {
		transSvc.deletePractice(new Integer(id));
		return listAllPaging(model);
	}
}
