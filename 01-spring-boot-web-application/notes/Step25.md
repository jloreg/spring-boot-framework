## Step 25: Exception Handling

What we will do:

- Basic Exception Handling
- Exception Handling is a cross-cutting concern
- Do not handle exceptions in Controllers or Services, if you cannot add value to them.
- Bit of refactoring on the controllers
- Whitelabel Error Page provided by default by Spring Boot
- You can see a few details of the errors
- We can customize if we would want to
- @ControllerAdvice and Controller Specific Exception Handling
- Handling Errors thrown from Views

We look at how to improve the exception handling and create a custom exception handler ErrorController class right now. It's a typical controller, however, I would want this to be handling errors, so I'd say @Controller("error") to get say Spring that this is an error controller, I'm adding a keyword called "error". 

In here, I can add handlers for specific exceptions. How can I add handlers for specific exceptions? by using the annotation @ExceptionHandler and specify which kind of exception you would want to handle. Let's say I want to handle any Exception.class, and you can pass in any parameters that you would want. In here, let's pass in the request and response.

Instead of using the default error page that the SpringBoot is providing by default, we would want to use a custom error page of our own, what we can do is create a new ModelAndView object, and adding the exception as an object to the model, etc.

```java
@Controller("error")
public class ErrorController {

    @ExceptionHandler(RuntimeException.class)
    public ModelAndView handleException (HttpServletRequest request, HttpServletResponse response, Exception ex) {
        ModelAndView mv = new ModelAndView();
        mv.addObject("exception", ex.getStackTrace());  //#1
        mv.addObject("url", request.getRequestURL());   //#1
        mv.setViewName("error");	//error.jsp page to redirect
        return mv;
    }
}
```

Basically what we are doing in here, is we are creating a very simple thing, we have not to use this response, and the response from here. So what we have created is a simple method called handle exception which gives the request details and the exception details which happen when the exception is thrown, it would come the country comes here, we are putting a couple of values in the model, and we are redirecting the user to a error page. The way we have been doing until now, is we passed in a model map in here and we added objects to the model, and return the view name. There is another way we can do that, so I'm creating a ModelAndView and adding everything here in handleException method to the model

When an exception ocurred, you can add in a few more details, maybe a ID to the exception and things like that in error.jsp page. The details we have here in *public ModelAndView handleException* method, are the "exception" and the "url", probably we can actually put them in the log, or we can actually even put them to the database, so I can store all this information that we have to the database (see #1). So that when a user contacts the support he goes into this database, I'm using my userID "imh", and there was an error, and if we store this information to the database, helps this user go to the database, see the exception and possibly provide help.

The HelpDesk user is not able to provide support, he would redirect the query to the developer who would be able to help him. The most important thing is that the developer has all the information now. If you capture the exception that you are well and all kind of stuff and put it to the database, you have all the information that he would need to fix the exception.

**Resume**

In this step what we looked, was the default exception handling which was provided by Spring Boot. We started with that, we saw that Spring Boot by default provided a good exception handling, a good default exception handling, and then we saw how to costumize the exception handling by creating an error page of our own.

## Complete Code Example

##### /src/main/java/com/imh/springboot/web/controller/ErrorController.java

``` java
package com.imh.springboot.web.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;

@Controller("error")
public class ErrorController {

	@ExceptionHandler(RuntimeException.class)
	public ModelAndView handleException (HttpServletRequest request, HttpServletResponse response, Exception ex) {
		ModelAndView mv = new ModelAndView();
		mv.addObject("exception", ex.getStackTrace());	//#1
		mv.addObject("url", request.getRequestURL());	//#1
		mv.setViewName("error");
		return mv;
	}
	
}
```

##### /src/main/webapp/WEB-INF/jsp/error.jsp

``` xml
	<%@ include file="common/header.jspf" %>
	<%@ include file="common/navigation.jspf" %>
	<div class="container" >
			An exception ocurred! Please contact Support!
	</div>
	<%@ include file="common/footer.jspf" %>
```