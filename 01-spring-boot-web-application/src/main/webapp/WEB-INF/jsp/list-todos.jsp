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