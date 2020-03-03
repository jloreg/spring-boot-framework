## Step 01.1: Part 1 Basic Spring Boot Web Application Setup

What You Will Learn during this Step:

- Create a simple web application using Spring Boot
- Run the Spring Boot Application

Basically we use [Spring initializr](https://start.spring.io/) to initialize to bootstrap our application. 

*Project*: Select Maven project as the build tool.
*Spring Boot*: Always try to select the version of Spring Boot that not contains the words (SNAPSHOT); this is the stable version.
*Project Metada*
*Group*: com.imh.springboot.web. GroupId it's similar to the name that we assign a package on a Java class.
*Artifact*:spring-boot-web-application. ArtifactId it's similar to the name that we assign a class on Java, but here it's the name of the application that we are creating.
*Dependencies*: Web, DevTools. All of them are starters that contain all the dependencies that are needed to develop an application: Web contains Spring MVC.

Generate project and extract the zip file to a folder content. Later, launch up an eclipse workspace and import the project that we have downloaded.

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

##### /src/main/resources/application.properties
```
```

##### /src/main/java/com/imh/springboot/web/SpringBootWebApplication.java

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

##### /src/test/java/com/imh/springboot/web/SpringBootWebApplicationTests.java

```java
package com.imh.springboot.web;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class SpringBootWebApplicationTests {

	@Test
	void contextLoads() {
	}

}
```