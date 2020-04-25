## Step 30: Update, Delete and Retrieve Todos using JPA Repository

In the last step, we saw that we were able to insert a row into the database. However, the flow from the database is not being shown on the screen because we have not really updated any of the retrieve or the update methods. In this step, let's complete that task of connecting the entire application to the backend.

``` java
@Controller
public class TodoController {

	//@Autowired				//To delete
	//TodoService service;
	
	@Autowired
	TodoRepository repository;

```
When you've updated the methods to use the database repository, you can login into the application, and you would see that there are no Todo's present in the application, this is due to the application is getting the data from the database. Let's now insert a Todo in the application through localhost:8080/add-todo page, and you'll see that there is a date which is inserted into the database.

**Resume**

In this step we looked how to really talk to the database, and also makes sure that we are able to update, insert, delete, and retrieve the values of Todo's.

## Complete Code Example

##### /src/main/java/com/imh/springboot/web/controller/TodoController.java

``` java
package com.imh.springboot.web.controller;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Optional;

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
			model.put("todos", repository.findByUser(name));
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
		return "redirect:/list-todos";
		
	}
	
	@RequestMapping(value="/delete-todo", method = RequestMethod.GET)
	public String deleteTodo(@RequestParam int id){
		repository.deleteById(id);
		return "redirect:/list-todos";
	}
	
	@RequestMapping(value="/update-todo", method = RequestMethod.GET)
	public String showUpdateTodoPage(@RequestParam int id, ModelMap model){
		Object todo = repository.findById(id);
		model.put("todo", todo);
		return "todo";
	}
	
	@RequestMapping(value="/update-todo", method = RequestMethod.POST)
	public String updateTodo(ModelMap model, @Valid Todo todo, BindingResult result){
		
		if (result.hasErrors()) {
			return "todo";
		}
		
		todo.setUser(getLoggedInUserName(model));
		repository.save(todo);
		return "redirect:/list-todos";
	}

}
```

##### /src/main/java/com/imh/springboot/web/service/TodoRepository.java

``` java
package com.imh.springboot.web.service;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.imh.springboot.web.model.Todo;

public interface TodoRepository extends JpaRepository <Todo, Integer> {
	
	//This method service.retrieveTodos(name) is not provided by the TodoRepository; it's not necessary to implement
	List <Todo> findByUser(String user);

	//Provided by default by the TodoRepository; it's not necessary to implement
	//service.deleteTodo(id);
	//service.retrieveTodo(id);
	//service.updateTodo(todo);
	//service.addTodo(getLoggedInUserName(model), todo.getDesc(), todo.getTargetDate(), false);
}
```