## Step 10: Create TodoController and list-todos view. Make TodoService a @Service

What You Will Learn during this Step:

- Create TodoController and list-todos.jsp
- Make TodoService a @Service and inject it

This is the first step where we would be talking about our real application that we want to develop. That's the Todo management application. So we would want to be able to add delete and  modify to do this through this applcation. To be able to do that, we need to have some business logic in place. What we'll do instead is we would use an in-memory kind of data stored using an ArrayList.

What we have is a TodoService which has doing add, delete, and modifyt the Todo's. So this is plain Java code and this is a very simple java code. So you have a Todo.java which is a kind of the bean that which contains all the data of the Todo, and we have a TodoService to add, delete and modify Todo's. So what will do in this step is we'll start with adding the TodoService and the Todo.java and then, we would also add in a list Todo page. So we want to be able to see the list of Todo's as they are are right now, so we'll create a page to do that, we'll see how to integrate it into our application. So let's get started.

Until now, when a user succesfully logs in with the correct userid and password, you would show in localhost:8080/login.jsp a welcome message render. What we would want to do, is we would want to show welcome message in list-todos.jsp view, and now a list of Todo's links that you're ready to manage. When user clicks that link, we would want to take him to the list Todo.
 
**Resume**

There are a lot of things that we need to work on. One of the things is imh is harcoded in the TodoController, and input as parameter in the invoque to TodoService *model.put("todos", service.retrieveTodos("imh"));*, this is one thing which we need to fix. 

The UI is not really appealing as of now, it will work with the assist framework called Bootstrap to make this really good a little later. 

The other thing you will notice is that you don't really need to login to see the list of Todo's, so if you just go anywhere and type in the URL localhost:8080/list-todos, you can see the list of Todo's. 

This application is not really secure. Those are all the things which we  will fix during our subsequent steps.

Until now we have developed a simple login page, we created a welcome page simple as one well, and then we created a very rough list-todos.jsp page. There are a lot of work left for us to do in the subsequent steps.

## Complete Code Example

##### /src/main/java/com/imh/springboot/web/controller/TodoController.java

```java
package com.imh.springboot.web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.imh.springboot.web.service.TodoService;

@Component
public class TodoController {
	
	@Autowired
	TodoService service;

	@RequestMapping(value="/list-todos", method = RequestMethod.GET)
	public String showTodos(ModelMap model){
		model.put("todos", service.retrieveTodos("imh"));
		return "list-todos";
	}

}
```

##### /src/main/java/com/imh/springboot/web/service/TodoService.java

```java
package com.imh.springboot.web.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import org.springframework.stereotype.Service;
import com.imh.springboot.web.model.Todo;

@Service
public class TodoService {
    private static List<Todo> todos = new ArrayList<Todo>();
    private static int todoCount = 3;

    static {
        todos.add(new Todo(1, "imh", "Learn Spring MVC", new Date(),
                false));
        todos.add(new Todo(2, "imh", "Learn Struts", new Date(), false));
        todos.add(new Todo(3, "imh", "Learn Hibernate", new Date(),
                false));
    }

    public List<Todo> retrieveTodos(String user) {
        List<Todo> filteredTodos = new ArrayList<Todo>();
        for (Todo todo : todos) {
            if (todo.getUser().equals(user)) {
                filteredTodos.add(todo);
            }
        }
        return filteredTodos;
    }

    public void addTodo(String name, String desc, Date targetDate,
            boolean isDone) {
        todos.add(new Todo(++todoCount, name, desc, targetDate, isDone));
    }

    public void deleteTodo(int id) {
        Iterator<Todo> iterator = todos.iterator();
        while (iterator.hasNext()) {
            Todo todo = iterator.next();
            if (todo.getId() == id) {
                iterator.remove();
            }
        }
    }
}
```

##### /src/main/java/com/imh/springboot/web/model/Todo.java

```java
package com.imh.springboot.web.model;

import java.util.Date;

public class Todo {
	
    private int id;
    private String user;
    private String desc;
    private Date targetDate;
    private boolean isDone;

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

##### /src/main/webapp/WEB-INF/jsp/welcome.jsp

```
<html>
	<head>
		<title>First Web Applications</title>
	</head>
	<body>
		Welcome ${name} !! <a href="/list-todos">Click here</a> to manage your todo's.
	</body>
</html>
```

##### /src/main/webapp/WEB-INF/jsp/list-todos.jsp

```
<html>
	<head>
		<title>First Web Applications</title>
	</head>
	<body>
		Here are the list of your todos: 
		${todos}
	</body>
</html>
```