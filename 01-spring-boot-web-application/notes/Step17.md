## Step 17: Format Add Todo Page and Adding Basic HTML5 form validation

What You Will Learn during this Step:

- Format Add Todo Page
- Add Html5 Form Validations

What will do now, is we would want to format our add Todo page, and we would want to add in HTML5 form validations.

If you just press enter in todo.jsp it would add an Employee to do, so I would want that to be prevented. So I can say something like  class="form-control" required="required", this actually triggered in required validation on the client-side. So any compatible HTML5 browser, prevents this form would be from submitted if you have not entered any value in the Todo description.

```html
<html>
	<head>
		<title>First Web Applications</title>
		<link href="webjars/bootstrap/3.3.6/css/bootstrap.min.css" rel="stylesheet">	<!-- Added: Bootstrap CSS stylesheet -->
	</head>
	<body>
		<div class="container">															<!-- Added: Bootstrap container class -->
			<form method="post">
				<fieldset class="form-group">
					<label>Description</label> <input name="desc" type="text"
						class="form-control" required="required" />						<!-- Added: HTML5 required validation -->
				</fieldset>
	
				<button type="submit" class="btn btn-success">Add</button>
			</form>
		</div>
		
		<script src="webjars/jquery/1.9.1/jquery.min.js"></script>						<!-- Added: Bootstrap javascript files -->
		<script src="webjars/bootstrap/3.3.6/js/bootstrap.min.js"></script>
	
	</body>
</html>
```

This was a very quick step in which we learned how to format the add-todo page, and also we added in HTML5 required validation on the client side, to prevent add an empty employee. We will add more fields to this screen and do a lot more stuff in the next few steps.

## Complete Code Example

##### /src/main/webapp/WEB-INF/jsp/todos.jsp

```html
<html>
	<head>
		<title>First Web Applications</title>
		<link href="webjars/bootstrap/3.3.6/css/bootstrap.min.css" rel="stylesheet">
	</head>
	<body>
		<div class="container">															
			<form method="post">
				<fieldset class="form-group">
					<label>Description</label> <input name="desc" type="text"
						class="form-control" required="required" />
				</fieldset>
	
				<button type="submit" class="btn btn-success">Add</button>
			</form>
		</div>
		
		<script src="webjars/jquery/1.9.1/jquery.min.js"></script>
		<script src="webjars/bootstrap/3.3.6/js/bootstrap.min.js"></script>
	
	</body>
</html>
```