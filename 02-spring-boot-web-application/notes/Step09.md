## Step 09: Magic of Spring

What You Will Learn during this Step:

- Magic of Spring
- Learn about Spring Auto-wiring and Dependency Management.
- @Autowired, @Component

In this step, we will learn a little bit more about Spring Autowiring and Dependency Injection. Spring is one of the most famous Dependency Injection Frameworks.

If you go one decade back it was impossible to unit test applications. That's because everything was tightly coupled, everything was using things like the next above.

```java
@Controller
public class LoginController {
	
	LoginService service = new LoginService();
}
```

In that kind of scenario, it's very difficult to unit test stuff. That's where this kind of scenario world when Spring Framework came in with this concept called Dependency Injection.

With Dependency Injection, it makes it really really easy to unit test applications. Spring is a vast framework and it will focus on three different things related to Spring.

@Controller. If your bean is handling request from the browser, then you're kind of having control or login in there then you should ideally use this annotation.
@Service. If your bean has business logic then you should ideally use this annotation.
@Repository. If you're using your bean to save stuff to the database and retrieve from there, you are supposed to use this annotation.
@Component. Is a kind of the generic thing for all these three kinds of scenario. 

About the different annotations which help you to declare that something is a Spring Bean, those are the things which help you to say "Hey Spring, manages me".

@Autowired. It's basically the way you are saying this LoginController, is saying I want to Spring Framework to autowire this *LoginService service;* into me. What would happen is the service would be created by the Spring Framework and right autowired here in the LoginController.

You can try running the application removing the @Autowired and you'd see that you get a NullPointerException because that *LoginService service;* will not be autowired in.

Autowiring is basically the phase where once a bean is created, Spring Boot autowired it into wherever it's needed. Once a *LoginService service;* is created, since LoginController needing it, it would be autowired into the LoginController.

```java
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

```java
@Service	//@Service instead @Component: it's for business logic
public class LoginService {

	//Hard coded validation
	public boolean validateUser (String userid, String password) {
		//userid: jon, password: noob 
		return userid.equalsIgnoreCase("jon") 
				&& password.equalsIgnoreCase("noob");
	}
}
```

@ComponentScan. We got ComponentScan automatically when we created our SpringBootWebApplication. One of the things that you need to know, is all our classes, all the things that we have created until now, are in this package com.imh.springboot.web; or in his subpackages of it. 

```java
package com.imh.springboot.web;

@SpringBootApplication
//@ComponentScan ("com.imh.springboot.web"); //If we don't annotate the class with this annotation, @SpringBootApplication loads equally
public class SpringBootWebApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringBootWebApplication.class, args);
	}

}
```

One of the things that @SpringBootApplication annotation has is something called @ComponentScan build in. So what is happening is, as soon as I created at @SpringBootApplication it is really defining automatically an @ComponentScan on this specific package com.imh.springboot.web.

What the Spring Framework would do, is it will look at these packages for any definitions of these four things (@Controller, @Service, @Repository, @AutoWired). So what this Spring Framework does it starts looking in these package com.imh.springboot.web, and it's sub-packages com.imh.springboot.web.* for components. So if you create a component outside this package, then it will not be autowired by Spring Framework.

So Spring Framework is all about finding your components, that's ComponentScan, and auto wiring them. **Those are the two important concepts in Spring**. Spring finds components and manages the lifecycle of them, it manages these beans, injects them wherever they are needed, and that's all Spring does.

In this step, we looked at some of the important things that Spring does. So we looked a few important annotations which you can use to say Spring "Spring, go ahead and manage these beans", and we also look at @Autowired and @ComponentScan annotations.