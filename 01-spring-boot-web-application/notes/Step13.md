## Step 13: Add new todo

What You Will Learn during this Step:

- Add Facility to add New Todo
- todo.jsp
- Importance of redirect:/list-todos

In this step, we will want to add the functionality to add on your Todo. Now we have a list of todos here in localhost:8080/list-todos, but what we want to be able to do is add a link, so that I can add in the Todo stuff.

##### /src/main/webapp/WEB-INF/jsp/list-todos.jsp

```
<html>
	<head>
		<title>First Web Applications</title>
	</head>
	<body>
		Here are the list of ${name}'s your todos: 		<!-- Added -->
		${todos}										<!-- Added -->
		<BR/>
		<a href="/add-todo)">Add a Todo</a> 			<!-- Added -->
	</body>
</html>
```

##### /src/main/webapp/WEB-INF/jsp/todos.jsp

```
<html>
	<head>
		<title>First Web Applications</title>
	</head>
	<body>
		ADD Todo Page for ${name}
		<form method="post">
			Description: <input name="desc" type="text"/> <input type="submit" />
		</form>
	</body>
</html>
```

```java
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
	
	@RequestMapping(value="/add-todo", method = RequestMethod.GET)	//Added
	public String showAddTodoPage(ModelMap model){
		return "todo";
	}
	
	@RequestMapping(value="/add-todo", method = RequestMethod.POST)	//Added
	public String addTodo(ModelMap model, @RequestParam String desc){

		//First option: It means I have to duplicate the logic again to put the list of Todos in the model.
//		String name = (String) model.get("name");
//		model.put("todos", service.retrieveTodos(name));
//		service.addTodo((String) model.get("name"), desc, new Date(), false);
//		return "list-todos";
		
		//Second option: Redirect to the list-todos to put the list of Todos in the model.
		service.addTodo((String) model.get("name"), desc, new Date(), false);
		return "redirect:/list-todos";
	}

}
```

```java
package com.imh.springboot.web.service;

import org.springframework.stereotype.Controller;

@Controller
public class LoginService {

	//Hard coded validation
	public boolean validateUser (String userid, String password) {
		//userid: imh, password: dummy 						//Added: user and password changed
		return userid.equalsIgnoreCase("imh") 
				&& password.equalsIgnoreCase("dummy");
	}
}
```

So the flow we have right now is, you can log in with user "imh" and password "dummy" in localhost:8080/login. In the welcome page do "Click here" to list the Todos, it shows all the Todos that you have, and you can now add a Todo as well. If we add a Todo "This is a test", you can see that there's a new Todo with "This is a test". Now, we have a way to add a Todo as well.
	
## Complete Code Example

##### /src/main/webapp/WEB-INF/jsp/list-todos.jsp

```
<html>
	<head>
		<title>First Web Applications</title>
	</head>
	<body>
		Here are the list of ${name}'s your todos: 
		${todos}	<!-- Added -->
		<BR/>
		<a href="/add-todo">Add a Todo</a> <!-- Added -->
	</body>
</html>
```

##### /src/main/webapp/WEB-INF/jsp/todos.jsp

```
<html>
	<head>
		<title>First Web Applications</title>
	</head>
	<body>
		ADD Todo Page for ${name}
		<form method="post">
			Description: <input name="desc" type="text"/> <input type="submit" />
		</form>
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

}
```

##### /src/main/java/com/imh/springboot/web/controller/LoginService.java

```java
package com.imh.springboot.web.service;

import org.springframework.stereotype.Controller;

@Controller
public class LoginService {

	//Hard coded validation
	public boolean validateUser (String userid, String password) {
		//userid: imh, password: dummy
		return userid.equalsIgnoreCase("imh") 
				&& password.equalsIgnoreCase("dummy");
	}
}
```