package com.project.controller;

import java.io.IOException;
import java.util.Random;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.project.constant.Constants;
import com.project.constant.PageConsts;
import com.project.service.GetService;

@Controller
@RequestMapping("/practice")
public class StudioController {

	@Autowired
	private GetService getSvc;

	@Value("${test.content}")
	private String CONTENT;

	@GetMapping
	public String entry(ModelMap model) throws IOException {
		System.out.println(CONTENT);
		return PageConsts.MAIN_PAGE;
	}

	@GetMapping("uploadPage")
	public String uploadPage(HttpSession session, Model model) throws Exception {
		return "fileUpload";
	}

	@PostMapping(value = "responseJson", produces = "application/json;charset=UTF-8")
	@ResponseBody
	public String restResponse(@RequestParam String gender) {
		return getSvc.getByGenderJson(gender);
	}

	/**
	 * 亂數驗證碼
	 * 
	 * @param request
	 * @param response
	 * @param session
	 * @param modelMap
	 * @throws IOException
	 */
	@GetMapping("jcaptcha.jpg")
	public void getJcaptcha(HttpServletRequest request, HttpServletResponse response, HttpSession session) throws IOException {
		// 設置頁面不緩存
		response.setHeader("Pragma", "No-cache");
		response.setHeader("Cache-Control", "no-cache");
		response.setDateHeader("Expires", 0);

		String randomStr = Integer.valueOf(new Random().nextInt(89999) + 10000).toString();
		session.setAttribute("randomStr", randomStr);
		// 輸出圖像到頁面
		ImageIO.write(getSvc.getCaptcha(randomStr), "JPEG", response.getOutputStream());
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

		try {
			if (StringUtils.equals(Constants.CONTENT_TYPE_EXCEL_XLSX, multipartFile.getContentType()))
				getSvc.excelCheck(multipartFile);
			return ResponseEntity.ok().build();
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
		}
	}

}