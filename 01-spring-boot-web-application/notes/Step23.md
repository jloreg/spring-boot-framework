## Step 23: Initial Spring Security Setup

- Get Setup for Spring Security

I created a simple class SecurityConfiguration, one of the things is we would want this to be picked up as the configuration file. So how do we have Spring pick up this specific bean? it's by saying @Configuration. The @Configuration annotation is a Spring annotation which help us to add more configuration. We want to add a little bit of configuration for our Spring security, so we would want to add a few more Spring Beans of our own; @Configuration indicates that a class declares one or more bean methods, so it's basically a class where we are defining a few beans, and these are to be processed by the Spring container to generate bean definitions. So what are we configured in here, we would want Spring Container to create beans around them, so that's why we use @Configuration, we want to create the beans that are needed to configure our security around our application. 

To configure spring security we would extend a specific class, so we need to extend the class WebSecurityConfigureAdapter, we want to override a couple of methods inside the WebSecurityConfigureAdapter to be able to add our own security configuration. There are two kinds of configuration that we would want to create. The first one is to create a couple of users, and the second thing which we want to say is to create a login form, we want to show the login page when the user log is unauthorized.

**Resume**

So in this step what we have looked is a couple of small things. One is we added the Spring Security Starter in the pom.xml, as soon as we added in this Spring Security Starter, it enables basic authentication around the entire application. What we looked is when we try to go to any URL, it used to ask for a pop-up where I enter the userId and password that we took from the log console. And then it was fine.

Later in the second part of this particular step, we looked at how to configure the security. Here in *public void configureGlobalSecurity* we are using in-memory authentication with the user id and password of our choice. 

The other thing which we did in *protected void configure*, was we wanted to get a form login page. So what we did is we said, any page in this particular application which matches these URLs "/" and "/*todo*/**", somebody has to have a roll of user to access them, if they don't, then show them a login form.

That's the configuration that we have done as part of these steps, and we enable spring security on top of our web application.

## Complete Code Example

##### pom.xml

```xml
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
		<dependency>											<!-- Added -->
			<groupId>org.springframework.boot</groupId>
			<artifactId>spring-boot-starter-security</artifactId>
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
            <artifactId>bootstrap-datepicker</artifactId>
            <version>1.0.1</version>
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

##### /src/main/java/com/imh/springboot/web/security/SecurityConfiguration.java

``` java
package com.imh.springboot.web.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@Configuration
public class SecurityConfirguration extends WebSecurityConfigurerAdapter {
	
	//1. Create Users - imh/dummy
	//2. Create  a Login Form
	
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