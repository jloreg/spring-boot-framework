## Step 02.1: Part 1 First Spring MVC Controller, @ResponseBody, @Controller

What You Will Learn during this Step:

- http://localhost:8080/login
- @RequestMapping(value = "/login", method = RequestMethod.GET)
- Why @ResponseBody?
- Important of RequestMapping method 
- How do web applications work? Request and Response
	- Browser sends Http Request to Web Server
	- Code in Web Server => Input:HttpRequest, Output: HttpResponse
	- Web Server responds with Http Response

**First part**

If I type http://localhost:8080/login without any controller implementation in our SpringBootWebApplication, this is the default error page that's provided by Spring Boot by default. So it's saying the application has no explicit mapping for "/login", so you're seeing this as default.

```
Whitelabel Error Page

This application has no explicit mapping for /error, so you are seeing this as a fallback.
Tue Mar 03 10:27:10 CET 2020
There was an unexpected error (type=Not Found, status=404).
No message available
```

**Second part**

To solve the problem presented in the last part, you must implement a controller that intercepts the GET Request "/login" and render a simple welcome page.

If I type /login => I want a "Hello World" message back. So how do we do that? the way do that in Spring MVC is would need to map "/login" to a Java class. So the Java class which we would be creating would be called a Controller. So we would need to map "/login" to our controller. And because the controller is going to handle the "/login", I would need to call this as LoginController; **the name must reflect what it's doing**

```java
package com.imh.springboot.web.controller;

@Controller //This particular class needs to be picked up by Spring to be loaded. To be loaded, you must add this annotation.
public class LoginController {

	@RequestMapping("/login")
	public String loginMessage(){
		return "Hello World";
	}
}
```
Only If you put @Controller in the LoginController class, it would pick up the methods in the class as a request mappings. So what's is saying is I'm mapping "/login" into a method *public String loginMessage()* inside the log in control. 

But if now I type localhost:8080/login on the web browser, this is what shows Spring Boot.

```
Whitelabel Error Page

This application has no explicit mapping for /error, so you are seeing this as a fallback.
Tue Mar 03 12:41:39 CET 2020
There was an unexpected error (type=Not Found, status=404).
No message available
```

**Third part**

To try to understand why the application doesn't show you the expected results, we are going to debug the application to find the root of the problem.

I want to find out what's happening in the background when I type "/login", but still there is nothing in the console. **By default Spring Boot logs at logging level of info**, but what we want to do is we would want to log more information. So what we would need to do, is we need to change the logging level to something called DEBUG.

So when I say DEBUG, there will be a lot of log that is being printed. So instead of sitting generically everything to debug, what I'll do is only to this web framework. So what I'll do is only org.springframework.web. This is the package where the Spring MVC logic is present, all the Spring MVC things are in this specific package. Only for this package alone, what we'll do is we'll change the logging to debug -the thing is they'll be a lot of information that is printed in the log which is unnecessary-, **so only for a specific package logging.level.com.imh.springboot.web**, we'll change it to debug level.

So how do we do that? How do we change the logging level to debug for our *org.springframework.web*. The way we do that is we'll go to the application.properties and add a simple property.

```
# INFO by default org.springframework.web => Spring MVC
#logging.level.com.imh.sprinframework.web=debug
logging.level.org.springframework.web=debug
```
I launch the application.

```
Console output:

[2m2020-03-03 18:04:34.794[0;39m [32m INFO[0;39m [35m4414[0;39m [2m---[0;39m [2m[  restartedMain][0;39m [36mc.i.s.web.SpringBootWebApplication      [0;39m [2m:[0;39m Starting SpringBootWebApplication on laptop with PID 4414 (/home/jon/spring-workspace/spring-boot-framework/01-spring-boot-web-application/target/classes started by jon in /home/jon/spring-workspace/spring-boot-framework/01-spring-boot-web-application)
[2m2020-03-03 18:04:34.807[0;39m [32m INFO[0;39m [35m4414[0;39m [2m---[0;39m [2m[  restartedMain][0;39m [36mc.i.s.web.SpringBootWebApplication      [0;39m [2m:[0;39m No active profile set, falling back to default profiles: default
[2m2020-03-03 18:04:34.857[0;39m [32m INFO[0;39m [35m4414[0;39m [2m---[0;39m [2m[  restartedMain][0;39m [36m.e.DevToolsPropertyDefaultsPostProcessor[0;39m [2m:[0;39m Devtools property defaults active! Set 'spring.devtools.add-properties' to 'false' to disable
[2m2020-03-03 18:04:34.857[0;39m [32m INFO[0;39m [35m4414[0;39m [2m---[0;39m [2m[  restartedMain][0;39m [36m.e.DevToolsPropertyDefaultsPostProcessor[0;39m [2m:[0;39m For additional web related logging consider setting the 'logging.level.web' property to 'DEBUG'
[2m2020-03-03 18:04:35.470[0;39m [32m INFO[0;39m [35m4414[0;39m [2m---[0;39m [2m[  restartedMain][0;39m [36mo.s.b.w.embedded.tomcat.TomcatWebServer [0;39m [2m:[0;39m Tomcat initialized with port(s): 8080 (http)
[2m2020-03-03 18:04:35.475[0;39m [32m INFO[0;39m [35m4414[0;39m [2m---[0;39m [2m[  restartedMain][0;39m [36mo.apache.catalina.core.StandardService  [0;39m [2m:[0;39m Starting service [Tomcat]
[2m2020-03-03 18:04:35.475[0;39m [32m INFO[0;39m [35m4414[0;39m [2m---[0;39m [2m[  restartedMain][0;39m [36morg.apache.catalina.core.StandardEngine [0;39m [2m:[0;39m Starting Servlet engine: [Apache Tomcat/9.0.31]
[2m2020-03-03 18:04:35.511[0;39m [32m INFO[0;39m [35m4414[0;39m [2m---[0;39m [2m[  restartedMain][0;39m [36mo.a.c.c.C.[Tomcat].[localhost].[/]      [0;39m [2m:[0;39m Initializing Spring embedded WebApplicationContext
[2m2020-03-03 18:04:35.511[0;39m [32mDEBUG[0;39m [35m4414[0;39m [2m---[0;39m [2m[  restartedMain][0;39m [36mo.s.web.context.ContextLoader           [0;39m [2m:[0;39m Published root WebApplicationContext as ServletContext attribute with name [org.springframework.web.context.WebApplicationContext.ROOT]
[2m2020-03-03 18:04:35.511[0;39m [32m INFO[0;39m [35m4414[0;39m [2m---[0;39m [2m[  restartedMain][0;39m [36mo.s.web.context.ContextLoader           [0;39m [2m:[0;39m Root WebApplicationContext: initialization completed in 654 ms
[2m2020-03-03 18:04:35.612[0;39m [32m INFO[0;39m [35m4414[0;39m [2m---[0;39m [2m[  restartedMain][0;39m [36mo.s.s.concurrent.ThreadPoolTaskExecutor [0;39m [2m:[0;39m Initializing ExecutorService 'applicationTaskExecutor'
[2m2020-03-03 18:04:35.617[0;39m [32mDEBUG[0;39m [35m4414[0;39m [2m---[0;39m [2m[  restartedMain][0;39m [36ms.w.s.m.m.a.RequestMappingHandlerAdapter[0;39m [2m:[0;39m ControllerAdvice beans: 0 @ModelAttribute, 0 @InitBinder, 1 RequestBodyAdvice, 1 ResponseBodyAdvice
[2m2020-03-03 18:04:35.641[0;39m [32mDEBUG[0;39m [35m4414[0;39m [2m---[0;39m [2m[  restartedMain][0;39m [36ms.w.s.m.m.a.RequestMappingHandlerMapping[0;39m [2m:[0;39m 3 mappings in 'requestMappingHandlerMapping'
[2m2020-03-03 18:04:35.653[0;39m [32mDEBUG[0;39m [35m4414[0;39m [2m---[0;39m [2m[  restartedMain][0;39m [36mo.s.w.s.handler.SimpleUrlHandlerMapping [0;39m [2m:[0;39m Patterns [/webjars/**, /**] in 'resourceHandlerMapping'
[2m2020-03-03 18:04:35.658[0;39m [32mDEBUG[0;39m [35m4414[0;39m [2m---[0;39m [2m[  restartedMain][0;39m [36m.m.m.a.ExceptionHandlerExceptionResolver[0;39m [2m:[0;39m ControllerAdvice beans: 0 @ExceptionHandler, 1 ResponseBodyAdvice
[2m2020-03-03 18:04:35.696[0;39m [32m INFO[0;39m [35m4414[0;39m [2m---[0;39m [2m[  restartedMain][0;39m [36mo.s.b.d.a.OptionalLiveReloadServer      [0;39m [2m:[0;39m LiveReload server is running on port 35729
[2m2020-03-03 18:04:35.724[0;39m [32m INFO[0;39m [35m4414[0;39m [2m---[0;39m [2m[  restartedMain][0;39m [36mo.s.b.w.embedded.tomcat.TomcatWebServer [0;39m [2m:[0;39m Tomcat started on port(s): 8080 (http) with context path ''
[2m2020-03-03 18:04:35.727[0;39m [32m INFO[0;39m [35m4414[0;39m [2m---[0;39m [2m[  restartedMain][0;39m [36mc.i.s.web.SpringBootWebApplication      [0;39m [2m:[0;39m Started SpringBootWebApplication in 1.185 seconds (JVM running for 1.722)
```

You'd see that there are already certain things that are printed in debug mode on the console (o.s.w - org.springframework.web). That's basically the shortcut for that, so our *org.springframework.web* is being now printed in the debug mode. If I clear the console and type http://localhost:8080/login into the web browser, that's basically what shown the console output.

```
Console output:

[2m2020-03-03 18:11:23.367[0;39m [32mDEBUG[0;39m [35m4414[0;39m [2m---[0;39m [2m[nio-8080-exec-3][0;39m [36mo.s.web.servlet.DispatcherServlet       [0;39m [2m:[0;39m GET "/login", parameters={}
[2m2020-03-03 18:11:23.368[0;39m [32mDEBUG[0;39m [35m4414[0;39m [2m---[0;39m [2m[nio-8080-exec-3][0;39m [36ms.w.s.m.m.a.RequestMappingHandlerMapping[0;39m [2m:[0;39m Mapped to com.imh.springboot.web.controller.LoginController#loginMessage()
[2m2020-03-03 18:11:23.369[0;39m [32mDEBUG[0;39m [35m4414[0;39m [2m---[0;39m [2m[nio-8080-exec-3][0;39m [36mo.s.w.s.v.ContentNegotiatingViewResolver[0;39m [2m:[0;39m Selected 'text/html' given [text/html, application/xhtml+xml, image/webp, application/xml;q=0.9, */*;q=0.8]
[2m2020-03-03 18:11:23.370[0;39m [32mDEBUG[0;39m [35m4414[0;39m [2m---[0;39m [2m[nio-8080-exec-3][0;39m [36mo.s.w.servlet.view.InternalResourceView [0;39m [2m:[0;39m View name 'Hello World', model {}
[2m2020-03-03 18:11:23.370[0;39m [32mDEBUG[0;39m [35m4414[0;39m [2m---[0;39m [2m[nio-8080-exec-3][0;39m [36mo.s.w.servlet.view.InternalResourceView [0;39m [2m:[0;39m Forwarding to [Hello World]
[2m2020-03-03 18:11:23.370[0;39m [32mDEBUG[0;39m [35m4414[0;39m [2m---[0;39m [2m[nio-8080-exec-3][0;39m [36mo.s.web.servlet.DispatcherServlet       [0;39m [2m:[0;39m "FORWARD" dispatch for GET "/Hello World", parameters={}
[2m2020-03-03 18:11:23.371[0;39m [32mDEBUG[0;39m [35m4414[0;39m [2m---[0;39m [2m[nio-8080-exec-3][0;39m [36mo.s.w.s.handler.SimpleUrlHandlerMapping [0;39m [2m:[0;39m Mapped to ResourceHttpRequestHandler ["classpath:/META-INF/resources/", "classpath:/resources/", "classpath:/static/", "classpath:/public/", "/"]
[2m2020-03-03 18:11:23.373[0;39m [32mDEBUG[0;39m [35m4414[0;39m [2m---[0;39m [2m[nio-8080-exec-3][0;39m [36mo.s.w.s.r.ResourceHttpRequestHandler    [0;39m [2m:[0;39m Resource not found
[2m2020-03-03 18:11:23.373[0;39m [32mDEBUG[0;39m [35m4414[0;39m [2m---[0;39m [2m[nio-8080-exec-3][0;39m [36mo.s.web.servlet.DispatcherServlet       [0;39m [2m:[0;39m Exiting from "FORWARD" dispatch, status 404
[2m2020-03-03 18:11:23.374[0;39m [32mDEBUG[0;39m [35m4414[0;39m [2m---[0;39m [2m[nio-8080-exec-3][0;39m [36mo.s.web.servlet.DispatcherServlet       [0;39m [2m:[0;39m Completed 404 NOT_FOUND
[2m2020-03-03 18:11:23.374[0;39m [32mDEBUG[0;39m [35m4414[0;39m [2m---[0;39m [2m[nio-8080-exec-3][0;39m [36mo.s.web.servlet.DispatcherServlet       [0;39m [2m:[0;39m "ERROR" dispatch for GET "/error", parameters={}
[2m2020-03-03 18:11:23.374[0;39m [32mDEBUG[0;39m [35m4414[0;39m [2m---[0;39m [2m[nio-8080-exec-3][0;39m [36ms.w.s.m.m.a.RequestMappingHandlerMapping[0;39m [2m:[0;39m Mapped to org.springframework.boot.autoconfigure.web.servlet.error.BasicErrorController#errorHtml(HttpServletRequest, HttpServletResponse)
[2m2020-03-03 18:11:23.376[0;39m [32mDEBUG[0;39m [35m4414[0;39m [2m---[0;39m [2m[nio-8080-exec-3][0;39m [36mo.s.w.s.v.ContentNegotiatingViewResolver[0;39m [2m:[0;39m Selected 'text/html' given [text/html, text/html;q=0.8]
[2m2020-03-03 18:11:23.377[0;39m [32mDEBUG[0;39m [35m4414[0;39m [2m---[0;39m [2m[nio-8080-exec-3][0;39m [36mo.s.web.servlet.DispatcherServlet       [0;39m [2m:[0;39m Exiting from "ERROR" dispatch, status 404
```

You can see what's happening. So there is something called DispatcherServlet which is being used, so what this DispatcherServlet would do is do the mapping. So the "/login" that you types in the web browser it maps to the LoginController in *public String loginMessage()*, so that's basically what the DispatcherServlet does.  It starts looking for a view, view is something like a .jsp or something which kind of shows something on the screen. DispatcherServlet it gets "/login" mapped to LoginController, and LoginController returns "Hello World". Once LoginController returns 'Hello World', the DispatcherServlet start's looking for a view with the name 'Hello World'. Because we don't have anything of that kind right now, it says "I'm not able to find anything, so show an error page". That's basically what's happening.

How do I prevent the Dispatcher Servlet from looking for a view? I don't want to look for a view, I don't want to look for a .jsp, all that I would want is, I want that the DispatcherServlet to just go and return whatever value I'm giving it back to the browser. How I do that? there is a simple addition called an @ResponseBody, so you just need to add in @ResponseBody on this specific method *public String loginMessage()*, where I just want to return the string back.

There is a lot more functionality to @ResponseBody, it can actually work with whatever you are writing, either .json, XML, or similar things like that through something called message converter. Here, all that we are writing is a simple string "Hello World", we wanted to go to the browser, so we added in a @ResponseBody.

```java
@Controller
public class LoginController {

	@RequestMapping("/login")
	@ResponseBody	//Necessary if you don't add a specific web page
	public String loginMessage(){
		return "Hello World";
	}
}
```
So now if I restart the server and refresh the page http://localhost:8080/login/ again, the web browser shows:

```
Output web browser:

Hello World
```

**Resume**

Until now what we have done in this step, is we wanted to map this "/login" to our URL, and we want to show the message that it returns "Hello World Modified" on the screen. That' what we focus in this step. What we had to do was we had to create a LoginController, we wad to put an @Controller annotation so that class is picked up, and then we created a method returning that "Hello World Modified" as the String, and also we added in @RequestMapping("/login") to map "/login" URL to the method *public String loginMessage*, and we have to add an @ResponseBody annotation so that this message "Hello World Modified" which is returned back is returned as it is to the screen.

Those are the things that we discussed in the practical part of this particular step. In the theory part of this step, we will look at a little bit more about how web application work, what is request, what is response,  and things like that.

## Complete Code Example

##### /src/main/resources/application.properties
```
#logging.level.com.imh.sprinframework.web=debug
logging.level.org.springframework.web=debug
```

##### /src/main/java/com/imh/springboot/web/controller/LoginController.java

```java
package com.imh.springboot.web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class LoginController {

	@RequestMapping("/login")
	@ResponseBody
	public String loginMessage(){
		return "Hello World";
	}
}
```