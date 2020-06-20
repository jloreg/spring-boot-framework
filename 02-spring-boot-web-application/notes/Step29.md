## Step 29: Insert Todo using JPA Repository

In the previous step we created the TodoRepository, so let's now start making use of it. 

``` java
@Controller
public class TodoController {
	
	@Autowired
	TodoService service;
	
	@Autowired
	TodoRepository repository;
}
```

So now the TodoRepository is present in here, what will do in this step, is let's start creating just the Create Todo to use this repository. So what will happen is when we create a new Todo, it will be saved to the database. However when we retrieve the details, it will not retrieve the new Todo because it is still talking to the ArrayList, so it's all the retrieve methods are still talking to the ArrayList, that's fine what we'll do is we'll insert a Todo and look at the h2-console, look at the database to see if it's inserted properly. So let's go to the addTodo method, and over here what we are doing is add the necessary instructions.

``` java
@RequestMapping(value="/add-todo", method = RequestMethod.POST)
public String addTodo(ModelMap model, @Valid Todo todo, BindingResult result){
		
	if (result.hasErrors()) {
		return "todo";
	}
		
	todo.setUser(getLoggedInUserName(model));
	repository.save(todo);
	//service.addTodo(getLoggedInUserName(model), todo.getDesc(), todo.getTargetDate(), false);
	return "redirect:/list-todos";
		
}
```

ĺnstead of the service, I would want to use the repository method, so let's say *repository.save(todo);*. However the important thing to note, is that the Todo, does not contain the user name, so let's say *todo.setUser(getLoggedInUserName(model));*. Now instead of saving it to the ArrayList, what we are doing is, we are saving it to the database, we are using the JpaRepository, saving the Todo to the database.

Now, let's go to the localhost:8080/h2-console/, connect, and do a "SELECT * FROM TODO". You would see that there are no rows in the database, but if you add a new Todo through application *localhost:8080/login* and you run again the query "SELECT * FROM TODO" against the database, you'll see that a new row inserted properly.

**Resume**

Using JPA and using Spring JPA is a very simple thing. It makes it very easy to talk to the database, in this step we changed our create Todo operation *public String addTodo* to connect to the database, and we saw that we were able to insert a row into the database when we add a new Todo in.

## Complete Code Example

##### /src/main/java/com/imh/springboot/web/controller/TodoController.java

``` java
package com.imh.springboot.web.controller;

import java.text.SimpleDateFormat;
import java.util.Date;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.imh.springboot.web.model.Todo;
import com.imh.springboot.web.service.TodoRepository;
import com.imh.springboot.web.service.TodoService;

@Controller
public class TodoController {
	
	@Autowired
	TodoService service;
	
	@Autowired
	TodoRepository repository;
	
	@InitBinder
	protected void initBinder(WebDataBinder binder) {
		SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
		binder.registerCustomEditor(Date.class, new CustomDateEditor(
				dateFormat, false));
	}

	private String getLoggedInUserName(ModelMap model) {
		
		Object principal = 
				SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		
		if (principal instanceof UserDetails) {
			return ((UserDetails) principal).getUsername();
		}
		
		return principal.toString();
	}
	
	@RequestMapping(value="/list-todos", method = RequestMethod.GET)
	public String showTodos(ModelMap model){
			String name = getLoggedInUserName(model);
			model.put("todos", service.retrieveTodos(name));
		return "list-todos";
	}
	
	@RequestMapping(value="/add-todo", method = RequestMethod.GET)
	public String showAddTodoPage(ModelMap model){
		model.addAttribute("todo", new Todo(0, getLoggedInUserName(model), "Default Desc",
				new Date(), false));
		return "todo";
	}
	
	@RequestMapping(value="/add-todo", method = RequestMethod.POST)
	public String addTodo(ModelMap model, @Valid Todo todo, BindingResult result){
		
		if (result.hasErrors()) {
			return "todo";
		}
		
		todo.setUser(getLoggedInUserName(model));
		repository.save(todo);
//		service.addTodo(getLoggedInUserName(model), todo.getDesc(), todo.getTargetDate(), false);
		return "redirect:/list-todos";
		
	}
	
	@RequestMapping(value="/delete-todo", method = RequestMethod.GET)
	public String deleteTodo(@RequestParam int id){
		
		if (id == 1) {
			throw new RuntimeException("Something went wrong");
		}
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
		
		if (result.hasErrors()) {
			return "todo";
		}
		
		todo.setUser(getLoggedInUserName(model));
		service.updateTodo(todo);
		return "redirect:/list-todos";
	}

}
```