## Step 04: Redirect to Login JSP - @ResponseBody and View Resolver

What You Will Learn during this Step:

- Your First JSP
- There is a bit of setup before we get there!
- Introduction to View Resolver

**Notes**

```
Dispatcher Servlet

1. I type localhost:8080/login/ in the web browser =>  The DispatcherServlet process the GET request and goes to the LoginController.
2. LoginContoller returns back the string "login" => The DispatcherServlet process the response and search for a view named "login" in the default path of our project.
3. If the ViewResolver is configured correctly in application.properties, the DispatcherServlet finds the view "login" in the specified path src/main/webapp/WEB-INF/jsp/login.jsp 
```

What we want in this step is to use a view. Java is good at writing business logic, but if you want to do some Html stuff, if you want to create an Html containing your view information, then Java is not the right place. All that kind of logic should be in a view, either are .jsp pages or some of the templating languages. In this course we'll be using .jsp as the view, so what will do in this specific step is really create a .jsp, and we have .jsp rendered the view.

**First part**

The best place to create a .jsp is src/main/webapp/WEB-INF/jsp/X.jsp. If you create a .jsp in this route, then it' s kind of the more secure way of doing it. So, we started creating a login.jsp and his respective folders in the path src/main/webapp/WEB-INF/jsp/.

```
<html>
	<head>
		<title>Works!!</title>
	</head>
	<body>
		My First JSP!!!
	</body>
</html>
```

Now, I don't want this LoginController returns this message "Hello World" back, I would want it to redirect to the "login" view, the way we do that is by saying return "login";. 

```
package com.imh.springboot.web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class LoginController {

	@RequestMapping("/login")
//	@ResponseBody	//Not necessary when we add a file (.jsp, .html, .json), to be send as response
	public String loginMessage(){
//		return "Hello World";
		return "login";
	}
}
```

**Second part**

If you stop and restart the application, you would see the next.

```
Web browser output:

login
```

That it's not redirecting to the login.jsp page, but it's actually printing "login" in the web browser. What's happening? We said /login => is now returning back the string "login" in the web browser. 

The Dispatcher Servlet says that the request is coming to /login, and it sends the request to LoginController, next loginMessage method because /login is mapped to it with the annotation @RequestMapping("/login"), and this returns "login" back to the DispatcherServlet. So DispatcherServlet gets this "login" string along with the fact, that's @ResponseBody. What happens If it @ResponseBody ? Dispatcher Servlet would directly written this content back (String "login"), to the browser, as part to the response.

We don't want that to happen. How do I do that, I remove the @ResponseBody. You would see that the application has restarted, so let's go to see the browser.

```
Web browser output:

Whitelabel Error Page

This application has no explicit mapping for /error, so you are seeing this as a fallback.
Wed Mar 04 10:58:18 CET 2020
There was an unexpected error (type=Not Found, status=404).
/WEB-INF/jsp/Hello World.jsp
```

You see that the application has restarted, and I'm getting a white label error page. The message says that "this application has no explicit mapping for /error". What is happening is we are returning /login back in the method *public String loginMessage()*, and explicitly saying to the DispatcherServlet that must go to the view that we specified in the return instruction (return "login";). So what's happening here is we are saying: when we type /login => go to the "login" view, and the DispatcherServlet search for a view named "login" but doesn't know that this view is in this particular folder *src/main/webapp/WEB-INF/jsp/*.

How do I tell the DispatcherServlet that the "login" view is in this specific part src/main/webapp/WEB-INF/jsp/*. That's where a concept called ViewResolver comes into the picture. When you provide a view name to the DispatcherServlet, it uses something called View Resolver to resolve it. So now I need to find some way of mapping.

In Spring Boot we would do that in /src/main/resources/application.properties by configuring a View Resolver. I would want to "login" redirect to this => src/main/webapp/WEB-INF/jsp/login.jsp. 

One of the important things with web applications is that src/main/webapp/ is kind of the default folder, so I don't specify that, so I can remove src/main/webapp, so this is the folder that I would need to redirect to /WEB-INF/jsp/login.jsp. So this /WEB-INF/jsp/ is the prefix, and suffix is this .jsp.

```
spring.mvc.view.prefix=/WEB-INF/jsp/
spring.mvc.view.suffix=.jsp
#logging.level.com.imh.sprinframework.web=debug
logging.level.org.springframework.web=debug
```

**Third part**

Due to we are using embedded Tomcat, the additional thing that we would need to provide to works with .jsp is the appropriate dependency.
So, we enable the .jsp support in the embedded Tomcat server by adding a provided dependency *tomcat-embed-jasper* in the pom.xml

```xml
<!-- To enable .jsp support in embedded Tomcat server! -->
	<dependency>
            <groupId>org.apache.tomcat.embed</groupId>
            <artifactId>tomcat-embed-jasper</artifactId>
            <scope>provided</scope>
     </dependency>
```

Now, if you launch the application, you'll see that finally, the web browser shows the expected result in the form of the view login.jsp.

```
Web browser output:

 My First JSP!!! 
```

**Resume**

We started creating a .jsp in the path src/main/webapp/WEB-INF/jsp/login.jsp. Next, we created a view prefix and view suffix, so that the ViewResolver knows where to search for the .jsp. And next, we enable the .jsp support in the embedded Tomcat server by adding a provided dependency on *tomcat-embed-jasper*. Those are the things that we need to get our .jsp working.

We were sending a request we got an HTML response back, and the browser renders that on the screen

## Complete Code Example

##### pom.xml

```
<?xml version="1.0" encoding="UTF-8"?>
<project xmlns="http://maven.apache.org/POM/4.0.0" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
	xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 https://maven.apache.org/xsd/maven-4.0.0.xsd">
	<modelVersion>4.0.0</modelVersion>
	<parent>
		<groupId>org.springframework.boot</groupId>
		<artifactId>spring-boot-starter-parent</artifactId>
		<version>2.2.5.RELEASE</version>
		<relativePath/> <!-- lookup parent from repository -->
	</parent>
	<groupId>com.imh.springboot.web</groupId>
	<artifactId>spring-boot-first-web-application</artifactId>
	<version>0.0.1-SNAPSHOT</version>
	<name>01-spring-boot-web-application</name>
	<description>Web project for Spring Boot</description>

	<properties>
		<java.version>11</java.version>
		<project.build.sourceEncoding>UTF-8</project.build.sourceEncoding>
		<project.reporting.outputEncoding>UTF-8</project.reporting.outputEncoding>
	</properties>

	<dependencies>
		<dependency>
			<groupId>org.springframework.boot</groupId>
			<artifactId>spring-boot-starter-web</artifactId>
		</dependency>

		<dependency>
			<groupId>org.springframework.boot</groupId>
			<artifactId>spring-boot-devtools</artifactId>
			<scope>runtime</scope>
			<optional>true</optional>
		</dependency>
		<dependency>
			<groupId>org.springframework.boot</groupId>
			<artifactId>spring-boot-starter-test</artifactId>
			<scope>test</scope>
			<exclusions>
				<exclusion>
					<groupId>org.junit.vintage</groupId>
					<artifactId>junit-vintage-engine</artifactId>
				</exclusion>
			</exclusions>
		</dependency>
		  
		<!-- To enable .jsp support in embedded Tomcat server! -->
		<dependency>
            <groupId>org.apache.tomcat.embed</groupId>
            <artifactId>tomcat-embed-jasper</artifactId>
            <scope>provided</scope>
        </dependency>
		
	</dependencies>

	<build>
		<plugins>
			<plugin>
				<groupId>org.springframework.boot</groupId>
				<artifactId>spring-boot-maven-plugin</artifactId>
			</plugin>
		</plugins>
	</build>
</
```

##### src/main/webapp/WEB-INF/jsp/login.jsp

```
<html>
	<head>
		<title>Works!!</title>
	</head>
	<body>
		My First JSP!!!
	</body>
</html>
```

##### /src/main/resources/application.properties

```
spring.mvc.view.prefix=/WEB-INF/jsp/
spring.mvc.view.suffix=.jsp
#logging.level.com.imh.sprinframework.web=debug
logging.level.org.springframework.web=debug
```

##### /src/test/java/com/imh/springboot/controller/LoginController.java

```java
package com.imh.springboot.web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class LoginController {

	@RequestMapping("/login")
//	@ResponseBody	//Not necessary when we add a file (.jsp, .html, .json), to be send as response
	public String loginMessage(){
		return "login";
	}
}
```