## Step 18.2: Part 2 Using JSR 349 Validations

In the part two of this step, we want to enable validation. In the part one of this step, we created a Command Bean. The main advantage of the Command Bean or Form Backing Bean, is that we now have double binding. What is double binding ?

We have a Bean which we are mapping to a form, and we have a form which we are mapping to a Bean. What I'm talking about ? what is happening is, when I would want to do an "Add" Todo in add-todo.jsp page, what's happening in the background, is that this *model.addAttribute* in *public String showAddTodoPage*, the defualt Todo created in *new Todo(0,(String) model.get("name"), "",new Date(), false)* is added into the model, this default todo which is added into the model, get bound to the *CommandName=todo* in the add-todo.jsp page. So because they hava the same name, this CommandName gets bounded into the form with the values gived in the instruction *model.addAttribute("todo", new Todo(0,(String) model.get("name"), "",new Date(), false));*.

```java
@Controller
@SessionAttributes("name")
public class TodoController {
	
	@Autowired
	TodoService service;
	@RequestMapping(value="/add-todo", method = RequestMethod.GET)
	public String showAddTodoPage(ModelMap model){
		model.addAttribute("todo", new Todo(0,(String) model.get("name"), "", new Date(), false));	//#1.1
		return "todo";
	}
}
```

So whatever values we put in this "todo", gets mapped to this *form:form method="post" attributeName="todo"*. The values from this Todo bean *new Todo(0,(String) model.get("name"), "",new Date(), false))*, get mapped to this *form:form method="post" commandName="todo"*. So that's the first stage, Bean to -> Form; so from the beans the values get mapped to the form.	

```html
<form:form method="post" attributeName="todo">	<!-- Deprecated #2.1: form:form method="post"  commandName="todo" -->
	<fieldset class="form-group">
		<form:label path="desc">Description</form:label>	
		<form:input path="desc" type="text"
			class="form-control" required="required" />
	</fieldset>
	
	<button type="submit" class="btn btn-success">Add</button>
</form:form>	
```

The second side of the Binding, is form to Bean. If I type "Learn something" in add-todo.jsp page, and press "Add", this value "Learn something" which is inside the form would go and get bound to the Todo object that we have here in *public String addTodo(ModelMap model, Todo todo),*. This *Todo todo* is the second side of the binding, this is form to -> Bean. So whatever value you are entering here in add-todo.jsp page, gets bound to the bean *Todo todo*, and it's added to the in-program database with the instruction *servide.addTodo*.

So in the first step, we enable double binding, how did we enable double binding ? by using a Command Bean or the Form Backing Bean. So double binding is basically the binding which happens from the Bean -> Form, and from the Form -> Bean.

**Notes**

```
Implementing Server Side Validation
~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
Concept call Command Bean or Form Backing Bean
Double Binding
Bean -> Form
Form -> Bean

Add Validation
Use Validation on Controller
Display Errors in View

Command Bean
~~~~~~~~~~~~
Controller
View - Spring Form tags
```

We would want to add the validations. If in the add-todo.jsp page I type "123" in the field Description and submit with "Add", I'll create a Todo with a description of three characters. I don't want to allow that, what I want to do, I would want to say "You have to enter almost six characters". So if you enter one, two, three, four, or five, that's not sufficient. You should at least enter six characters for the description to be a valid description. I would want to add a validation around that.

How do I do that ? That's basically what we would be looking right now. The first step of adding any validation, is to use something called a Bean Validation API. So I want to use the bean validation API to add validation around the description field *private String desc* in Todo class. How do I add the validation around the description field ? it's very simple, I would want to say "it should have a minimum of 10 characters". So the description, should have a minimum of 10 characters, the way you would enable that is by using @Size annotation.

The @Size is part of the Java Validation API, in which you can specify what is the maximum, and what is the minimum as well. So for now I'll just put the minimum, so I would want a minimum of 10 characters, so that's the minimum of characters that I would want to allow, and you can add in a message, what's the message you want to show if the validation fails.

```java
public class Todo {
	
    private int id;
    private String user;
    @Size(min=10, message="Enter at least 10 Characters...")
    private String desc;
    private Date targetDate;
    private boolean isDone;
 
 } 
 ```
 
Now I have the validation on the Todo bean, but the validation is not yet enabled on the TodoController. Where's the binding takes place ? actual, the binding takes place on the controller, and in the controller, you can enable validation by adding @Valid annotation.
 
So now I have added in validation around the Todo, so I'm enabling validation into bean Todo. How do I know if the validation succeded or not ? **So the validation is enabled, I would want to know whether the validation really succeeded or failed, the way we would do that, is using something called BindingResult**. Whenever you use @Valid on a Command Bean (Todo todo), there would be another bean which would be populated called BindingResult. This BindingResult we'll have the result of the validation. Now this result, can show whether the validation succeeded or not.

```java
@Controller
@SessionAttributes("name")
public class TodoController {
	
	@Autowired
	TodoService service;

	@RequestMapping(value="/add-todo", method = RequestMethod.POST)
	public String addTodo(ModelMap model, @Valid Todo todo, BindingResult result){
		//Redirect to the list-todos to put the list of Todos in the model.
		if (result.hasErrors()) {						//Added #1: if there are errors, the user gets redirected to the todo.jsp page.
			return "todo";
		}
		service.addTodo((String) model.get("name"), todo.getDesc(), new Date(), false);
		return "redirect:/list-todos";
	}
}
```
The problem now, is the add-todo.jsp page it's not showing the error message in here. If the error message is shown, then I can be clear that the validation is getting executed. How do I show the error message in the add-todo.jsp page ? If you want to display the error message on a specific field *path=desc*, there is an another specific tag *form:errors*.

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
				<fieldset class="form-group">				
					<form:label path="desc">Description</form:label>
					<form:input path="desc" type="text"
						class="form-control" required="required"/>
					<form:errors path="desc" cssClass="text-warning"/>
				</fieldset>
				
				<button type="submit" class="btn btn-success">Add</button>	<!-- Added -->
			</form:form>
		</div>
		
		<script src="webjars/jquery/1.9.1/jquery.min.js"></script>
		<script src="webjars/bootstrap/3.3.6/js/bootstrap.min.js"></script>
	
	</body>
</html>
```

What we are looking at in this specific step, is how to add validations. So what we did in this particular part of this step is, first we started with adding the validation on the Todo bean, so on the Todo bean, we added the validation using Java Validation API, @Size is one of the things which is present in the Java validation API. If you really want to get into @Size, then you can look at all other validations which are present in the validation-api-1.1.0.Final.jar. You can look up the validation API documentation to find out more information about what other validations are present. 

After we added validation on the bean with @Size, we added the *@Valid Todo todo* annotation to enable validation on the Controller, and once when we added @Valid the result will be populated into something called BindingResult, so what we did is we took that result, and if there are errors, we return the user back to the todo.jsp page. In the todo.jsp page, we added in the page the *form:errors*, so that we can show the warning, or the error message to the user.

Those are the different things that we have done as part of this particular step. So in this step, in the first part we started with adding a Command Bean, and in the second part we have added in validations.

## Complete Code Example

##### /src/main/webapp/WEB-INF/jsp/todos.jsp

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
		if (result.hasErrors()) {
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

}
```

##### /src/main/java/com/imh/springboot/web/model/Todo.java

```java
package com.imh.springboot.web.model;

import java.util.Date;

import javax.validation.constraints.Size;

public class Todo {
	
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