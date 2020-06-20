	package com.imh.springboot.web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.imh.springboot.web.service.LoginService;

@Controller
public class WelcomeController {
	
	@Autowired
	LoginService service;
	
	@RequestMapping(value="/", method = RequestMethod.GET)
	public String showWelcomePage(ModelMap model){
		model.put("name", getLoggedinUserName());
		return "welcome";
	}
	
	private String getLoggedinUserName() {					//Added
		Object principal = 
				SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		
		if (principal instanceof UserDetails) {
			return ((UserDetails) principal).getUsername();
		}
		return principal.toString();
	}
	
}