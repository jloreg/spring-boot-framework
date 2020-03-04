## Step 05: Show userid and password on welcome page - ModelMap and @RequestParam

What You Will Learn during this Step:

- Your first GET Parameter
- Problem with using GET

In this step, we will create our first get parameter. We'll understand what a GET parameter is and we'll see how to pass that as a parameter to our view in .jsp. Let's get started now.

What we want to do in this step, is we want to pass in loggin?name=charles, I want to pass in a parameter the name Charles, and I would want to see that in the view login.jsp content. That's what I would want to do.

So, if we type http://localhost:8080/login?name=charles, and next we go to the Network monitor of the web browser, you would see what is happening in here. There is a request that is being sent * Request URL: http://localhost:8080/login?name=charles *, and the Response coming back is login.jsp. 

The question is, How do I take the name parameter and send it in the response. This parameter localhost:8080/login?**name=charles** which we are passing to the GET request is called GET parameter. Before I print the GET parameter, I mean before I send the GET parameter to the .jsp, the first thing I need to know is how do I get it to the Controller. 

So you're passing a GET parameter in the browser (Request => Controller => View), how do I accept it inside the methods, how they get it to the method ?, how do I be able to print the value in here. A GET is nothing but a request parameter, so there's an annotation called @RequestParam. So we want to bind a @RequestParam, what's the type of the request parameter ?, name is a String.

**Note**. Keep in mind, that this is only an example. Ideally, we should use a logging framework to implement this functionality.

```
@Controller
public class LoginController {

	@RequestMapping("/login")
	public String loginMessage(@RequestParam String name){
		System.out.println("name is " + name);
		return "login";
	}
}
```

Now with the Controller, How do I make it available to the View ? in Spring MVC, we use something called a Model; **Model is used to pass data from Controller to View (JSP) in MVC pattern**. So when I want to send the data from the Controller to the View here in the LoginController class  I have a Controller and It wants to send some data to the View. How does it send it ? it sends it by putting in something called a Model. I need to put the name in the Model, a send it to the View login.jsp. That's how we would pass data from the Controller to the View through the Model.

These three parts, Model View and Controller combine to create what we call the MVC - Model View Contoller pattern. So the Controller controls the entire flow, once it has some data it puts it in the Model and redirects to the View, the View uses the Model to render the data on the screen. So now we have a name coming into the Contoller. Now I would want to put it in the Model, How I put it into the Model ?, before I put it into the model, I need to have the Model pass into the method. So the Model object in Spring is ModelMap. So all that I need to do is add a parameter called *ModelMap model* and Spring MVC would be making sure that a Model is created and made available in here. Now, all that I need to do is say *model.put("name", value);*; **with this intruction I put an attribute called name into the Model**.

```java
@Controller
public class LoginController {

	@RequestMapping("/login")
	public String loginMessage(@RequestParam String name, ModelMap model){
		//The name of the first argument at model.put (name,y), must be the same that we typed in the web browser: http://localhost:8080/login?name=charles
		model.put("name",name);
		return "login";
	}
}
```

Sring MVC would look at this method *public String loginMessage(@RequestParam String name, ModelMap model)* and it sees, OK there's a ModelMap here, so it creates a Model and makes it available here inside the method. What we are doing with *model.put* is we are putting a value called name in the model. Now, what we are putting in here in *model.put* is available to the .jsp view to whatever name value that is passed. So this **name** *model.put("**name**",name);*, will be available here in the login.jsp view in the expression ${name}.

```
<html>
	<head>
		<title>Works!!</title>
	</head>
	<body>
		My First JSP!!! Welcome ${name}
	</body>
</html>
```

Here we are reading the value form the Model and creating the HTML.  If we launch the application and type http://localhost:8080/login?name=charles in the web browser, we would see the next.

```
Web browser output:

My First JSP!!! Welcome charles
```

So now we are able to pass in a value from the Request to the Controller, to the View, and to pass the value from Controller to the View, we used something called a Model.

One of the things that we have done until now, is we have a controller, we have a login.jsp, we are passing values from the request, and those are accessible in the controller, and we are sending it to the view.

## Complete Code Example

##### /src/test/java/com/imh/springboot/controller/LoginController.java

```java
package com.imh.springboot.web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class LoginController {

	@RequestMapping("/login")
	public String loginMessage(@RequestParam String name, ModelMap model){
//		System.out.println("name is " + name);
		model.put("name",name);
		return "login";
	}
}
```

##### src/main/webapp/WEB-INF/jsp/login.jsp

```
<html>
	<head>
		<title>Works!!</title>
	</head>
	<body>
		My First JSP!!! Welcome ${name}
	</body>
</html>
```