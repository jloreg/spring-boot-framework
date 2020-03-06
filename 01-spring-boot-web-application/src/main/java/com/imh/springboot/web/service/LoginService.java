package com.imh.springboot.web.service;

import org.springframework.stereotype.Controller;

@Controller
public class LoginService {

	//Hard coded validation
	public boolean validateUser (String userid, String password) {
		//userid: jon, password: noob 
		return userid.equalsIgnoreCase("jon") 
				&& password.equalsIgnoreCase("noob");
	}
}
