package com.project.handler;

import javax.servlet.http.*;
import org.springframework.context.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.project.constant.Constants;

@Configuration
public class ProjectInterceptor extends HandlerInterceptorAdapter {
	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception {
		request.setAttribute(Constants.TEST_JSON_MAP_NAME, Constants.TEST_JSON_MAP_CONTENT);
		request.setAttribute(Constants.GENDER_MAP, Constants.GENDER_MAP_CONTENT);
		request.setAttribute(Constants.GENDER_MAP_KEY, Constants.GENDER_MAP_CONTENT.keySet());
		super.postHandle(request, response, handler, modelAndView);
	}
}