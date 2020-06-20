package com.imh.springboot.web.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

//import org.apache.commons.logging.Log;
//import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;

@Controller("error")
public class ErrorController {

//	private Log logger = LogFactory.getLog(ExceptionController.class);
	
	@ExceptionHandler(RuntimeException.class)
	public ModelAndView handleException (HttpServletRequest request, HttpServletResponse response, Exception ex) {
		
//		logger.error("Request: " + request.getRequestURL() + " raised " + ex);
		
		ModelAndView mv = new ModelAndView();
		mv.addObject("exception", ex.getStackTrace());
		mv.addObject("url", request.getRequestURL());
		mv.setViewName("error");
		return mv;
	}
	
}
