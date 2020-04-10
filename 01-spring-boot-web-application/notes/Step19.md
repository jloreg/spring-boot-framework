## Step 19: Updating a todo

- Add Update Functionality
- Let's Use the Same JSP as earlier.

Now we have the functionality to add a new Todo and delete an existing Todo. What we want to do, is we want to add and update button here in list-todo.jsp page and add the functionality to be able to update a specific Todo here in list-todo.jsp page. There are basically three small mini-steps that we have to do to implement the update functionality.

The first one is we'll want to add an update button in list-todo.jsp page, and once we have the update button in here, we would need to create a controller method to handle the update and to show the update screen.

The three mini steps are basically, we would want to add a button to the list-todo.jsp view and then handle the GET request and the POST request. So let's get started with the first mini step which is to add the button here in list-todo.jsp page.

```html
<table class="table table-striped">
	<caption> Your todos are </caption>
		<thead>
			<tr>
				<th>Description</th>
				<th>Target Date</th>
				<th>Is it Done?</th>
				<th></th>														<!-- Added: -->
			</tr>
		</thead>
		<tbody>
			<!-- JSTL For Loop -->
			<c:forEach items="${todos}" var="todo">
				<tr>
					<th>${todo.desc}</th>
					<th>${todo.targetDate}</th>
					<th>${todo.done}</th>
					<td>
						<a type="button" class="btn btn-success"
						      href="/update-todo?id=${todo.id}">Update</a>				<!-- Added: -->
						<a type="button" class="btn btn-warning"
						      href="/delete-todo?id=${todo.id}">Delete</a>
					</td>
				</tr>
		</c:forEach>
	</tbody>
</table>
```

Now we have the update button in list-todo.jsp page, so we can update every Todo. When you click it, we need to have a method to handle that. Let's go to the TodoController and add a method to handle the update. This update Todo is very similar to the *public String deleteTodo* controller method.

```java
@Controller
@SessionAttributes("name")
public class TodoController {
	
	@Autowired
	TodoService service;
	
	@RequestMapping(value="/update-todo", method = RequestMethod.GET)					//Added
	public String showUpdateTodoPage(@RequestParam int id, ModelMap model){
		Todo todo = service.retrieveTodo(id);	//#1
		model.put("todo", todo);			//#1.1
		return "todo";
	}
}
```

```java
@Service
public class TodoService {
    private static List<Todo> todos = new ArrayList<Todo>();
    private static int todoCount = 3;
    
    public Todo retrieveTodo(int id) {		//Added
        for (Todo todo : todos) {
            if (todo.getId() == id) {
                return todo;
            }
        }
        return null;	//If nothing matches return null back
    }

}
```

If I would want to show these details on the screen (see #1). How do I show the details on the screen, we need to add it to the model. So what we need is a *ModelMap model*. So if I want to show something, if I want to pass on data from the Controller to the View, how do we do that? by using the Model, so similar to that, we would be using a Model here *model.put("todo", todo)* in public String showUpdateTodoPage (see #1.1). So once you put the value in the model, what we would need to do, is we need to send the user, not to the list-todo.jsp, but we want to send it to them to the todo.jsp page. So that we can show the details of the user.

So what we want to do in the *public String showUpdateTodoPage*, when the user clicks update the Todo button, we want to take him to the todo.jsp page with the current Todo populated. So this *model.put("todo", todo)* is populating the current Todo, and this *return "todo"* is redirecting him to the current todo.jsp view. You can try clicking the Add button, but you'd see that it would fail, because the *public String showUpdateTodoPage* does not support the POST method, only has the GET method. Let's now create the POST method for it as well to handle the update.

```java
@Controller
@SessionAttributes("name")
public class TodoController {
	
	@Autowired
	TodoService service;
	
	@RequestMapping(value="/update-todo", method = RequestMethod.POST)					//Added
	public String updateTodo(@Valid Todo todo, BindingResult result){
		
		//Validation
		if (result.hasErrors()) {
			return "todo";
		}
		
		Todo todo = service.retrieveTodo(id);
		service.updateTodo(todo);
		return "todo";
	}

}
```

Once the evaluation process is passed, then I would want to save the details back to the database. The TodoService does not have an update Todo method, so what we'll do is we'll create a simple updateTodo method. You can get a lot of logic to do the update but I'm taking the simplest logic so that we can focus on the view. So, the easiest way to do the update Todo is by removing the existing Todo, I'll remove the Todo which I have already. So, all the details will be removed and I would add in the new details. Basically when I try and remove something, it works based on the field *private int id* of Todo; **Todo equals methods compare based on id**. So it looks at the Todo id and removes that ID which matches the Todo, and when I add the Todo to the list, it would add the new Todo that we have updated.

```
@Service
public class TodoService {
    private static List<Todo> todos = new ArrayList<Todo>();
    private static int todoCount = 3;
    
    public void updateTodo(Todo todo) {		//Added
    	todos.remove(todo);
    	todos.add(todo);
    }
}
```

Let's see how this works. In the TodoController, I would need to go to the updateTodo and say *service.updateTodo(todo)*, and pass in the Todo, once I update the Todo, what I would want to do is return the user to the list-todo.jsp page, so I would need to redirect the list-todos page (see #2.1). If now we launch the application and we Update a specific Todo, we get the error status=500. If you get this error, one of the things you need to remember, is this would be a null pointer exception (you can see the null pointer exception in the console output).

What's happening here is the fact that when I updated the earlier Todo, we did not populate the Id into the form. In the todo.jsp we only have the description field present in here, there is no Id here in the todo.jsp, when I did the update and this gets mapped, what it does it tries and picks the todo.id from the *Todo todo* value we have submitted, it tries to bind the Id.description, Id.target_date and everything based on whatever we are having in here in todo.jsp. So it only finds the description, only maps the description. The ID is null so it will try to put into the id field and which is causing the null pointer exception, to prevent that from happening, what I'll do is I populate the id into a hidden field in the todo.jsp.

```html
<form:form method="post" modelAttribute="todo">	<!-- Deprecated: commandName="todo" -->
	<form:hidden path="id"/>					<!-- Added: -->	
```

What I've done is I've entered the hidden form inside the *form:form*; if I put the hidden form outside the  *form:form*, the Command bean does not exist, so it's only inside the form that the command bean exists.

There is one other thing that we need to do before we would be able to update the Todo. Now in the todo.jsp page we have a Id, we have a description, there is one more thing that is mandatory in any Todo, and that's the user. So we'll also set the user into the Todo (see #2.1). We can't really know which user has been login, so for that, we have that in session, and how do I pick it up from session ? We already have a few examples to try to, I just need to have a model map as a parameter to our method to update the Todo. Once that's done I just need to get the value from model, because the name is already in session when the user logs in, we are already setting the name of the user in session (see #2.2).

```java
@Controller
@SessionAttributes("name")
public class TodoController {
	
	@Autowired
	TodoService service;
	
	@RequestMapping(value="/update-todo", method = RequestMethod.POST)					//Added
	public String updateTodo(ModelMap model, @Valid Todo todo, BindingResult result){
		
		//Validation
		if (result.hasErrors()) {
			return "todo";
		}
		
		//After validation
		todo.setUser((String) model.get("name"));	//#2.1
		service.updateTodo(todo);				//#2.2
		return "redirect:/list-todos";				//#2.2
	}

}
```

However, the Target Date in list-todos.jsp is still empty because we have not populated it on the form. So in the form, there is no target date so it's not getting populated. We'll work on that a little later, but you can see that all the other stuff is getting updated properly.

**Resume**

In this step, we added the functionality to update a specific Todo, so we have the functionality to delete a Todo, we have the functionality to update a Todo, and the functionality to edit a Todo as well. So, as far as the description is concerned, we almost have complete functionality.

In the next few steps, we'll try and make this application have a little bit of navigation, will add more feel so we would want to be able to edit and update the target date and things like that.

## Complete Code Example

##### /src/main/webapp/WEB-INF/jsp/list-todos.jsp

```html
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<html>
	<head>
		<title>Todo's for ${name}</title>
		<link href="webjars/bootstrap/3.3.6/css/bootstrap.min.css" rel="stylesheet">
	</head>
	<body>
		<div class="container">
			<table class="table table-striped">
				<caption> Your todos are </caption>
				<thead>
					<tr>
						<th>Description</th>
						<th>Target Date</th>
						<th>Is it Done?</th>
						<th></th>													<!-- Added: -->
					</tr>
				</thead>
				<tbody>
					<!-- JSTL For Loop -->
					<c:forEach items="${todos}" var="todo">
						<tr>
							<th>${todo.desc}</th>
							<th>${todo.targetDate}</th>
							<th>${todo.done}</th>
							<td>
								<a type="button" class="btn btn-success"
								   href="/update-todo?id=${todo.id}">Update</a>		<!-- Added: -->
								<a type="button" class="btn btn-warning"
								   href="/delete-todo?id=${todo.id}">Delete</a>
							</td>
						</tr>
					</c:forEach>
				</tbody>
			</table>
			<div>
				<a class="button" href="/add-todo">Add a Todo</a>
			</div>

			<script src="webjars/jquery/1.9.1/jquery.min.js"></script>	
			<script src="webjars/bootstrap/3.3.6/js/bootstrap.min.js"></script>		
		</div>
	</body>

</html>
```

##### /src/main/webapp/WEB-INF/jsp/todo.jsp

```html
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<html>
	<head>
		<title>First Web Applications</title>
		<link href="webjars/bootstrap/3.3.6/css/bootstrap.min.css" rel="stylesheet">
	</head>
	
	<body>
		<div class="container">		
			<form:form method="post" modelAttribute="todo">					<!-- Deprecated: commandName="todo" -->
				<form:hidden path="id"/>									<!-- Added: -->	
				<fieldset class="form-group">				
					<form:label path="desc">Description</form:label>
					<form:input path="desc" type="text"
						class="form-control" required="required"/>
					<form:errors path="desc" cssClass="text-warning"/>
				</fieldset>
				
				<button type="submit" class="btn btn-success">Add</button>
			</form:form>
		</div>
		
		<script src="webjars/jquery/1.9.1/jquery.min.js"></script>
		<script src="webjars/bootstrap/3.3.6/js/bootstrap.min.js"></script>
	
	</body>
</html>
```

##### /src/main/java/com/imh/springboot/web/controller/TodoController.java

```java
package com.imh.springboot.web.controller;

import java.util.Date;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.annotation.RequestParam;

import com.imh.springboot.web.model.Todo;
import com.imh.springboot.web.service.TodoService;

@Controller
@SessionAttributes("name")
public class TodoController {
	
	@Autowired
	TodoService service;

	@RequestMapping(value="/list-todos", method = RequestMethod.GET)
	public String showTodos(ModelMap model){
			String name = (String) model.get("name");
			model.put("todos", service.retrieveTodos(name));
		return "list-todos";
	}
	
	@RequestMapping(value="/add-todo", method = RequestMethod.GET)
	public String showAddTodoPage(ModelMap model){
		//model.addAttribute("todo",value);
		model.addAttribute("todo", new Todo(0,(String) model.get("name"), "",
				new Date(), false));
		return "todo";
	}
	
	@RequestMapping(value="/add-todo", method = RequestMethod.POST)
	public String addTodo(ModelMap model, @Valid Todo todo, BindingResult result){	
		if (result.hasErrors()) {	//Added: if there are errors, the user gets redirected to the todo.jsp page.
			return "todo";
		}
		service.addTodo((String) model.get("name"), todo.getDesc(), new Date(), false);
		return "redirect:/list-todos";
	}
	
	@RequestMapping(value="/delete-todo", method = RequestMethod.GET)
	public String deleteTodo(@RequestParam int id){
		service.deleteTodo(id);
		return "redirect:/list-todos";
	}
	
	@RequestMapping(value="/update-todo", method = RequestMethod.GET)
	public String showUpdateTodoPage(@RequestParam int id, ModelMap model){								//Added
		Todo todo = service.retrieveTodo(id);
		model.put("todo", todo);
		return "todo";
	}
	
	@RequestMapping(value="/update-todo", method = RequestMethod.POST)
	public String updateTodo(ModelMap model, @Valid Todo todo, BindingResult result){					//Added
		
		//Validation
		if (result.hasErrors()) {
			return "todo";
		}
		
		//After validation
		todo.setUser((String) model.get("name"));
		service.updateTodo(todo);
		return "redirect:/list-todos";
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

    //Initializing the Todo's
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

    public void addTodo(String name, String desc, Date targetDate, boolean isDone) {
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
    
    public Todo retrieveTodo(int id) {				//Added
        for (Todo todo : todos) {
            if (todo.getId() == id) {
                return todo;
            }
        }
        return null;
    }
    
    public void updateTodo(Todo todo) {				//Added
    	todos.remove(todo);
    	todos.add(todo);
    }
    
    
}
```