<%@ page language="java" contentType="text/html; charset=UTF-8"
    	 pageEncoding="UTF-8"%>
<%@ page import="java.util.List"%>
<%@ page import="com.demo.entity.Employee"%>

<!DOCTYPE html>
<html>
	<head>
		<meta charset="UTF-8">
		<title>Average Salary Of Each Department</title>
		
		<link rel='stylesheet' href='https://maxcdn.bootstrapcdn.com/bootstrap/3.4.1/css/bootstrap.min.css'>
		<script src='https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js'></script>
		<script src='https://maxcdn.bootstrapcdn.com/bootstrap/3.4.1/js/bootstrap.min.js'></script>
		<style>
			table th, td { text-align:center; }
		</style>
	</head>
	<body>
		<br>
		<div class="container">
		  	<div class="pull-right">
		    	<a href='option-menu' class='btn btn-info'><span class="glyphicon glyphicon-menu-left"></span> Go Back to Menu</a>
		    	<a href='logout' class='btn btn-danger'><span class="glyphicon glyphicon-log-out"></span> Logout</a>
		  	</div>
		</div>
		<div class='container'>
			<br>
			
			<h2 style="text-align: center"><%=request.getAttribute("informationType") %></h2>	
			<table class='table table-bordered table-striped table-hover'>
			<%	
				@SuppressWarnings("unchecked")
				List<String> columnLabel = (List<String>) request.getAttribute("columnLabel");
			
				@SuppressWarnings("unchecked")
				List<Employee> records = (List<Employee>) request.getAttribute("records");				
			%>	
				<tr>
				<% 
					for (String label : columnLabel) { 
				%>
					<th><%= label %></th>
				<% 
					} 
				%>
				</tr>
				<%
					// print the table rows
					for(Employee employee : records) {
				%>
				<tr>
					<td><%= employee.getId() 	%></td>
					<td><%= employee.getName() %></td>
					<td><%= employee.getAge()   %></td>	
					<td><%=String.format("$%,.2f", employee.getSalary()) %></td>
				</tr>					
				<%
					}
				%>
			</table>
		</div>
		<hr>
	</body>
</html>