## Step 08: Add hard-coded validation of userid and password

What You Will Learn during this Step:

- Add validation for userid and password
- Hard coded validation!!

In this step we would want to add a simple validation to the userid and password to get started.

**An appointment in dependency injection**

When I launched the application, failed due to there's a NullPointerException. The reason why is throwing in an exception is because we have not initialized the LoginService service. 

```
@Controller
public class LoginController {

	LoginService service;
}
```

Typical way we initialized this kind of services is by saying the next above.

```
@Controller
public class LoginController {
	
	LoginService service = new LoginService();
}
```

That's the typical way we would initialize things if we are not using the Spring Framework. When we are not using Spring to 10 years back, this is the way I would be initializing. The LoginService is a dependency of the LoginController, what does that mean? LoginController needs LoginService to be able to work fine. When I do a *LoginService service = new LoginService();*, **then I'm tightly coupled to this LoginService**, so there is no way I can pass in a different instance of the LoginService to this.

That's where Spring comes in with this concept of Dependency Injection. Since LoginService is a Dependency of the LoginController, all that I should do is declare that this is a dependency. The dependency injection Framework will make sure that the LoginService would be autowired. When we use a dependency injection framework very well, we would not need to create instances of classes *new LoginService()*. We would not need to create the object, all the beans that would be created by the dependency injection framework, and they would be instantiated, and the dependencies will be autowired. Two basic steps in any dependency injection thing.

```java
@Controller	//Spring Bean
public class LoginController {
	
	@Autowired	//#1.2: Injected automatically
	LoginService service;
	
	@RequestMapping(value="/login", method = RequestMethod.GET)
	public String showLoginPage(ModelMap model){
		return "login";
	}
	
	@RequestMapping(value="/login", method = RequestMethod.POST)
	public String showWelcomePage(ModelMap model, @RequestParam String name, @RequestParam String password){
		
		boolean isValidUser = service.validateUser(name, password);
		if (!isValidUser) {
			return "login";
		}
		
		model.put("name", name);
		model.put("password", password);
		return "welcome";
	}
	
}
```

First thing I would need to say "Spring you need to manages this LoginService class", so I need to say this LoginService is a Spring bean, Spring needs to create an instance of this particular thing, that means that this is a Spring bean (see #1.1). 

The second thing I would need to do is go to LoginController and I should be able to say LoginService need to be injected automatically. Whatever LoginService is created in here, I would want to take it and I would want to autowired in here (see #1.2). I don't want to do a *new LoginService();*, How do I do that? the two steps are, one declared a bean, so in LoginService class this is going to say "I'm a declared Spring bean", in LoginController would say "I need a LoginService", how does the LoginService say "I'm a Spring bean" ... there are a couple of annotations available, we will use only one of them, it's called @Component. So when I have @Component on top of the LoginService class, it means Spring is going to manage this, Spring would say "Hey LoginService, I would create an instance of you, you don't need to worry about it".

```java
@Component	//#1.1: Spring Bean
public class LoginService {

	public boolean validateUser (String userid, String password) {
		//userid: Charles, password: dummy
		return userid.equalsIgnoreCase(userid) 
				&& password.equalsIgnoreCase(password);
	}
}
```

One of the instance is created, the LoginControler needs to pick up that instance, LoginController needs to tell Spring Framework "Hey Spring Framework, give me the *LoginService service;* that you created. How do you say that? that's through another annotation called @Autowired, basically LoginContoller is saying "Hey Spring Framework, hey Dependency Injection Framework when you find the LoginService instance, when you find a bean of the LoginService instance, take it and autowired it here in the LoginController". So now we are not creating an instance of the LoginService.

Ideally, actually we can even create the LoginService as an interface. So if the LoginService was an interface, then I can have one dummy implementation like this *public boolean validateUser* which validates user, I can have one implementation of the interface that talks to the database,
and another implementation it is talking to the elder implementation. **So whenever I would want to switch from one service to another service, you don't really need to change anything in your LoginController**. 

**Resume**

In this step we actually now added in a hard coed validation for userId and password, if a user enters a valid user id and password combination, then we send him to the welcome.jsp page. Otherwise, we ask him to tell the userId and password again with a simple validation message.

In the last eight steps, we have learnt a lot of things regarding Spring Boot, we looked different things related to web applications, we looked GET, POST, we looked at different parameters, how you can create a form, and how you can pass the data between your controller, and your views.

## Complete Code Example

##### /src/main/java/com/imh/springboot/web/service/LoginController.java

```java
package com.imh.springboot.web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.imh.springboot.web.service.LoginService;

@Controller
public class LoginController {
	
	@Autowired
	LoginService service;
	
	@RequestMapping(value="/login", method = RequestMethod.GET)
	public String showLoginPage(ModelMap model){
		return "login";
	}
	
	@RequestMapping(value="/login", method = RequestMethod.POST)
	public String showWelcomePage(ModelMap model, @RequestParam String name, @RequestParam String password){
		
		boolean isValidUser = service.validateUser(name, password);
		if (!isValidUser) {
			model.put("errorMessage", "Invalid Credentials");
			return "login";
		}
		
		model.put("name", name);
		model.put("password", password);
		return "welcome";
	}
	
}
```

##### /src/main/java/com/imh/springboot/web/service/LoginService.java
```java
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

```

##### /src/main/webapp/WEB-INF/jsp/login.jsp
```
<html>
	<head>
		<title>First Web Application</title>
	</head>
	
	<body>
		<font color="red">${errorMessage}</font>
		<form method="post">
			Name : <input type="text" name="name" />
			Password : <input type="password" name="password" />
			<input type="submit" />
		</form>
	</body>
</html>
```