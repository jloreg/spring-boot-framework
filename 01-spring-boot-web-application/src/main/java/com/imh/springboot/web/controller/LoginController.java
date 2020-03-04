package com.imh.springboot.web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class LoginController {

	@RequestMapping("/login")
//	@ResponseBody	//Not necessary when we add a file (.jsp, .html, .json), to be send as response
	public String loginMessage(){
		return "login";
	}
}