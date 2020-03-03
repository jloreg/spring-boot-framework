## Step 01.2: Part 2 Pom.xml, Spring Boot Application and application properties

In the last step, we imported the created project from [Spring Initializr](https://start.spring.io/) into our eclipse workspace. If you expand the folders you'd see that there are three really important files which are present. One is pom.xml, SpringBootWebApplication class, and the other one is application.properties.

**First part.**

If you do double click and open pom.xml, and you go to the tab "pom.xml", you'll see the entire content of the pom.xml; **pom.xml contains Maven Configuration, and describes the characteristics of the project**. At the top of the pom.xml, there is a *<groupId>com.imh.springboot.web</groupId>* and *<artifactId>spring-boot-first-web-application</artifactId>* that we configured in the last step. **So groupId and artifactId of the project, are the name that we give to the project, and the name that we give to his package**.

```xml
<groupId>com.imh.springboot.web</groupId>
<artifactId>spring-boot-first-web-application</artifactId>
<version>0.0.1-SNAPSHOT</version>
<name>01-spring-boot-web-application</name>
<description>Web project for Spring Boot</description>
```

The other thing that you would see is a version *<version>0.0.1-SNAPSHOT</version>*, this SNAPSHOT in here **indicates that this is a version in development**, since we are working on this, the version which we give to this is 0.0.1-SNAPSHOT, that indicates that there's a project in development.

And we want to create a jar file. So that's basically the packaging, packaging says whether you'd want to create a jar file, a web archive, an enterprise archive, etc, what kind of application are you developing. Even we are developing a web application, we're using packaging of .Jar because that's one of the magic of SpringBoot. So SpringBoot comes up with a concept called an embedded web server. And therefore we are using packaging of jar, typically in web applications, we would use a packaging of ".war".**Because of some Spring magic which will look into a little later, we are putting a packaging of ".Jar", Java archive.**
 
Then you have the name, the description, but there are three other parts of pom.xml which are really important. One is called the parent. Here we're using *<artifactId>spring-boot-starter-parent</artifactId>*, parent is very similar to inheritance in Java, just like we use inheritance to inherit from one class to another, and here what we want to do is we want to use this Maven pom as the parent for us, we want to inherit a few things from it. So you can assume that *<artifactId>spring-boot-starter-parent</artifactId>*, is the parent for our pom files, and we get a lot of stuff from there.

```xml
<parent>
	<groupId>org.springframework.boot</groupId>
	<artifactId>spring-boot-starter-parent</artifactId>
	<version>2.2.5.RELEASE</version>
	<relativePath/> <!-- lookup parent from repository -->
</parent>

<! -- Characteristics of the project-->
<properties>
	<java.version>11</java.version>
	<project.build.sourceEncoding>UTF-8</project.build.sourceEncoding>
	<project.reporting.outputEncoding>UTF-8</project.reporting.outputEncoding>
</properties>
```

The next important thing that you'd see in the pom.xml is the dependencies. There are three important dependencies in here: spring-boot-starter-web, spring-boot-devtools, and spring-boot-starter-test.

```xml
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
</dependencies>
```

*Spring-boot-starter-web* is the starter for developing web applications. So if I want to develop any web application with Spring Boot, I would use <artifactId>spring-boot-starter-web</artifactId>. **So whether you would want to develop a web application or restful services, this would be the starter**.*Spring-boot-devtools* brings in features that make developing applications very productive. *Spring-boot-starter-test* help to us write good unit tests and integration tests.

As soon as you add a dependency into the dependencies section of your pom.xml, Maven automatically downloads that dependency for you. And also it would download some things called transitive dependencies. For example, Spring is the dependency for me, however, Spring might need to let's say another framework for it to be able to work. **That dependency is called transitive dependency**. So for my project to work I need Spring, and for Spring to work, let's say it's depending on log4j-over-slf4j.jar, I would need a slf4j. My project dependencies are now Spring, as well as it's transitive dependencies which is slf4j.

The last thing you'd see in the pom.xml is something called *<artifactId>spring-boot-maven-plugin</artifactId>*. Spring-boot-maven-plugin helps in easily running your SprigBoot applications, and also it helps in creating jar and war files out of SprigBoot web applications.

```xml
	<build>
		<plugins>
			<plugin>
				<groupId>org.springframework.boot</groupId>
				<artifactId>spring-boot-maven-plugin</artifactId>
			</plugin>
		</plugins>
	</build>
```
That's kind of the important stuff which is present in your pom.xml.

---

**Second part.**

For now let's focus on getting the application running. The second For now, let's focus on getting the application running. The second important file is SpringBootWebApplication.java. All that you'd see is a simple @SpringBootApplication annotation, and inside the main method, there's a being a SpringApplication.run that ran on this SpringBootWebApplication specific class. So those are the two important things that are present in the SpringBootWebApplication class.

@SpringBootApplication annotation is one of the most important annotations in SpringBoot. It does two things and is it initializers Spring Framework.

The first is Spring Boot is on top of Spring, so you have Spring, and top of that you have Spring Boot, so Spring Boot application first it initializers something called *component scan* in the Spring-Framework, so it basically initializes the Spring Framework.

The second thing it does is it initializes the Spring Boot itself. The most important feature in Spring Boot is something called Auto Configuration. **So this @SpringBootApplication annotation** **initializes** Spring Framework (**Component Scan**), **and** Spring Boot (**Auto configuration**). So this annotation initializes the Auto configuration of Spring Boot application.

SpringApplication.run launches a class (an application), that uses the @SpringBootApplication annotation. So If you give that class SpringBootWebApplication, to the SpringApplication.run, this would run this specific application SpringBootWebApplication.

```java
package com.imh.springboot.web;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class SpringBootWebApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringBootWebApplication.class, args);
	}
}
```

---

**Third part.**

The last file that is important is the application.properties located in /src/main/resources/application.properties, which has no values in there. This application.properties can be used as a configuration file. So let's say If I want to run the web application in port 9000 instead 8080 port (default), I can put a specific property value in here with the value 9000, and the SpringBoot application would start running on 9000 port. **So this is the kind of the configuration file for the entire application.**

Some of the important terminologies that we have learned as part of this particular step are: 

```
- Spring Boot Starter Parent
- Spring Boot Starter Web 
- The annotation @SpringBootApplication
- Something called Auto Configuration
```

In the subsequent steps, we'll look at each of these in detail and understand what features each of these is bringing to your Spring Boot application.