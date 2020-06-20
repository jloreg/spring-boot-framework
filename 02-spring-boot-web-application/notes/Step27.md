## Step 27: Configuring H2 Console

For the H2 database, we have a web console which is available, but we need to enable that, as well as enable login of all the queries that we created. Let's do that configuration, and also there is a little bit of security configuration that we would need to do, to get localhost:8080/h2-console running.

I'm building up application.properties for our application, and I would want to ensure that all the queries get locked. How can I do that? *spring.jpa.show-sql=true* makes sure that all the queries which are run will be printed to the log, the other thing we would want to do is to enable h2-console *spring.h2.console.enabled=true*, so we would want to enable to use the h2-console to see the data in the database.

```
spring.jpa.show-sql=true
spring.h2.console.enabled=true
```

When you click connect you would see that there is an error which is coming up, it says: "An exception occurred! Please contact Support!". This is because of Spring Security, in the last steps of the Spring Boot web application design, we added in Spring Security to secure our application, and Spring Security does a few things which break the H2-console. What will do is we'll disable the security for h2-console, and make a couple of spring security settings so that we would be able to get this working fine.

What we need to do, is go to our SprinBoot security configuration in SecurityConfirguration class. Over here we don't want any security around the h2-console, so I'll say don't worry about h2-console, how do I do that? *http.authorizeRequests().antMatchers("/h2-console/**")*, because h2-console userId and password are provided by the h2 database itself, so we don't really need any security around that part (see #1).

The other things it's h2-console users a few things that we would need to disable. So h2-console is actually part of a frame, so you need to allow frames to be present, and also we would need to disable csrf. Csrf is one of the security threats and be a disabling that *http.csrf().disable();*, that is actually automatically enabled by Spring Security, what I would also want to do is to quickly do *http.headers().frameOptions().disable();*, the things h2-console runs in a frame, and that's why we need to disable. 

```java
@Configuration
public class SecurityConfirguration extends WebSecurityConfigurerAdapter {

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
		http.authorizeRequests().antMatchers("/login", "/h2-console/**").permitAll()	//Added #1: "/h2-console/**"
				.antMatchers("/", "/*todo*/**").access("hasRole('USER')").and()
				.formLogin();
		
		http.csrf().disable();													//Added
		http.headers().frameOptions().disable();								//Added
	} 

}
```

In the next we create and entity and a repository to be able to access data from our database.

**Notes**

http://localhost:8080/h2-console/

```
Saved Settings: Generic H2 (Embedded)
Setting Name: Generic H2 (Embedded)
Driver Class: org.h2.Driver
JDBC URL: jdbc:h2:mem:testdb
User Name: sa
Password:
```

## Complete Code Example

##### /src/main/resources/application.properties

```
spring.mvc.view.prefix=/WEB-INF/jsp/
spring.mvc.view.suffix=.jsp
#logging.level.com.imh.sprinframework.web=debug
logging.level.org.springframework.web=info

spring.jpa.show-sql=true
spring.h2.console.enabled=true
```

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
		http.authorizeRequests().antMatchers("/login", "/h2-console/**").permitAll()
				.antMatchers("/", "/*todo*/**").access("hasRole('USER')").and()
				.formLogin();
		
		http.csrf().disable();
		http.headers().frameOptions().disable();
	} 

}
```