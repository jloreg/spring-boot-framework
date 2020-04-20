package com.imh.springboot.web.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

public class LogoutController {
	
	@RequestMapping(value="/logout", method = RequestMethod.GET)
	public String logout(HttpServletRequest request, HttpServletResponse response){

		//Get authentication details of the user
		Authentication authentication = SecurityContextHolder.getContext().
				getAuthentication();
		
		if (authentication != null) {
			new SecurityContextLogoutHandler().logout(request, response, 
					authentication);
		}
		
		//Redirect the user to the login.jsp page
		return "redirect:/";	
		
	}

}
