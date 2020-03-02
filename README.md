# Spring Boot

In this repository, you will learn about Spring Boot and Spring Boot Starter Projects. We will develop

- A web application to manage your todos
- A basic REST Service to manage questions of a survey

This notes would be a perfect first step as an introduction to Spring Boot. Here is a quick overview of different sections:

- Develop a Todo Management Web Application
- Advanced Features of Spring Boot - We learn these developing a simple API for managing survey questionnaire.
- Introduction to JPA
- Connecting our Todo Management Web Application to JPA

You will be using REST Services, Spring (Dependency Management), Spring MVC, Spring Boot, Spring Security (Authentication and Authorization), BootStrap (Styling Pages), Maven (dependencies management), Eclipse (IDE) and Tomcat Embedded Web Server.

You will learn about:

- Basics of Spring Boot
- Basics of Auto Configuration and Spring Boot Magic
- Spring Boot Starter Projects
- Spring Initializr
- DispatcherServlet
- Basic Todo Management Application with Login/Logout
- Model, Controllers, ViewResolver and Filters
- Forms - DataBinding, Validation
- Annotation based approach - @RequestParam, @ModelAttribute, @SessionAttributes etc
- Bootstrap to style the page
- Basic REST Services using Spring Boot Starter Web
- REST Service Content Negotiation with JSON and XML
- Embedded servlet containers : Tomcat, Jetty and Undertow
- Writing Unit and Integration tests using Spring Boot Starter Test
- Profiles and Dynamic Configuration with Spring Boot
- Spring Boot Data JPA
- Spring Boot Actuator
- Spring Security
- Spring Boot Developer Tools and LiveReload

## Web Application with Spring Boot

- 0000 - 02 - Section Introduction - Web Application with Spring Boot
- Step 01.1: Part 1 Basic Spring Boot Web Application Setup
- Step 01.2: Part 2 Pom.xml, Spring Boot Application and application properties
- Step 02.1: Part 1 First Spring MVC Controller, @ResponseBody, @Controller
- Step 02.2: Part 2 Understanding HTTP Request Flow
- Step 03: Demystifying some of the Spring Boot magic
- Step 04: Redirect to Login JSP - @ResponseBody and View Resolver
- Step 05: Show userid and password on welcome page - ModelMap and @RequestParam
- Step 06: DispatcherServlet and Spring MVC Flow
- Step 07: Your First HTML form
- Step 08: Add hard-coded validation of userid and password
- Step 09: Magic of Spring
- Step 10: Create TodoController and list-todos view. Make TodoService a @Service
- Step 11: Architecture of Web Applications
- Step 12: Session vs Model vs Request - @SessionAttributes
- Step 13: Add new todo
- Step 14: Display Todos in a table using JSTL Tags
- Step 15: Bootstrap for Page Formatting using webjars
- Step 16: Let's delete a Todo
- Step 17: Format Add Todo Page and Adding Basic HTML5 form validation
- Step 18.1: Part 1 Validations with Hibernate Validator - Using Command Bean
- Step 18.2: Part 2 Using JSR 349 Validations
- Step 19: Updating a todo
- Step 20: Let's add a Target Date for Todo - Use initBinder to Handle Date Fields
- Step 21: JSP Fragments and Navigation Bar
- Step 22: Preparing for Spring Security
- Step 23: Initial Spring Security Setup
- Step 24: Refactor and add Logout Functionality using Spring Security
- Step 25: Exception Handling

## Spring Boot Deep Dive with a simple API

- 0000 - 05 - Section Introduction - Spring Boot Deep Dive with a simple API
- Step 01: Setup and Launch Spring Boot Application with Maven and Eclipse
- Step 02: Creating your first RestController & Theory Basics of Spring Framework
- Step 03: Understanding Spring Boot Magic - Spring Boot Starter Web
- Step 04: Understanding Spring Boot Magic - Spring Boot Starter Parent
- Step 05: Spring Boot vs Spring
- Step 06: Create all Services for Survey and Questions
- Step 07: Creating REST Service with GetMapping and PathVariable
- Step 07: Theory - Message Converters and Introduction to REST
- Step 08: Second REST Service to retrieve a specific question
- Step 09: Spring Boot Developer Tools and LiveReload  Develop faster
- Step 10: Create a Service to add a new question to survey PostMapping Postman
- Step 11: Understand Content Negotiation. Deliver XML Responses.
- Step 12: Spring Initializr. Create Spring Boot Projects on the fly
- Step 13: Spring Boot Actuator. Monitor your Spring Boot applications
- Step 14: Understanding Embedded servlet containers. Switch to Jetty or Undertow
- Step 15: Adding Dynamic Configuration to your application. YAML and More..
- Step 16: Basics of Profiles
- Step 17: Advanced Application Configuration - Type Safe Configuration Properties
- Step 18.1: Spring Boot Starter - Spring Data JPA with CommandLineRunner Part 1
- Step 18.2: Spring Boot Starter - Spring Data JPA with CommandLineRunner Part 2
- Step 19: In Memory Database H2 Console and add a new JPA Repository Method
- Step 20: Spring Boot Starter - Introduction to Spring Data Rest
- Step 21.1: Spring Boot Integration Test Part 1
- Step 21.1: Spring Boot Integration Test Part 2
- Step 22: Adding Integration Test for POST Request
- Step 23: Small Refactoring to organise ourselves
- Step 24: Writing Unit Tests with Spring Boot and Mockito Part 1
- Step 24: Writing Unit Tests with Spring Boot and Mockito Part 2
- Step 25: Writing Unit Test for createTodo
- Step 26: Securing our services with Basic Authentication using Spring Security
- Step 27.1: Configure Spring Security user roles for survey and other services Part 1
- Step 27.2: Configure Spring Security user roles Part 2
- Step 28: A Deep Dive into Spring Boot Auto Configuration

## Introduction to JPA

- 0000 - 06 - Section Introduction - Introduction to JPA with Spring Boot in 10 Steps
- Step 01 : Object Relational Impedence Mismatch - Understanding the problem that JPA solves
- Step 02 : World before JPA - JDBC, Spring JDBC and myBatis
- Step 03 : Introduction to JPA
- Step 04 : Creating a JPA Project using Spring Initializr
- Step 05 : Defining a JPA Entity - User
- Step 06 : Defining a Service to manage the Entity - UserService and EntityManager
- Step 07 : Using a Command Line Runner to save the User to Database
- Step 08 : Magic of Spring Boot and In Memory Database H2
- Step 09 : Introduction to Spring Data JPA
- Step 10 : More JPA Repository : findById and findAll

## Connecting Web Application with JPA

- 0000 - 07 - Section Introduction - Connecting Web Application with JPA
- Step 01: Adding Dependencies for JPA and H2
- Step 02: Configuring H2 Console
- Step 03: Create Todo Entity and JPA Repository
- Step 04: Insert Todo using JPA Repository
- Step 05: Update, Delete and Retrieve Todos using JPA Repository
- Step 06: Data initialization with data.sql
- Step 07: Connecting JPA to other databases
- Step 08: Upgrading to Spring Boot 2 and Spring 5