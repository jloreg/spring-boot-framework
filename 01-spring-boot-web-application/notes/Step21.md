## Step 21: JSP Fragments and Navigation Bar

- Add a navigation bar
- Use JSP Fragments (refactor jsp fragments)
- Exercise : Align the login & welcome pages.
- Exercise : Highlight the correct menu item.

Note. We recommend using Chrome Browser from here on !

## Complete Code Example

##### /src/main/webapp/WEB-INF/jsp/todo.jsp

``` xml
<%@ include file="common/header.jspf" %>
<%@ include file="common/navigation.jspf" %>

		<div class="container">		
			<form:form method="post" modelAttribute="todo">					<!-- Deprecated: commandName="todo" -->
				<form:hidden path="id"/>
				<fieldset class="form-group">				
					<form:label path="desc">Description</form:label>
					<form:input path="desc" type="text"
						class="form-control" required="required"/>
					<form:errors path="desc" cssClass="text-warning"/>
				</fieldset>
				
				<fieldset class="form-group">													<!-- Added: -->				
					<form:label path="targetDate">Target Date</form:label>
					<form:input path="targetDate" type="text"
						class="form-control" required="required"/>
					<form:errors path="targetDate" cssClass="text-warning"/>
				</fieldset>
				
				<button type="submit" class="btn btn-success">Add</button>
			</form:form>
		</div>
		
		<%@ include file="common/footer.jspf" %>
```

##### /src/main/webapp/WEB-INF/jsp/list-todos.jsp

``` xml
<%@ include file="common/header.jspf" %>
<%@ include file="common/navigation.jspf" %>

		<div class="container">
			<table class="table table-striped">
				<caption> Your todos are </caption>
				<thead>
					<tr>
						<th>Description</th>
						<th>Target Date</th>
						<th>Is it Done?</th>
						<th></th>
					</tr>
				</thead>
				<tbody>
					<!-- JSTL For Loop -->
					<c:forEach items="${todos}" var="todo">
						<tr>															<!-- Added: -->	
							<td>${todo.desc}</td>
							<td><fmt:formatDate value="${todo.targetDate}" pattern="dd/MM/yyyy"/></td>
							<td>${todo.done}</td>
							<td><a type="button" class="btn btn-success"
								href="/update-todo?id=${todo.id}">Update</a></td>
							<td><a type="button" class="btn btn-warning"
								href="/delete-todo?id=${todo.id}">Delete</a></td>
						</tr>
					</c:forEach>
				</tbody>
			</table>
			<div>
				<a class="button" href="/add-todo">Add a Todo</a>
			</div>	
		</div>
		
<%@ include file="common/footer.jspf" %>
```

##### /src/main/webapp/WEB-INF/jsp/login.jsp

``` xml
<%@ include file="common/header.jspf" %>
<%@ include file="common/navigation.jspf" %>

	<div class="container" >
	
		<font color="red">${errorMessage}</font>
		<form method="post">
			Name : <input type="text" name="name" />
			Password : <input type="password" name="password" />
			<input type="submit" />
		</form>
	</div>

<%@ include file="common/footer.jspf" %>
```

##### /src/main/webapp/WEB-INF/jsp/welcome.jsp

``` xml
<%@ include file="common/header.jspf" %>
<%@ include file="common/navigation.jspf" %>
<div class="container" >
		Welcome ${name} !! <a href="/list-todos">Click here</a> to manage your todo's.
</div>
<%@ include file="common/footer.jspf" %>
```

##### /src/main/webapp/WEB-INF/jsp/common/header.jspf

``` xml
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>

<html>
	<head>
		<title>First Web Applications</title>
		<link href="webjars/bootstrap/3.3.6/css/bootstrap.min.css" rel="stylesheet">
	</head>
	
	<body>
```

##### /src/main/webapp/WEB-INF/jsp/common/navigation.jspf

``` xml
<nav role="navigation" class="navbar navbar-default">
	<div class="">
		<a href="http://www.imh.com" class="navbar-brand">Imh</a>
	</div>
	<div class="navbar-collapse">
		<ul class="nav navbar-nav">
			<li class="active"><a href="/login">Home</a></li>
			<li><a href="/list-todos">Todos</a></li>
		</ul>
	</div>
</nav>
```

##### /src/main/webapp/WEB-INF/jsp/common/footer.jspf

``` xml
<script src="webjars/jquery/1.9.1/jquery.min.js"></script>
<script src="webjars/bootstrap/3.3.6/js/bootstrap.min.js"></script>
<script src="webjars/bootstrap-datepicker/1.0.1/js/bootstrap-datepicker.js"></script>

			<script>
				$('#targetDate').datepicker({
					format : 'dd/mm/yyyy'
				});
			</script>

	</body>
</html>
```