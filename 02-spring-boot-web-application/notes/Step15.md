## Step 15: Bootstrap for Page Formatting using webjars

What You Will Learn during this Step:

- Add bootstrap to give basic formatting to the page : We use bootstrap classes container,table and table-striped.
- We will use webjars
- Already auto configured by Spring Boot : o.s.w.s.handler.SimpleUrlHandlerMapping : Mapped URL path [/webjars/**] onto handler of type [class org.springframework.web.servlet.resource.ResourceHttpRequestHandler]
 
In this step, we would add a CSS framework called Bootstrap. If we look at the application developed until now, it's not really well formatted, so we'll try to make it better using a framework called Bootstrap to give some basic formatting to the page. We'll use a basic Bootstrap classes which will look at a little later, and also what we would do, is we would use a concept called webjars.

One of the things that you will want to understand further is the concept called webjars. One of the things that you already know is for example, let's say I want to use SpringFramework in my project, all that I need to do is include the dependency, and the .jar would be downloaded for me. But typically, how the javascript files was used earlier, was we needed to directly download the javascript files and put it in a folder called javascript in here src/main/java/webapp/WEB-INF/.

For example, I would want to use JQuery, of if I want to use some CSS file, typically how it was done, is you downloaded from the internet, download the jquery.css or let's say bootstrap.css, you downloaded from the internet, and then you would put it in a folder in your workspace. What if you wanted to upgrade to the next version of JQuery? Then, what you have to do is you have to download it again yourself and do it all over again.

That's where the concept of webjars come in. Webjars are basically your's static things, so static things are typically your CSS and javascript files, these are zipped as a .jar and made it available in the Maven repository. We can version them, similar to your Spring. So If I want to upgrade the version of them, I can directly go to my Pom.xm and change the version of my project -If I want to use 1.4.3 instead of 1.4.2, I only need to type in pom.xml-, 

Similar to that, the webjars brings that functionality for the static files which are your javascript and css files. There are the dependencies that are present next above, which are the webjars for bootstrap and the webjars for jquery.

##### pom.xml

```
<!-- To enable Bootsrap & JQuery -->
 <dependency>
         <groupId>org.webjars</groupId>
        <artifactId>bootstrap</artifactId>
        <version>3.3.6</version>
 </dependency>
 <dependency>
       <groupId>org.webjars</groupId>
       <artifactId>jquery</artifactId>
       <version>1.9.1</version>
 </dependency>
```

One of the things I'll do is change the level of application.properties to info level. I would want to look at some of the things in the log Debug. 

##### src/main/resources/application.properties
```
logging.level.org.springframework.web=info
```

One of the things you already see when your re-start the application, is that there's a URL of webjars which is already mapped. So there is a mapped URL path to webjars on to handler of type *Mapped URL path [/webjars/**] onto handler of type [org.springframework.web.servlet.resource.ResourceHttpRequestHandler]*, so anything request that is starting with the URL [/webjars/**] it would be handled by this particular controller. In a typical Spring MVC application, this is something which we would need to configure, but with Spring Boot, think is Auto-Configuration takes care of it. So as soon as I added in the *spring-boot-starter-web* project, I get the webjars configuration for free, so I don't really need to do anything to get that working.

To be able to use Bootstrap, I would need JQuery, so that's the reason why we included bootstrap and the Jquery in the pom.xml. To be able to use these in the list-todos.jsp, we would need to give a link in here. What I want to do is use the bootstrap and Jquery in my list-todo.jsp page. So how do I do that ? One of the important things is I would add all the javascript files at the bottom of the page, that's one of the best HTML practices. Just above the </body> we can include our javascript files in. We will use the minimized option of bootstrap and jquery that are here in the bootstrap-3.3.6.jar and jquery-1.9.1.jar in the pom.xml.

```
<script src="webjars/jquery/1.9.1/jquery.min.js"></script>
<script src="webjars/bootstrap/3.3.6/js/bootstrap.min.js"></script>
<link href="webjars/bootstrap/3.3.6/css/bootstrap.min.css" rel="stylesheet">
```

Copy the two first things at the bottom of the page, and the css stylessheet just below the title in the head. This is a good practise to include the CSS in the header and the javascript just below just about the body. The javascript's can be loaded as late as possible and the CSS is assessed to be loaded as early as possible.

To use bootstrap what we need to do, is we'll need to do is include one CSS file which is the bootstrap.min.css, and we need two javascript files, those are the bootstrap.min.js and jquery.min.js. So we included all these three things in our application.

##### /src/main/webapp/WEB-INF/jsp/list-todos.jsp
```
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<html>
	<head>
		<title>Todo's for ${name}</title>				<!-- Added: it's a good practise load the CSS stylesheet at the top of the page-->
		<link href="webjars/bootstrap/3.3.6/css/bootstrap.min.css" rel="stylesheet">
	</head>
	<body>
		<!-- <H1>Your Todos</H1> -->
		<div class="container">						<!-- Added: Bootstrap container class -->
			<table class="table table-striped">		<!-- Added: Bootstrap tab class -->
				<caption> Your todos are </caption>
				<thead>
					<tr>
						<th>Description</th>
						<th>Target Date</th>
						<th>Is it Done?</th>
					</tr>
				</thead>
				<tbody>
					<!-- JSTL For Loop -->
					<c:forEach items="${todos}" var="todo">
						<tr>
							<th>${todo.desc}</th>
							<th>${todo.targetDate}</th>
							<th>${todo.done}</th>
						</tr>
					</c:forEach>
				</tbody>
			</table>
			<div>									<!-- Added: Bootstrap button class-->
				<a class="button" href="/add-todo">Add a Todo</a>
			</div>
													<!-- Added: Bootstrap and JQuery javascript files to be loaded-->
			<script src="webjars/jquery/1.9.1/jquery.min.js"></script>	
			<script src="webjars/bootstrap/3.3.6/js/bootstrap.min.js"></script>		
		</div>
	</body>

</html>
```

What we were looking in this specific step was how we can use our page bootstrap to format our page better. In this step we added in dependencies for JQuery and Bootstrap, we used webjars for them, and we included links for them in our list-todo.jsp view. 

What we made use of are some of the classes from Bootstrap, bootstrap is an awesome framework.  I mean, You just go and google bootstrap, you can go to https://getbootstrap.com/ and you'll find a lot of documentation in here. What we're really touching it's only the top of the iceberg for bootstrap, there are a lot of other formatting options that bootstrap provides which we are not going to cover in this steps at all.

Basically what we are looking is, what kind of functionality bootstrap provides. Bootstrap is basically a CSS framework which helps us to format stuff in a very easy way, so you can see that just by adding a couple of simple classes, we can format our page very well. In the subsequent steps, we'll make use of a few more classes from Bootstrap.

## Complete Code Example

##### pom.xml

```java
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
        
		<!-- To enable JSTL Tags! -->
        <dependency>
            <groupId>javax.servlet</groupId>
            <artifactId>jstl</artifactId>
        </dependency>
		
		<!-- To enable Bootsrap & JQuery -->
        <dependency>
            <groupId>org.webjars</groupId>
            <artifactId>bootstrap</artifactId>
            <version>3.3.6</version>
        </dependency>
        <dependency>
            <groupId>org.webjars</groupId>
            <artifactId>jquery</artifactId>
            <version>1.9.1</version>
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

</project>
```

##### /src/main/webapp/WEB-INF/jsp/list-todos.jsp

```
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<html>
	<head>
		<title>Todo's for ${name}</title>							 <!-- Added: -->
		<link href="webjars/bootstrap/3.3.6/css/bootstrap.min.css" rel="stylesheet">
	</head>
	<body>
		<!-- <H1>Your Todos</H1> -->
		<div class="container">								<!-- Added: Bootstrap container class -->
			<table class="table table-striped">				<!-- Added: Bootstrap tab class -->
				<caption> Your todos are </caption>
				<thead>
					<tr>
						<th>Description</th>
						<th>Target Date</th>
						<th>Is it Done?</th>
					</tr>
				</thead>
				<tbody>
					<!-- JSTL For Loop -->
					<c:forEach items="${todos}" var="todo">
						<tr>
							<th>${todo.desc}</th>
							<th>${todo.targetDate}</th>
							<th>${todo.done}</th>
						</tr>
					</c:forEach>
				</tbody>
			</table>
			<div>													<!-- Added: Bootstrap button class-->
				<a class="button" href="/add-todo">Add a Todo</a>
			</div>

			<script src="webjars/jquery/1.9.1/jquery.min.js"></script>	
			<script src="webjars/bootstrap/3.3.6/js/bootstrap.min.js"></script>		
		</div>
	</body>

</html>
```