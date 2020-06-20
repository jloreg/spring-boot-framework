## Step 07: Your First HTML form

What You Will Learn during this Step:

- Add validation for userid and password
- Hard coded validation !!

**Notes**

```
- GET method is not secure.
- POST method is secure. Use it when you need to send critical data to the server.
```

If we type localhost:8080/login?name=charles, you'd see that values that we entered are part of the URL as well. That means that the request which is being sent is a GET request. **If I don't put any method on the form, then it uses GET by default, and therefore the data is visible**. That's not really cool because it's visible in the URL.

The way Internet works is, there are a number of hops between your browser and the server where your application is deployed. It's not a direct connection between your browser and your server. The request goes through a number of intermediate routers. If you have data in your URL, routers can see that information and that's not really good, I mean you can kind of get away with it by using HTTP, but even that's not a perfect solution. Sending data our in a GET request is not really a secure way of doing it. The ideal way would be to send data in a POST.

##### /src/main/webapp/WEB-INF/jsp/login.jsp
```
<html>
	<head>
		<title>First Web Application</title>
	</head>
	
	<body>
		<form method="post">
			Name : <input type="text" name="name" />
			Password : <input type="password" name="password" />
			<input type="submit" />
		</form>
	</body>
</html>
```

If we add a form with POST in the login  view, you would see that the thing is you're not able to see the data in the URL, when you look at the Network Monitor tab in your web browser, you can see a new thing, you can see there is a new one called Params. You can see that the request URL is localhost:8080/login, the request method is POST, and if you go to the tab Params, Params shows name and password. So what is happening now is the data is not being sent. What I'm typing in the HTML form is not being sent as part of the URL. It's actually being sent as part of the body of your request as form parameters. That is much better way of sending information out.

When you are developing web applications, **make sure that any sensitive information is sent as part of a POST request**. If you send it as part of a GET request it's part of the URL, and that's not really good, that's not secure at all.

When we launch the application, the login view is sending a request and a POST resquest, and for both of them you're getting the same response back. Why is inadequates mapping ? until now we have not specified a request method, when you don't specify a request method in a request mapping, what would happen is this (see #1), would be used. 

```java
@Controller
public class LoginController {

	//#1: Process GET, POST, etc
	@RequestMapping("/login")
	public String loginMessage(ModelMap model){
		model.put("name",name);
		return "login";
	}
}
```

You can look at it and there is no change in the response back. Why is it so ?, we are sending a request and a POST request, for both of them, you're getting the same response back. Why is inadequate mapping? Until now we have not specified a request method. When you don't specify a request method in a request mapping what would happen is this would be used for all the requests (see #1), so this method *public String loginMessage* will be used for GET, POST, PUT, etc. How do we avoid that, so I don't want this method to be used for a POST, how do I do that? the way I do that is by adding a parameter to the annotation *value="/login", method = RequestMethod.GET*. So now that we are adding in a new parameter to the annotation, I would want to use this only for the GET request. When the GET method is executed, I want to show the login page.

```java
@Controller
public class LoginController {

	//Process only GET
	@RequestMapping(value="/login", method = RequestMethod.GET)
	public String loginMessage(@RequestParam String name, ModelMap model){
		return "login";
	}
}
```

If now I type http://localhost:8080/login, the request is now getting the same request back, but If I type a name and a password and press Submit Query (POST), the web browser shows the next above.

```
Web browser output:

Whitelabel Error Page

This application has no explicit mapping for /error, so you are seeing this as a fallback.
Thu Mar 05 12:57:17 CET 2020
There was an unexpected error (type=Method Not Allowed, status=405).
Request method 'POST' not supported
org.springframework.web.HttpRequestMethodNotSupportedException: Request method 'POST' not supported

```

We are getting an error page Request method 'POST' is not supported. If you look at the Controller right now, we only have a method to support the GET. We don't have a method to support the POST, thatś the reason why it's causing a problem. So now what we'll do is we'll add that in. So let's add in a method to support POST. Now I create separate methods for GET and POST, and to differentiate them what I'll do is redirect to different views depending on whether it is a GET or a POST request.

```
@Controller
public class LoginController {

	@RequestMapping(value="/login", method = RequestMethod.GET)
	public String showLoginPage(ModelMap model){
		return "login";
	}
	
	@RequestMapping(value="/login", method = RequestMethod.POST)
	public String showWelcomePage(ModelMap model){
		model.put("name", name);
		return "welcome";
	}
	
}
```

##### /src/main/webapp/WEB-INF/jsp/welcome.jsp
```
<html>
	<head>
		<title>First Web Applications</title>
	</head>
	<body>
		Welcome !!
	</body>
</html>
```

What we have done now, is based on the type of the request, we have different things being shown. What I want to do now is I would want to pick up the value from the request. So let's start with picking up the value of the name to the *model.put("name", name);* and show it on the welcome page.

##### /src/main/webapp/WEB-INF/jsp/welcome.jsp
```
<html>
	<head>
		<title>First Web Applications</title>
	</head>
	<body>
		Welcome {name} !!
	</body>
</html>
```

What we are doing in here, is in the LogginController, now we have a separate methods to support GET and POST. When a GET request is executed on the login URL, we show the login.jsp page, when a POST request is executed on the login URL, we show the welcome.jsp page.
So we take the name from the web form and show it as the name in the page as well.

## Complete Code Example

##### /src/main/java/com/imh/springboot/web/controller/LoginController.java

```java
package com.imh.springboot.web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class LoginController {
	
	@RequestMapping(value="/login", method = RequestMethod.GET)
	public String showLoginPage(ModelMap model){
		return "login";
	}
	
	@RequestMapping(value="/login", method = RequestMethod.POST)
	public String showWelcomePage(@RequestParam String name, ModelMap model){
		model.put("name", name);
		return "welcome";
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
		<form method="post">
			Name : <input type="text" name="name" />
			Password : <input type="password" name="password" />
			<input type="submit" />
		</form>
	</body>
</html>
```

##### /src/main/webapp/WEB-INF/jsp/welcome.jsp
```
<html>
	<head>
		<title>First Web Applications</title>
	</head>
	<body>
		Welcome {name} !!
	</body>
</html>
```