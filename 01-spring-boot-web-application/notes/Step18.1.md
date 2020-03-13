## Step 18.1: Part 1 Validations with Hibernate Validator - Using Command Bean

What we will do:

- Lets use a command bean for Todo
- Add Validations
- The JSR 303 and JSR 349 defines specification for the Bean Validation API (version 1.0 and 1.1, respectively), and Hibernate Validator is the reference implementation.
- org.hibernate:hibernate-validator

In the previous step we formatted the todo.jsp page and we added in a bit of HTML5 validations. **You should never depend on the client side validations**, because using Javascript and things like that, you can easily get around the client side validations.

The best thing to do always is to have server side validations on the server side. In this step we'll learn how to write validations on the server side. We'll use JSR303 and JSR349 Bean Validation API to define our validations. So we'll add validations on top of our Beans, and we would use the reference implementation of these, which is Hibernate Validator. Don't confuse this with Hibernate, which is the most popular implementation of JPA, this is Hibernate Validator which is just used to validate stuff.

So those kind of validations I can do using Hibernate Validator. Hibernate Validator is a reference implementation for the Bean Validation API.

**Notes**

```
Implementing Server Side Validation
~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
Concept call Command Bean or Form Backing Bean
Add Validation
Use Validation on Controller
Display Errors in View

Command Bean
~~~~~~~~~~~~
Controller
View - Spring Form tags

Flow of this step
~~~~~~~~~~~~~~~
- In list-todo.jsp we do "Add a Todo" => TodoContoller process the GET Request "/add-todo".
- The *showAddTodoPage *controller method puts a new attribute called "todo" into the model, with a new Todo objects as a value => returns the flow to the Dispatcher Servlet to go to the "todo" view.
- When we type a description in the todo.jsp, the attribute "model" putted in the model in the last step, gets filled with the value gived => when we submit the Todo with "Add", the *addTodo* controller method process the POST Request "/addTodo", and insert the new Todo in the in-program database.
```

What would happen if I want to add multiple other fields in the add-todo.jsp. Let's say I want to add in *targetDate*, or is it complet or not. In that kind of scenarios I would need to add more requests params here in the *public String addTodo* on TodoController, that could be a little difficult to maintain. What Spring MVC brings in, is the concept called Command Bean of the Form Backing Bean.

```java
@Controller
@SessionAttributes("name")
public class TodoController {
	
	@Autowired
	TodoService service;
	
	//Old way
	@RequestMapping(value="/add-todo", method = RequestMethod.POST)
	public String addTodo(ModelMap model, @RequestParam String desc){
		
		//Redirect to the list-todos to put the list of Todos in the model.
		service.addTodo((String) model.get("name"), desc, new Date(), false);
		return "redirect:/list-todos";
	}
}
```

So instead of using the *@RequestParam String desc*, what we can do, is we can say *Todo todo* in public String addTodo. So I can directly map it to a Todo object, so I can directly map it to the Todo bean. And what would happen is the data description from the web form, will be mapped to the *private String desc* in the Todo object, and then inside the *public String addTodo* method I can get **todo.desc** instead of **@RequestParam String desc**. This concept is called a Form Backing Object or a Command Bean. 

So we would map values directly from the web form to the bean, and vice versa. You would see that when we do the reverse, we would map values directly from the bean to the form as well. Once we set up the Command Bean, we can add validations on the Command Bean using the bean validation API. Once you add validations on the beans, you can use them in the Controller, you can enable them in the Controller, and if there's a validation error, you can show the validation error in the view.

So those are all the things that we would implement as part of this particular step. So let's first start with switching to a Command Bean. So, right now in add-todo.jsp the <label>Description</label> we are getting is a String. I don't want to get is as a String, but I would want to get it as part of the Todo object. How do we do that ? let's start with that right now.

```html
<label>Description</label> 
<input name="desc" type="text"class="form-control" required="required" />
```

If I want to use a Command Bean, then there are two things that we have to do. **One thing** is implemented in the Controller, so we need to update the TodoController to use the CommandBean instead of the *@RequestParam String desc* here in *public String addTodo*. **The second thing** we need to do, is we need to use Spring Form tags on the view. Spring MVC provides his own set of form tags libraries, so we need to use them in the view, so that both sides binding what happens, **so the values from the form will be bound to the bean**, and **the values from the bean will be bound to the form**. So we'll start updating the Controller.

```java
@Controller
@SessionAttributes("name")
public class TodoController {
	
	@Autowired
	TodoService service;
	
	//Old way
//	@RequestMapping(value="/add-todo", method = RequestMethod.POST)
//	public String addTodo(ModelMap model, @RequestParam String desc){
//
//		//Redirect to the list-todos to put the list of Todos in the model.
//		service.addTodo((String) model.get("name"), desc, new Date(), false);
//		return "redirect:/list-todos";
//	}
	
	//New way
	@RequestMapping(value="/add-todo", method = RequestMethod.POST)
	public String addTodo(ModelMap model, Todo todo){	//#1: Command Bean (Todo todo)
		
		//Redirect to the list-todos to put the list of Todos in the model.
		service.addTodo((String) model.get("name"), todo.getDesc(), new Date(), false);
		return "redirect:/list-todos";
	}
}
```

So what would happen when everything works fine and everything is set up on the view, is the values get directly mapped. So whenever I add a field in Todo class, I don't need to add a @RequestParam again in the *Public String addTodo* controller method. So because the Todo bean can accept those values *Todo todo*, it will be automatically bound to the Todo bean.

So, tet's now go to the Todo.jsp. In the Todo.jsp what we would need to do, is we need to use something called Spring Form. I'll copy the taglib uri for this Spring Form library at the top of the todo.jsp page (see Added #2). One of the things with Spring is, whenever we use the Spring Form library, you have to update all the form related tags to use the Form library.

```
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>		<!-- Added #2: Spring Form taglib and prefix -->
<html>
	<head>
		<title>First Web Applications</title>
		<link href="webjars/bootstrap/3.3.6/css/bootstrap.min.css" rel="stylesheet">
	</head>
	<body>
		<div class="container">
																							<!-- Before: <form method="post"> -->
			<form:form method="post" attributeName="todo">										<!-- Deprecated: commandName="todo" --> <!-- Added: Spring Form-->
				<fieldset class="form-group">
																							<!-- Before: <label>Description</label> -->				
					<form:label path="desc">Description</form:label>						<!-- Added: Spring Form -->
																							<!-- Before: <input name="desc" type="text" -->
					<form:input path="desc" type="text"
						class="form-control" required="required" />							<!-- Added: Spring Form -->
				</fieldset>
	
				<button type="submit" class="btn btn-success">Add</button>
																							<!-- Before: </form> -->
			</form:form>																	<!-- Added: Spring Form -->
		</div>
		
		<script src="webjars/jquery/1.9.1/jquery.min.js"></script>
		<script src="webjars/bootstrap/3.3.6/js/bootstrap.min.js"></script>
	
	</body>
</html>
```

We want to bind the bean which is created from here *public String addTodo(ModelMap model, Todo todo)*, so we want to bind the Todo bean, to the todo.jsp. The way we do that is using something called a CommandBean. So the thing is CommandName in todo.jsp is equal to the name of the bean <form:form method="post" commandName="todo"/>, so I would want to call it "todo". With that, I'm saying, "I'll send something called Todo to the TodoController", so what we need to do in the model ? we need to add something called "todo" as well. So in the *public String showAddTodoPage*, we have a *ModelMap model* in here, so what we want to do is add an attribute called "todo" into the Model, and we'll put the default values in here (see #1.1).

```
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

So what we we're actually doing is, we wanted to create a default object, so this is the default Todo that we get bound when we create the CommandBean. So what would happen is the value which is in here (see #1), the thing which we are putting in this model "todo" attribute, what would get, is this would be mapped to the commandName="todo" here in todo.jsp. I would want to use the commandName bean here inside the *form:label*, as well (see #2.1). 

One of the things in *form:label*, is we need to say which field is this "Description" of.  This is the "Description" of the description field *private String desc;* in the Todo class, so to do that I need to say *form:label path="desc"*. Similar to that, we'll update the rest of the form:input path="desc", as well; what would happen is this Spring *form:input path="desc"*, once you give a path to it, it would automatically create a name field. We'll look at how that happened very soon.

```
<form:form method="post" attributeName="todo">	<!-- Deprecated #2.1: form:form method="post"  commandName="todo" -->
	<fieldset class="form-group">
		<form:label path="desc">Description</form:label>	
		<form:input path="desc" type="text"
			class="form-control" required="required" />
	</fieldset>
	
	<button type="submit" class="btn btn-success">Add</button>
</form:form>	
```

Now if we go to add-todo.jsp view, and we add a new Todo, the application doesn't run due to an error saying no default constructor found. One of the things with binding is, Spring expects a default constructor in the Todo object. So If you want to Spring do the binding, then one of the things Spring expects is a no argument constructor, so let's create a default no argument constructor.

```java
public class Todo {
	
    private int id;
    private String user;
    private String desc;
    private Date targetDate;
    private boolean isDone;

    public Todo() {
		super();
	}
}
```

Now that we wrote the code for it, let's run the project and see what's happened.

**Resume**

In the method *public String addTodo (ModelMap, Todo todo)*, we have added the CommandBean (**Todo todo**), instead of mapping into the description field with @RequestParam String desc, we are now trying to map it to Todo.

The other thing which we did, was to use the Spring Form tag libraries <%@taglib uri="http://www.springframework.org/tags/form">. So what we did is, instead of using the form, we are using the form:form, so form:form is the Spring form tag libraries, and we are using the form tag from that as well. So if you are interested in understanding the tag libraries a little bit more and understand what are the options which are present, you can just control+click above the form:form>, and you'll be able to see the source documentation, and all the tags which are present in the Spring form tag library.

In this step, what we wanted to do is really add validationn. But before we add validations, we need to use Command Bean. In the next part of this particular step, we would look at how to enable validations. Until now we have not enabled anything related to validations, we are not added a validation, we did nothing related to validations. In the second part of this step, we look at how to add the validations.

## Complete Code Example

##### /src/main/webapp/WEB-INF/jsp/todos.jsp

```html
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>					<!-- Added: Spring Form taglib and prefix -->
<html>
	<head>
		<title>First Web Applications</title>
		<link href="webjars/bootstrap/3.3.6/css/bootstrap.min.css" rel="stylesheet">
	</head>
	<body>
		<div class="container">
																							<!-- Before: <form method="post"> -->
				<form:form method="post" modelAttribute="todo">								<!-- Added: Spring Form--> <!-- Deprecated: commandName="todo" -->
				<fieldset class="form-group">
																							<!-- Before: <label>Description</label> -->				
					<form:label path="desc">Description</form:label>						<!-- Added: Spring Form -->
																							<!-- Before: <input name="desc" type="text" -->
					<form:input path="desc" type="text"
						class="form-control" required="required" />							<!-- Added: Spring Form -->
				</fieldset>
	
				<button type="submit" class="btn btn-success">Add</button>
																							<!-- Before: </form> -->
			</form:form>																	<!-- Added: Spring Form -->
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

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
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
	
	@RequestMapping(value="/add-todo", method = RequestMethod.GET)	//Added: process the "Add a Todo" GET Request from list-todos.
	public String showAddTodoPage(ModelMap model){
		model.addAttribute("todo", new Todo(0,(String) model.get("name"), "", new Date(), false));
		return "todo";
	}
	
	@RequestMapping(value="/add-todo", method = RequestMethod.POST)
	public String addTodo(ModelMap model, Todo todo){
		
		//Redirect to the list-todos to put the list of Todos in the model.
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

public class Todo {
	
    private int id;
    private String user;
    private String desc;
    private Date targetDate;
    private boolean isDone;

    public Todo() {															//Added
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