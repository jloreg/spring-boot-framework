## Step 32: Connecting JPA to other databases

One of the most important reasons why he use h2 in our applications is because it helps you to focus on learning the thing. So h2 has in-memory database, is very simple to use, it has a console to help you to manage the data, and also it is very easy to connect to from our application. We can really focus on the logic on the code, on our queries, and things like that when we're using h2 as the in-memory database.

However you might want to connect to a real database like MySql or Oracle. The thing is SpringBoot makes it very easy to switch from your H2 database, to MySql or Oracle database as well. So all the instructions for doing this is presenting below.

### INSTALLING AND SETTING UP ORACLE DATABASE ON DOCKER

#### Creating a PDB

```
linus@device:~$ sqlplus64 sys/Oracle18@localhost:32118/XE as sysdba

SQL*Plus: Release 18.0.0.0.0 - Production on Mon Mar 9 13:40:50 2020
Version 18.5.0.0.0

Copyright (c) 1982, 2018, Oracle.  All rights reserved.


Connected to:
Oracle Database 18c Express Edition Release 18.0.0.0.0 - Production
Version 18.4.0.0.0

SQL> create pluggable database xepdb2 admin user pdb_adm identified by Oradoc_db1 file_name_convert=('/opt/oracle/oradata/XE/pdbseed','/opt/oracle/oradata/XE/XEPDB2');

Pluggable database created.

SQL> select vp.name, vp.open_mode from v$pdbs vp;

NAME
-----------------------------------------------------------
OPEN_MODE
----------
PDB$SEED
READ ONLY

XEPDB1
READ WRITE

XEPDB2
MOUNTED

SQL> alter pluggable database xepdb2 open read write;

Pluggable database altered.
 
SQL> alter pluggable database all save state;

Pluggable database altered.

SQL> exit
Disconnected from Oracle Database 18c Express Edition Release 18.0.0.0.0 - Production
Version 18.4.0.0.0
```

#### Grant permissions

https://chartio.com/resources/tutorials/how-to-create-a-user-and-grant-permissions-in-oracle/

```
linus@device:~$ sqlplus64 sys/Oracle18@localhost:32118/XEPDB2 as sysdba
SQL> GRANT UNLIMITED TABLESPACE TO pdb_adm;
SQL> GRANT CREATE SESSION TO pdb_adm;
SQL> GRANT ANY PRIVILEGE TO pdb_adm;
SQL> GRANT ALL PRIVILEGES TO pdb_adm;
```

#### Execute manually the following instructions if you don't want that Spring Boot creates the table and the data automatically for us

Table

```sql
CREATE TABLE TODO_TABLE ( 
  todo_id number(10) NOT NULL,
  todo_desc varchar2(255),
  todo_isdone NUMBER(1,0) NOT NULL,
  target_date TIMESTAMP, 
  todo_user varchar2(50),
  CONSTRAINT todo_pk PRIMARY KEY (todo_id)
);
```

Data

```sql
--Oracle data v1
INSERT INTO TODO_TABLE VALUES (1001,'Learn Spring Boot',0,sysdate,'imh');
INSERT INTO TODO_TABLE VALUES (1002,'Learn Angular JS',0,sysdate,'imh');
INSERT INTO TODO_TABLE VALUES (1003,'Learn To Dance',0,sysdate,'imh');

--Oracle data v2
INSERT ALL
INTO TODO_TABLE (TODO_ID, TODO_DESC, TODO_ISDONE, TODO_TARGETDATE, TODO_USER) 
VALUES (1001,'Learn Spring Boot',0,sysdate,'imh')
INTO TODO_TABLE (TODO_ID, TODO_DESC, TODO_ISDONE, TODO_TARGETDATE, TODO_USER)
VALUES (1002,'Learn Angular JS',0,sysdate,'imh')
INTO TODO_TABLE (TODO_ID, TODO_DESC, TODO_ISDONE, TODO_TARGETDATE, TODO_USER)
VALUES (1003,'Learn To Dance',0,sysdate,'imh')
SELECT * FROM dual;
```

#### Starting Docker oracle container

```
linus@device:~$ docker rm 93963a8eebd7753be9f2e685ab84611e9cdc535bd9b27dd0bd9c4f294ac9d70a
93963a8eebd7753be9f2e685ab84611e9cdc535bd9b27dd0bd9c4f294ac9d70a
linus@device:~$ docker run -d   -p 32118:1521   -p 35518:5500   --name=oracle-xe   --volume ~/docker/oracle-xe:/opt/oracle/oradata   --network=oracle_network   oracle-xe:18c
96b96bec4522552df62d004626a489ba0d226b3a515ad3d2048b247b7158f78d
linus@device:~$ docker ps
CONTAINER ID        IMAGE               COMMAND                  CREATED             STATUS                             PORTS                                              NAMES
96b96bec4522        oracle-xe:18c       "/bin/sh -c 'exec ${…"   58 seconds ago      Up 58 seconds (health: starting)   0.0.0.0:32118->1521/tcp, 0.0.0.0:35518->5500/tcp   oracle-xe
linus@device:~$ docker start oracle-xe
oracle-xe
```

### Code changes to connect to Oracle

https://javabycode.com/build-tools/maven/add-oracle-jdbc-driver-maven.html
https://www.oracle.com/database/technologies/jdbc-ucp-122-downloads.html

Download file ojdbc8.jar and save it in the route ~/.m2/repository/com/oracle/ojdbc8/12.2.0.1 , and run the command below to install ojdbc8.jar
```
linus@device:~/.m2/repository/com/oracle/ojdbc8/12.2.0.1$ mvn install:install-file -Dfile=ojdbc8.jar -DgroupId=com.oracle -DartifactId=ojdbc8 -Dversion=12.2.0.1 -Dpackaging=jar
[INFO] ------------------------------------------------------------------------
[INFO] BUILD SUCCESS
[INFO] ------------------------------------------------------------------------
[INFO] Total time:  1.661 s
[INFO] Finished at: 2020-05-03T18:26:54+02:00
[INFO] ------------------------------------------------------------------------
```

- If you want to use ojdbc8.jar dependency, you must add this to the pom.xml (and remove H2 dependency)

```xml
<!--Oracle database driver -->
<dependency>
    <groupId>com.oracle</groupId>
    <artifactId>ojdbc8</artifactId>
    <version>12.2.0.1</version>
    <scope>provided</scope>
</dependency>
```

- Configure application.properties

```
spring.mvc.view.prefix=/WEB-INF/jsp/
spring.mvc.view.suffix=.jsp

#H2 in-memory database (deactivated on step 32)
spring.h2.console.enabled=false

# create and drop tables and sequences, loads import.sql
spring.jpa.show-sql=true

# logging settings
#logging.level.com.imh.sprinframework.web=debug
logging.level.org.springframework.web=info
logging.level.org.hibernate.SQL=debug
logging.pattern.console=%d{yyyy-MM-dd HH:mm:ss} %-5level %logger{36} - %msg%n

### Step 32: Connecting JPA to Oracle database

# use create when running the app for the first time, then change to "update" which just updates the schema when necessary
spring.jpa.hibernate.ddl-auto=create-drop
#hibernate configurations
spring.jpa.database-platform=org.hibernate.dialect.Oracle12cDialect

spring.datasource.url=jdbc:oracle:thin:@//localhost:32118/XEPDB2
spring.datasource.username=pdb_adm
spring.datasource.password=Oradoc_db1
spring.datasource.driver-class-name=oracle.jdbc.OracleDriver
```

- Modify import.sql into src/main/resources/application.properties to insert automatically the rows below when Spring Boot application startup

```
--INSERT ALL
--INTO TODO_TABLE (TODO_ID, TODO_DESC, TODO_ISDONE, TODO_TARGETDATE, TODO_USER) 
--VALUES (1001,'Learn Spring Boot',0,sysdate,'imh')
--INTO TODO_TABLE (TODO_ID, TODO_DESC, TODO_ISDONE, TODO_TARGETDATE, TODO_USER)
--VALUES (1002,'Learn Angular JS',0,sysdate,'imh')
--INTO TODO_TABLE (TODO_ID, TODO_DESC, TODO_ISDONE, TODO_TARGETDATE, TODO_USER)
--VALUES (1003,'Learn To Dance',0,sysdate,'imh')
--SELECT * FROM dual;

--CAUTION. Insert ';' only in the last instruction.
INSERT INTO TODO_TABLE VALUES (1001,'Learn Spring Boot',0,sysdate,'imh')
INSERT INTO TODO_TABLE VALUES (1002,'Learn Angular JS',0,sysdate,'imh')
INSERT INTO TODO_TABLE VALUES (1003,'Learn To Dance',0,sysdate,'imh');
```

- Restart and You are ready!

**Useful resources**
- https://www.oracletutorial.com/
- https://www.databasestar.com/
- https://www.springboottutorial.com/spring-boot-with-mysql-and-oracle
- https://dzone.com/articles/configuring-spring-boot-for-oracle
- https://dzone.com/articles/spring-boot-jpa-hibernate-oracle
- https://medium.com/skillhive/spring-boot-spring-data-jpa-and-oracle-database-c4af89f727e0
- https://stackoverflow.com/questions/54860555/connecting-oracle-using-springboot
- https://stackoverflow.com/questions/21422664/spring-boot-spring-data-import-sql-doesnt-run-spring-boot-1-0-0-rc1

### INSTALLING AND SETTING UP MYSQL

- Install MySQL https://dev.mysql.com/doc/en/installing.html
- More details : http://www.mysqltutorial.org/install-mysql/
- Trouble Shooting - https://dev.mysql.com/doc/refman/en/problems.html
- Startup the Server (as a service)
- Go to command prompt (or terminal)
- Execute following commands to create a database and a user

```
mysql --user=user_name --password db_name
create database todo_example;
create user 'todouser'@'localhost' identified by 'YOUR_PASSWORD';
grant all on todo_example.* to 'todouser'@'localhost';
```

- Execute following sql queries to create the table and insert the data

Table

```sql
create table todo 
(id integer not null, 
desc varchar(255), 
is_done boolean not null, 
target_date timestamp, 
user varchar(255), 
primary key (id));
```

Data

```sql
INSERT INTO TODO
VALUES(10001, 'Learn Spring Boot', false, sysdate(),'in28Minutes');

INSERT INTO TODO
VALUES(10002, 'Learn RESTful Web Services', false, sysdate(),'in28Minutes');

INSERT INTO TODO
VALUES(10003, 'Learn SOAP Web Services', false, sysdate(),'in28Minutes');
```

### Code changes to connect to MySQL

- Add dependency to pom.xml (and remove H2 dependency)

```xml
<dependency>
    <groupId>mysql</groupId>
    <artifactId>mysql-connector-java</artifactId>
</dependency>
```

- Configure application.properties

```
spring.jpa.hibernate.ddl-auto=none
spring.datasource.url=jdbc:mysql://localhost:3306/todo_example
spring.datasource.username=todouser
spring.datasource.password=YOUR_PASSWORD
```

- Restart and You are ready!

**Resume**
In this step, we look at what are the things you'd need to do to be able to connect to a different database. So we are using in-memory database if you want to connect to MySql or any other database, how do you do that ? that's basically what we look in this specific step.

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

##### /src/main/resources/application.properties

```
spring.mvc.view.prefix=/WEB-INF/jsp/
spring.mvc.view.suffix=.jsp

#H2 in-memory database (deactivated on step 32)
spring.h2.console.enabled=false

# create and drop tables and sequences, loads import.sql
spring.jpa.show-sql=true

# logging settings
#logging.level.com.imh.sprinframework.web=debug
logging.level.org.springframework.web=info
logging.level.org.hibernate.SQL=debug
logging.pattern.console=%d{yyyy-MM-dd HH:mm:ss} %-5level %logger{36} - %msg%n

### Step 32: Connecting JPA to Oracle database

# use create when running the app for the first time, then change to "update" which just updates the schema when necessary
spring.jpa.hibernate.ddl-auto=create-drop
#hibernate configurations
spring.jpa.database-platform=org.hibernate.dialect.Oracle12cDialect

spring.datasource.url=jdbc:oracle:thin:@//localhost:32118/XEPDB2
spring.datasource.username=pdb_adm
spring.datasource.password=Oradoc_db1
spring.datasource.driver-class-name=oracle.jdbc.OracleDriver
```

##### /src/main/resources/schema.sql

```sql
--H2 in-memory database
--insert into TODO
--values (10001, 'Learn Spring Boot', false, sysdate(), 'in28minutes');
--insert into TODO
--values (10002, 'Learn Angular JS', false, sysdate(), 'in28minutes');
--insert into TODO
--values (10003, 'Learn To Dance', false, sysdate(), 'in28minutes');
```

##### /src/main/resources/import.sql

```sql
--INSERT ALL
--INTO TODO_TABLE (TODO_ID, TODO_DESC, TODO_ISDONE, TODO_TARGETDATE, TODO_USER) 
--VALUES (1001,'Learn Spring Boot',0,sysdate,'imh')
--INTO TODO_TABLE (TODO_ID, TODO_DESC, TODO_ISDONE, TODO_TARGETDATE, TODO_USER)
--VALUES (1002,'Learn Angular JS',0,sysdate,'imh')
--INTO TODO_TABLE (TODO_ID, TODO_DESC, TODO_ISDONE, TODO_TARGETDATE, TODO_USER)
--VALUES (1003,'Learn To Dance',0,sysdate,'imh')
--SELECT * FROM dual;

--CAUTION. Insert ';' only in the last instruction.
INSERT INTO TODO_TABLE VALUES (1001,'Learn Spring Boot',0,sysdate,'imh')
INSERT INTO TODO_TABLE VALUES (1002,'Learn Angular JS',0,sysdate,'imh')
INSERT INTO TODO_TABLE VALUES (1003,'Learn To Dance',0,sysdate,'imh');
```

##### /src/main/java/com/imh/springboot/web/model/Todo.java

```java
package com.imh.springboot.web.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.Size;

import org.hibernate.annotations.Type;

@Entity
@Table(name = "TODO_TABLE")
public class Todo {
	
	@Column(name = "TODO_ID", nullable = false)
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
	@Column(name = "TODO_USER", length = 255)
    private String user;
	@Column(name = "TODO_DESC", length = 255)
    @Size(min=10, message="Enter at least 10 Characters...")
    private String desc;
	@Column(name = "TODO_TARGETDATE")
    private Date targetDate;
	@Column(name = "TODO_ISDONE", nullable = false)
	@Type(type = "org.hibernate.type.NumericBooleanType")
	private boolean isDone;
	
    public Todo() {
		super();
	}
    
    public Todo(int id, String user, String desc, Date targetDate,
            boolean isDone) {
        super();
        this.id = id;
        this.user = user;
        this.desc = desc;
        this.targetDate = targetDate;
        this.isDone = isDone;
    }
 
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public Date getTargetDate() {
        return targetDate;
    }

    public void setTargetDate(Date targetDate) {
        this.targetDate = targetDate;
    }

    public boolean isDone() {
        return isDone;
    }

    public void setDone(boolean isDone) {
        this.isDone = isDone;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + id;
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        Todo other = (Todo) obj;
        if (id != other.id) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return String.format(
                "Todo [id=%s, user=%s, desc=%s, targetDate=%s, isDone=%s]", id,
                user, desc, targetDate, isDone);
    }

}
```