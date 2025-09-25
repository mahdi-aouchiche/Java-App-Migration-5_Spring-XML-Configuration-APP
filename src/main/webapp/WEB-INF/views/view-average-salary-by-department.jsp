<%@ page language="java" contentType="text/html; charset=UTF-8"
    	 pageEncoding="UTF-8"%>
<%@ page import="java.util.ArrayList, 
				 java.util.LinkedHashMap,
				 java.util.Map"%>
<%@ page import="com.demo.entity.Department"%>

<!DOCTYPE html>
<html>
	<head>
		<meta charset="UTF-8">
		<title>Average Salary Of Each Department</title>
		
		<link rel='stylesheet' href='https://maxcdn.bootstrapcdn.com/bootstrap/3.4.1/css/bootstrap.min.css'>
		<script src='https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js'></script>
		<script src='https://maxcdn.bootstrapcdn.com/bootstrap/3.4.1/js/bootstrap.min.js'></script>
		<style>
			th { text-align:center; }
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
			
			<table class='table table-bordered table-striped table-hover'>
			<%	
				@SuppressWarnings("unchecked")
				ArrayList<String> columnLabel = (ArrayList<String>) request.getAttribute("columnLabel");
			
				@SuppressWarnings("unchecked")
				LinkedHashMap<Department, Double> records = (LinkedHashMap<Department, Double>) request.getAttribute("records");				
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
					for(Map.Entry<Department, Double> entry : records.entrySet()) {
				%>
				<tr>
					<td style='text-align:center'>
						<%= entry.getKey().getId() %>
					</td>
					<td style='text-indent: 20%'>
						<%= entry.getKey().getName()%>
					</td>	
					<td style='text-indent: 40%'>
						<%= String.format("$%,.2f", entry.getValue()) %>
					</td>
				</tr>					
				<%
					}
				%>
			</table>
		</div>
		<hr>
	</body>
</html>