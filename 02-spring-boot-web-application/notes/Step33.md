## Step 33: Upgrading to Spring Boot 2 and Spring 5

You'll need the next resources to upgrade to the latest version:

- https://start.spring.io/
- https://spring.io/projects/spring-boot#learn
- https://www.baeldung.com/spring-maven-repository
- https://mvnrepository.com/artifact/org.springframework.boot/spring-boot

When we looked at the web page start.spring.io, the latest version of the Spring Initializr was 2.2.6, this is the latest milestone release for Spring Boot.  In this step, what we'll do is upgrade our project from 2.3.5 to 2.3.6 version, which will mean that we are using the latest version of Spring Framework.

##### pom.xml

```xml
	<parent>
		<groupId>org.springframework.boot</groupId>
		<artifactId>spring-boot-starter-parent</artifactId>
		<version>2.2.5.RELEASE</version>
		<relativePath/> <!-- lookup parent from repository -->
	</parent>
```

We add the repository where is located the new releases and upgrade our project to the version 2.2.6 of Spring Boot.

```xml
	<parent>
		<groupId>org.springframework.boot</groupId>
		<artifactId>spring-boot-starter-parent</artifactId>
		<version>2.2.6.RELEASE</version>
		<relativePath/> <!-- lookup parent from repository -->
	</parent>
	
	<!-- Repository of Spring Boot releases -->
	<repositories>	
    		<repository> 
        		<id>repository.spring.release</id> 
        		<name>Spring GA Repository</name> 
        		<url>http://repo.spring.io/release</url> 
	    	</repository>
	</repositories>
```

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
		<version>2.2.6.RELEASE</version>
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
		<dependency>
			<groupId>org.springframework.boot</groupId>
			<artifactId>spring-boot-starter-security</artifactId>
		</dependency>
		<dependency>
			<groupId>org.springframework.boot</groupId>
			<artifactId>spring-boot-starter-data-jpa</artifactId>
		</dependency>
		<!--
		<dependency>
			<groupId>com.h2database</groupId>
			<artifactId>h2</artifactId>
			<scope>runtime</scope>
		</dependency>
		--> 
		<!-- Oracle database driver -->
		<dependency>
    		<groupId>com.oracle</groupId>
    		<artifactId>ojdbc8</artifactId>
    		<version>12.2.0.1</version>
    		<scope>provided</scope>
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
		WelcomeController
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
	
	<!-- Repository of Spring Boot releases -->
	<repositories>	
    	<repository> 
        	<id>repository.spring.release</id> 
        	<name>Spring GA Repository</name> 
        	<url>http://repo.spring.io/release</url> 
    	</repository>
	</repositories>

</project>
```