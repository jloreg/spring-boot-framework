## Step 28: Create Todo Entity and JPA Repository

In this step, let's update the Todo bean to be an entity and also create a repository, so this Todo bean right now, it's just a bean. How do we make it an entity, just add @Entity annotation as we learned in the JPA section, and I want the database to generate a value? So with @Id and @GeneratedValue, this will be generated based on a sequence, and I'm saying this *private int id* is the primary key.
    
``` java
@Entity
public class Todo {
	
    @Id				//Primary Key
    @GeneratedValue
    private int id;
    
}
```

One of the things which would happen in the background right now, it's Spring Boot auto-configuration kicks in. One of the features in Hibernate is that it can look at the Entities, and create the schema for you. What Spring Boot does is it enables that feature to auto-configuration when we are using an in-memory database.

So if I launch a localhost:8080/h2-console/ page righ now, you'll see a Todo table in here. The are no data because we have not inserted any data in here, but the table is present, the table is created because of the entity that we have declared, as soon as we declare the entity, at the application startup, Spring Boot auto configuration sees I'm conected an in-memory database, that means it enables the configuration for hibernate to generate the schema and create it for us. You can see the running hbm2ddl schema exporting here, and you can also see the exact query which was used into the console.

Now we have the table in the database, now we want to be able to insert data, and retrieve data from there. We would use a SpringDataRepository to do that, so let's create a TodoRepository into the service package. The Repository as we discussed during Jpa section is a interface.
 
``` java
public interface TodoRepository extends JpaRepository <Todo, Integer> {
	
	//#2
	List <Todo> findByUser(String user);
	
	//#1
	//service.retrieveTodos(name);
	//service.deleteTodo(id);
	//service.retrieveTodo(id);
	//service.updateTodo(todo);
	//service.addTodo(getLoggedInUserName(model), todo.getDesc(), 		todo.getTargetDate(), false);
}
```

There are two things that you need to define, what is the entity that we would want to manage, the entity that we would want to manage is *Todo*, and what is the primary key, the primary key is an *Integer*. If I look at the Todo class, the id field is an integer, that's the primary key, so that's we are putting her into *extends JpaRepository<Todo, Integer>*. 

So I'm saying that this JpaRepository manages Todo when I look at the different methods which are invoked on the TodoService into the TodoController, falling out the list of methods (see #1). These are all the operations that we are performing, out of these, these four operations are already provided by the TodoRepository, so these are all, and these are basically provided by default by the TodoRepository.

One of the things which is not present in the TodoRepository by default is retrieving Todo's by name, so we would want to retrieve the list of Todo's by name, that's not present, so I would want to query the table by the column name of *name* through the depository, does not have such a method by default. What we need to do, is we need to create, you would need to define a method for it, so this search (see #2), what does it return, it returns a list of Todo's for a specific user.

**Resume**

In this step, what we have done is we have defined an entity called Todo, we saw that this entity was also created in the in-memory database. The other thing is we created the TodoRepository, by default the TodoRepository has methods to insert, delete, update, and retrieve Todo's. We wanted a method to be able to find the Todo's by a specific user. So we added in an interface method to be able to do that.

## Complete Code Example

##### /src/main/java/com/imh/springboot/web/service/TodoRepository.java

``` java
package com.imh.springboot.web.service;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.imh.springboot.web.model.Todo;

public interface TodoRepository extends JpaRepository <Todo, Integer> {
	
	List <Todo> findByUser(String user);
	//service.retrieveTodos(name);
	
	//service.deleteTodo(id);
	//service.retrieveTodo(id);
	//service.updateTodo(todo);
	//service.addTodo(getLoggedInUserName(model), todo.getDesc(), todo.getTargetDate(), false);
}

```

##### /src/main/java/com/imh/springboot/web/model/Todo.java

```
package com.imh.springboot.web.model;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.validation.constraints.Size;

@Entity
public class Todo {
	
	@Id
	@GeneratedValue
    private int id;
    private String user;
    @Size(min=10, message="Enter at least 10 Characters...")
    private String desc;
    private Date targetDate;
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