<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<%@ page import="java.util.List, 
				 java.util.LinkedHashMap,
				 java.util.Map"%>
<%@ page import="com.demo.entity.Department"%>
    
    
<!DOCTYPE html>
<html>
	<head>
		<meta charset="UTF-8">
		<title>Departments With At Least A Given Number Of Employees</title>
	
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
		    	<a href='create-new-department' class='btn btn-primary'><span class="glyphicon glyphicon-add-button"></span> Add New Department</a>
		    	<a href='logout' class='btn btn-danger'><span class="glyphicon glyphicon-log-out"></span> Logout</a>
		  	</div>
		</div>
		<div class='container'>
			<br>
			
			<h2 style="text-align: center"><%=request.getAttribute("informationType") %></h2> 
			
			<table  class='table table-bordered table-striped table-hover'>
			<%	
				@SuppressWarnings("unchecked")
				List<String> columnLabel = (List<String>) request.getAttribute("columnLabel");
			
				@SuppressWarnings("unchecked")
				LinkedHashMap<Department, Integer> records = (LinkedHashMap<Department, Integer>) request.getAttribute("departmentList");				
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
					for(Map.Entry<Department, Integer> entry : records.entrySet()) {
				%>
				<tr>
					<td><%= entry.getKey().getId() 	%></td>
					<td><%= entry.getKey().getName()%></td>	
					<td><%= entry.getValue() 		%></td>
				</tr>					
				<%
					}
				%>
			</table>
		</div>
		<hr>
	</body>
</html>