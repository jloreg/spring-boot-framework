## Step 12: Session vs Model vs Request - @SessionAttributes

What You Will Learn during this Step:

- Session vs Model vs Request scope.
- Be cautious about what you use Session for.
- @SessionAttributes("name") and how it works?
- Why use Model? "adding elements directly to the HttpServletRequest (as request attributes) would seem to serve the same purpose. The reason to do this is obvious when taking a look at one of the requirements we have set for the MVC framework: It should be as view-agnostic as possible, which means we’d like to be able to incorporate view technologies not bound to the HttpServletRequest as well." - Rod Johnson et. al’s book Professional Java Development with the Spring Framework
- Spring documentation states that the @SessionAttributes annotation “list the names of model attributes which should be transparently stored in the session or some conversational storage.”

**Notes**

```
//Flow of the web application:

login.jsp =>  LoginController => adds name to model => redirects to welcome view.
welcome.jsp => shows ${name}

If you do "Click here" => goes to TodoController => redirects to list-todos view
list-todos.jsp => show ${name} is empty
```

In the *public String showWelcomePage* method that maps POST request of the login.jsp page, has two Request parameters called name and password, and the values which we're entering on the screen are mapped to *@RequestParam String name* and *@RequestParam String password*. We're using that to call the service here in *service.validateUser(name, password);*, and also then putting it into the model. So name and password come from the request, we are putting in the model, and we are returning welcome view back.

```
@Controller
public class LoginController {
	
	@Autowired
	LoginService service;
	
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

What we are putting in the model in here, or what about is coming in the @RequestParam parameter, they're not really available for subsequent requests. What do I mean?

So name and password come from the request, we are putting in the model, and we are returning "welcome" view back. What we are putting in the model here in *public String showWelcomePage*, or what about is coming in the request parameters, they're not really available for subsequent requests. What do I mean? What I mean is let's say over here in LoginController we have *model.put("name", name);* and *model.put("password", password);*, we're running the *return "welcome";* page back, and later let's say the user clicks "Click here to manage your todo's" in localhost:8080/login, what happens ?
 
The user goes to the TodoController, and TodoController redirects the user to the list-todos.jsp page. What our data LoginController put into the model as name *model.put("name", name);*, will it be available in the list-todos.jsp page

##### /src/main/webapp/WEB-INF/jsp/list-todos.jsp

```
<html>
	<head>
		<title>First Web Applications</title>
	</head>
	<body>
		Here are the list of your todos: 
		${todos}
		<br />
		Your name is : ${name}
	</body>
</html>
```

Now, If you do a refresh of localhost:8080/list-todos.jsp page, now name it's coming as empty *${name}*, even though we are putting the value of name "jon" into the model at the time of login in localhost:8080/login. So in the model, we put the value "jon", the view welcome.jsp page is displayed, and when we do click in "Click here", the next view list-todos.jsp page is rendered, we are trying to put the value of the name into the screen and it's not pleasant at all.

When we enter the name "jon" and enter our password "noob" in localhost:8080/login.jsp and do a submit query, what we're doing is we are adding *name* to the model, so whatever is in the request, is added to the model, and the welcome.jsp shows that value "jon" here in the welcome.jsp page. When I do click in "Click here", it goes to the TodoController, and the TodoController redirects to the list-todos.jsp view, and in the list-todos.jsp, **${name} is coming as an empty**.

**The most important thing that you need to understand is that all the request parameters have the scope of that specific request.** If I enter something on this form in localhost:8080/login and do submit, the data which we entered it's only valid for that specific request. So, If you go to the Network tab, and you reload the data that is sent as part of the request in the tab Params, you would see that whatever was sent in the previous request are no longer applicable for this request, so the name and the other stuff will not be sent as part of the next request.

**The scope of the request parameters is just that particular request**. They will not be available for the subsequent requests. **The same is the case for things in the model by default**.
So if you're adding a name to the model in one request, and the view shows it, that's it. After that, it will not be available at all. So if you try to access that value in any other Controller, it would come as empty, or it would not be able to get the value of it.

---

If you want to have the value stored between multiple requests, I have to use something called Session. So session is the way you would be able to store values across multiple requests. The values in Request and Model is Request scope by default, so if you want to store something across the length of the user session, then you would need to use something called Session.

How do you really put values in a session? that's the most important question. So how do I now put a value into a Session? What we want to do, is we want to be able to put the value into the Session in LoginController. All the subsequent controllers, for example, the TodoController would want to be able to use the value. What we would need to do is, in TodoController and LoginController, we need to add a simple @SessionAtribute annotation, and the name of the model attributes which you want to store in Session.

```java
@Controller
@SessionAttributes("name")	//Added
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

So here I want to store the name in session, and in any other controller where we want to access the value from the session, we need to put @SessionAtributes("attributeName") annotation as well. So in the TodoController, I would want to be able to access the name, I want to be able to take the name and get the Todo's for that particular name user. Here in *model.put("todos", service.retrieveTodos("imh"));* until now we hardcoded "imh", we don't want to do that. We want to use the name from Session, and we would want to do that, so how do we do that now? See #1, now I can access the value of the name from the model.

```java
@Controller
@SessionAttributes("name")	//#2. Added
public class TodoController {
	
	@Autowired
	TodoService service;

	@RequestMapping(value="/list-todos", method = RequestMethod.GET)
	public String showTodos(ModelMap model){
		String name = (String) model.get("name");	//#1. Added
		model.put("todos", service.retrieveTodos(name));	//#1. Added
		return "list-todos";
	}

}
```

Let's take a step back and review what we are doing. We added in an annotation called @SessionAttribute("name") in LoginController. What does this do? If there's an attribute called name in the model *model.put("name", name);*, what it does is it takes it and puts it into the session. And now in the TodoController, I would want to be able to access the value of that particular name. So what I need to do ?, I need to put an @SessionAtributtes("name") in TodoController. Now I can access that from my model (see #2).

Login again in localhost:8080/login, type in "jon" as name, and "noob" as password, submit query to put the value in the session, and now when I press in the link "Click here", now you can see rendered in the screen "Your name is: jon", because now the name is in session, and it's persisted across multiple requests. Spring provides this simple annotation @SessionAttributes to be able to have conversational storage. Conversational storage is basically values which persists across multiple requests.

**Important**

HTTP basically is a stateless protocol, so what we're using here is an HTTP request and HTTP response. HTTP request and HTTP response don't store any state, so they don't know what happened previously. The only thing which HTTP request contains is what is the action that the user is doing right now. If you want to store any conversational state, then you have to do it on the server-side.

So on the server-side, what we do is, we create a session. If we want to store values across requests, then we would store things in something called a Session; example in TodoController class with the use of the annotation @SessionAttributes. 

Let's say your application has millions of users. If you have a lot of data in your session, then the amount of memory that you need on your server would be huge. So you should be very cautious about the things which you put in your session. Your sessions footprint should be as small as possible. That's one thing you should always be cautious while developing web applications. So do not have a lot of conversational state, some conditional state is definitely needed but trying to restrict it to the minimum possible amount. Whenever you don't need some conditional state, try and remove the attributes from the session as well. 

**Resume**

In this step we looked Request, Model and Session, we discuss that any data in the Request it's only available for that specific request, all the parameters are not available for the subsequent requests by default. And same is the case with Model, anything you put in the model it's only until that specific request is executed. If you want conversational state you have to use something called Session, and the last thing that we discussed is the fact that you should be cautious about the amount of data that you put in your session.

## Complete Code Example

##### /src/main/webapp/WEB-INF/jsp/list-todos.jsp

```html
<html>
	<head>
		<title>First Web Applications</title>
	</head>
	<body>
		Here are the list of your todos: 
		${todos}
		<br />
		Your name is : ${name}
	</body>
</html>
```

##### /src/main/java/com/imh/springboot/web/controller/LoginController.java

```java
@Controller
@SessionAttributes("name")
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

##### /src/main/java/com/imh/springboot/web/controller/TodoController.java

```java
package com.imh.springboot.web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.SessionAttributes;

import com.imh.springboot.web.service.TodoService;

@Controller
@SessionAttributes("name")
public class TodoController {
	
	@Autowired
	TodoService service;

	@RequestMapping(value="/list-todos", method = RequestMethod.GET)
	public String showTodos(ModelMap model){
			String name = (String) model.get("name");
			model.put("todos", service.retrieveTodos(name));
		return "list-todos";
	}

}
```