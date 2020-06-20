## Step 22: Preparing for Spring Security

- Prepare for Using Spring Security
- Remove All the Login Related Functionality
- Make Welcome the default page - with some hardcoding to start with.
- Refactor getLoggedInUserName
- Update Home Page Link in navigation

## Complete Code Example

##### /src/main/webapp/WEB-INF/jsp/common/navigation.jspf

```xml
<nav role="navigation" class="navbar navbar-default">
	<div class="">
		<a href="http://www.imh.com" class="navbar-brand">Imh</a>
	</div>
	<div class="navbar-collapse">
		<ul class="nav navbar-nav">
			<li class="active"><a href="/">Home</a></li>
			<li><a href="/list-todos">Todos</a></li>
		</ul>
	</div>
</nav>
```

##### /src/main/java/com/imh/springboot/web/controller/LoginController.java

```java
package com.imh.springboot.web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;

import com.imh.springboot.web.service.LoginService;

@Controller
@SessionAttributes("name")
public class LoginController {
	
	@Autowired
	LoginService service;
	
	@RequestMapping(value="/", method = RequestMethod.GET)			//Added: value="/login" by value="/"
	public String showLoginPage(ModelMap model){
		model.put("name", "imh");									//Added
		return "login";
	}
	
}
```

##### /src/main/java/com/imh/springboot/web/controller/TodoController.java

```
package com.imh.springboot.web.controller;

import java.text.SimpleDateFormat;
import java.util.Date;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
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
	
	@InitBinder
	protected void initBinder(WebDataBinder binder) {
		SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
		binder.registerCustomEditor(Date.class, new CustomDateEditor(
				dateFormat, false));
	}

	private String getLoggedInUserName(ModelMap model) {							//Added
		String name = (String) model.get("name");
		return name;
	}
	
	@RequestMapping(value="/list-todos", method = RequestMethod.GET)
	public String showTodos(ModelMap model){
			String name = getLoggedInUserName(model);
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
		if (result.hasErrors()) {
			return "todo";
		}
		service.addTodo((String) model.get("name"), todo.getDesc(), todo.getTargetDate(), false);		
		return "redirect:/list-todos";
	}
	
	@RequestMapping(value="/delete-todo", method = RequestMethod.GET)
	public String deleteTodo(@RequestParam int id){
		service.deleteTodo(id);
		return "redirect:/list-todos";
	}
	
	@RequestMapping(value="/update-todo", method = RequestMethod.GET)
	public String showUpdateTodoPage(@RequestParam int id, ModelMap model){
		Todo todo = service.retrieveTodo(id);
		model.put("todo", todo);
		return "todo";
	}
	
	@RequestMapping(value="/update-todo", method = RequestMethod.POST)
	public String updateTodo(ModelMap model, @Valid Todo todo, BindingResult result){
		
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