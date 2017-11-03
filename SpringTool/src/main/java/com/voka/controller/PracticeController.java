package com.voka.controller;

import com.google.gson.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.http.*;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.validation.BindingResult;
import org.springframework.ui.*;

import java.util.List;

import javax.annotation.Nullable;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import com.google.gson.Gson;
import com.voka.constant.Constants;
import com.voka.model.*;
import com.voka.service.*;

@Controller
@RequestMapping("/practice")
@PropertySource("classpath:apConfig.properties")
public class PracticeController {

	@Autowired
	private TransService transSvc;

	@Autowired
	private GetService getSvc;

	@Autowired
	private Environment env;

	@RequestMapping(value = "uploadPage", method = RequestMethod.GET)
	public String view(HttpSession session, Model model) throws Exception {
		return "fileUpload";
	}
	
	@PostMapping(value = "responseJson", produces = "application/json;charset=UTF-8")
	@ResponseBody	
	public String restResponse(@RequestParam String gender) {
		return getSvc.getByGenderJson(gender);
	}

	@PostMapping(value = "getByGender", produces = "application/json;charset=UTF-8")
	@ResponseBody
	public String getByGender(String gender) {
		return getSvc.getByGenderJson(gender);
	}

	@GetMapping(value = "getResponseBody", produces = "application/json;charset=UTF-8")
	@ResponseBody
	public String getResponseBody(@RequestParam(value = "inputVal") String inputVal) {
		JsonArray array = new JsonArray();
		for (int i = 0; i < 5; i++) {
			JsonObject obj = new JsonObject();
			obj.addProperty("inputVal", inputVal + i);
			array.add(obj);
		}
		return new Gson().toJson(array);
	}

	@RequestMapping(value = "upload", method = RequestMethod.POST, produces = "application/json", consumes = "multipart/form-data")
	@ResponseBody
	public ResponseEntity<String> fileUpload(@RequestParam("file") MultipartFile multipartFile) throws Exception {

		System.out.println("in fileUpload, getContentType = " + multipartFile.getContentType());
		System.out.println("in fileUpload, getName = " + multipartFile.getName());
		System.out.println("in fileUpload, getOriginalFilename = " + multipartFile.getOriginalFilename());
		System.out.println("in fileUpload, getSize = " + multipartFile.getSize() + " bytes");
		System.out.println("in fileUpload, getSize = " + multipartFile.getSize() + " bytes");

		try {
			if (Constants.CONTENT_TYPE_EXCEL_XLSX.equals(multipartFile.getContentType()))
				getSvc.excelCheck(multipartFile);
			return ResponseEntity.ok().build();
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
		}
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