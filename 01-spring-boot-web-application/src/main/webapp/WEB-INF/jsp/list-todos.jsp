<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<html>
	<head>
		<title>Todo's for ${name}</title>	<!-- Added -->
	</head>
	<body>									<!-- Added -->
		<!-- <H1>Your Todos</H1> -->
		<table>
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
					</tr>
				</c:forEach>
			</tbody>
		</table>
		<BR/>
		<a href="/add-todo">Add a Todo</a>
	</body>
</html>