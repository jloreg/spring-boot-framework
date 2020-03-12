## Step 16: Let's delete a Todo

What You Will Learn during this Step:

- Add functionality to delete a todo

We now have a page where we are showing a list of todos to do this. I would want to be able able to add the functionality for deleting each of these Todo's. To delete a specific Todo, I can do that by passing the Todo id as a parameter 

```
<td><!-- Added: Bootstrap class and reference to the URL that is mapped by the TodoController -->
	<a type="button" class="btn btn-warning"
	      href="/delete-todo?id=${todo.id}">Delete</a>
</td>
```

##### /src/main/java/com/imh/springboot/web/controller/TodoController.java
```
@Controller
@SessionAttributes("name")
public class TodoController {
	
	@Autowired
	TodoService service;
	
	@RequestMapping(value="/delete-todo", method = RequestMethod.GET)			//Added
	public String deleteTodo(@RequestParam int id){
		service.deleteTodo(id);
		return "redirect:/list-todos";
	}
}
```

In this step, we added the functionality to delete the todo. So all we had to do, was we added a link to the list-todo.jsp page to be able to delete the Todo, and then we added a controller method *public String deleteTodo*, to delete the Todo passing in the Id.

## Complete Code Example

##### /src/main/webapp/WEB-INF/jsp/list-todos.jsp

```
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
					</tr>
				</thead>
				<tbody>
					<!-- JSTL For Loop -->
					<c:forEach items="${todos}" var="todo">
						<tr>
							<th>${todo.desc}</th>
							<th>${todo.targetDate}</th>
							<th>${todo.done}</th>
							<td>									<!-- Added: -->
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

##### /src/main/java/com/imh/springboot/web/controller/TodoController.java

```java
package com.imh.springboot.web.controller;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.annotation.RequestParam;

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
		return "todo";
	}
	
	@RequestMapping(value="/add-todo", method = RequestMethod.POST)
	public String addTodo(ModelMap model, @RequestParam String desc){
		
		//Redirect to the list-todos to put the list of Todos in the model.
		service.addTodo((String) model.get("name"), desc, new Date(), false);
		return "redirect:/list-todos";
	}
	
	@RequestMapping(value="/delete-todo", method = RequestMethod.GET)			//Added
	public String deleteTodo(@RequestParam int id){
		service.deleteTodo(id);
		return "redirect:/list-todos";
	}

}
```