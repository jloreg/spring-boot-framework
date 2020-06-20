## Step 14: Display Todos in a table using JSTL Tags

What You Will Learn during this Step:

 - Display Todos in a table using JSTL Tags
 - <%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
 - Add Dependency for jstl

In this step, we want to display our Todos in a simple table. We want to use JSTL tags to display in a table. 

To be able to use JSTL, we would need to add the .jar dependency to the pom.xml.

##### pom.xml
```
<!-- To enable JSTL Tags! -->
     	<dependency>
            <groupId>javax.servlet</groupId>
            <artifactId>jstl</artifactId>
        </dependency>
```

This jstl.jar woud give is the functionality to be able to run JSTL.

##### /src/main/webapp/WEB-INF/jsp/list-todos.jsp

```
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<html>
	<head>
		<title>Todo's for ${name}</title>	<!-- Added -->
	</head>
	<body>									<!-- Added -->
		<!-- <H1>Your Todos</H1> -->
		<table>
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
		<BR/>
		<a href="/add-todo">Add a Todo</a>
	</body>
</html>
```

In the taglib to include JSTL, I'm giving it a simple prefix="c"%, so that it's easy to refer to that.

In this step, we are actually displaying a list of Todos in a simple table instead of just printing some toString() on the screen.

## Complete Code Example

##### /src/main/webapp/WEB-INF/jsp/list-todos.jsp

```html
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<html>
	<head>
		<title>Todo's for ${name}</title>	<!-- Added -->
	</head>
	<body>									<!-- Added -->
		<!-- <H1>Your Todos</H1> -->
		<table>
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
		<BR/>
		<a href="/add-todo">Add a Todo</a>
	</body>
</html>
```

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