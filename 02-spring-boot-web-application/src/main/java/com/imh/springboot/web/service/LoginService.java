package com.imh.springboot.web.service;

import org.springframework.stereotype.Controller;

@Controller
public class LoginService {

	//Hard coded validation
	public boolean validateUser (String userid, String password) {
		//userid: imh, password: dummy 
		return userid.equalsIgnoreCase("imh") 
				&& password.equalsIgnoreCase("dummy");
	}
}
