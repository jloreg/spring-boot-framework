## Step 24: Refactor and add Logout Functionality using Spring Security

What we will do:

- Remove Hardcoding of User Name
- Remove LoginService
- Rename LoginController to WelcomeController
- Add Logout Functionality

**Note**. To solve the issue "There is no PasswordEncoder mapped for the id "null"", we added in SecurityConfirguration the suggested fix present on [stackoverflow](https://stackoverflow.com/questions/49654143/spring-security-5-there-is-no-passwordencoder-mapped-for-the-id-null) 

``` java
@SuppressWarnings("deprecation")
@Bean
public static NoOpPasswordEncoder passwordEncoder() {
return (NoOpPasswordEncoder) NoOpPass
```

## Complete Code Example

##### /src/main/java/com/imh/springboot/web/security/SecurityConfiguration.java

``` java
package com.imh.springboot.web.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;

@Configuration
public class SecurityConfirguration extends WebSecurityConfigurerAdapter {

	//Added to solve the issue: java.lang.IllegalArgumentException: There is no PasswordEncoder mapped for the id "null"
	@SuppressWarnings("deprecation")
	@Bean
	public static NoOpPasswordEncoder passwordEncoder() {
	return (NoOpPasswordEncoder) NoOpPasswordEncoder.getInstance();
	}
		
	@Autowired
	public void configureGlobalSecurity(AuthenticationManagerBuilder auth) 
			throws Exception {
		auth.inMemoryAuthentication().withUser("imh").password("dummy")
				.roles("USER", "ADMIN");
	}
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.authorizeRequests().antMatchers("/login").permitAll()
				.antMatchers("/", "/*todo*/**").access("hasRole('USER')").and()
				.formLogin();
	}

}
```

##### /src/main/java/com/imh/springboot/web/controller/WelcomeController.java

``` java
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
	
	private String getLoggedinUserName() {				//Added
		Object principal = 
				SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		
		if (principal instanceof UserDetails) {
			return ((UserDetails) principal).getUsername();
		}
		return principal.toString();
	}
	
}
```

##### /src/main/java/com/imh/springboot/web/controller/TodoController.java

``` java
package com.imh.springboot.web.controller;

import java.text.SimpleDateFormat;
import java.util.Date;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.annotation.RequestParam;

import com.imh.springboot.web.model.Todo;
import com.imh.springboot.web.service.TodoService;

@Controller
public class TodoController {
	
	@Autowired
	TodoService service;
	
	@InitBinder
	protected void initBinder(WebDataBinder binder) {
		SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
		binder.registerCustomEditor(Date.class, new CustomDateEditor(
				dateFormat, false));
	}

	private String getLoggedInUserName(ModelMap model) {	//Added
		
		Object principal = 
				SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		
		if (principal instanceof UserDetails) {
			return ((UserDetails) principal).getUsername();
		}
		
		return principal.toString();
	}
	
	@RequestMapping(value="/list-todos", method = RequestMethod.GET)
	public String showTodos(ModelMap model){
			String name = getLoggedInUserName(model);
			model.put("todos", service.retrieveTodos(name));
		return "list-todos";
	}
	
	@RequestMapping(value="/add-todo", method = RequestMethod.GET)
	public String showAddTodoPage(ModelMap model){
		model.addAttribute("todo", new Todo(0, getLoggedInUserName(model), "Default Desc",
				new Date(), false));
		return "todo";
	}
	
	@RequestMapping(value="/add-todo", method = RequestMethod.POST)
	public String addTodo(ModelMap model, @Valid Todo todo, BindingResult result){
		if (result.hasErrors()) {
			return "todo";
		}
		service.addTodo(getLoggedInUserName(model), todo.getDesc(), todo.getTargetDate(), false);
		return "redirect:/list-todos";
	}
	
	@RequestMapping(value="/delete-todo", method = RequestMethod.GET)
	public String deleteTodo(@RequestParam int id){
		service.deleteTodo(id);
		return "redirect:/list-todos";
	}
	
	@RequestMapping(value="/update-todo", method = RequestMethod.GET)
	public String showUpdateTodoPage(@RequestParam int id, ModelMap model){
		Todo todo = service.retrieveTodo(id);
		model.put("todo", todo);
		return "todo";
	}
	
	@RequestMapping(value="/update-todo", method = RequestMethod.POST)
	public String updateTodo(ModelMap model, @Valid Todo todo, BindingResult result){
		
		if (result.hasErrors()) {
			return "todo";
		}
		
		todo.setUser(getLoggedInUserName(model));
		service.updateTodo(todo);
		return "redirect:/list-todos";
	}

}
```

##### /src/main/java/com/imh/springboot/web/controller/LogoutController.java

``` java
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
		
		//
		if (authentication != null) {
			new SecurityContextLogoutHandler().logout(request, response, 
					authentication);
		}
		
		//Redirect the user to the login.jsp page
		return "redirect:/";	
		
	}

}
```