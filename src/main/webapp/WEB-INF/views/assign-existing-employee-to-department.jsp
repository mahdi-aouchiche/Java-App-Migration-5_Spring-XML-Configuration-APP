<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
  
<%@ page import="java.util.List" %>  
<%@ page import="com.demo.entity.Employee,com.demo.entity.Department" %>
				 
<!DOCTYPE html>
<html>
	<head>
		<meta charset="UTF-8">
		<title>Add An Existing Employee to a Department</title>
		
		<link rel='stylesheet' href='https://maxcdn.bootstrapcdn.com/bootstrap/3.4.1/css/bootstrap.min.css'>
		<script src='https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js' type="text/javascript"></script>
		<script src='https://maxcdn.bootstrapcdn.com/bootstrap/3.4.1/js/bootstrap.min.js' type="text/javascript"></script>

		<style type="text/css">
			body {
				display: grid;
				place-items: center;
				min-height: 100vh;
				margin: 0;
			}
			
			form {
				height: 400px;
				width: 500px;
				margin: 0 auto;
				padding: 20px;
			}
		
			table {
				border: 2px solid #ccc;
				width: 100%;
				height: 60%;
			}
					
			tr {
				height: 40px;
			}
			
			th {
				border: 2px solid #ccc;
				padding: 5px;
				text-align: center;
			}
			
			td {
				padding: 8px;
				text-align: left;
			}
			
			input {
				height: 40px;
				width: 100%;
				box-sizing: border-box;
				padding: 1px;
				text-align: center;
			}	
			
			select, input:hover {
				background-color: #00fbb0;
			}
									
			select {
				height: 100%;
				width: 100%;
			}
			
			option {
				text-align: center;
			}
			
			.centered-div{
				width: 500px;
				height: 100%;
			}
		</style>	
	</head>
	<body>
		<div class="container">
		  	<div class="pull-right">
		    	<a href='option-menu' class='btn btn-info'><span class="glyphicon glyphicon-menu-left"></span> Go Back to Menu</a>
		    	<a href='logout' class='btn btn-danger'><span class="glyphicon glyphicon-log-out"></span> Logout</a>
		  	</div>
		</div>
		<br>
		
		<div class="centered-div">
		<form method='post' action='assign-employee-to-department'>
			<%-- Display the message if it exists --%>
			<%= request.getAttribute("message") %>
			<table>
				<thead>
					<tr style="background-color: #ff6347;">
						<th colspan='2'>Select An Employee And A Department</th>
					</tr>
				</thead>
				
				<tbody>
					<%-- Employee List Drop down --%>
					<tr>
						<td style='width: 30%'>
							Employee Name
						</td>
						<td>
							<select name="employeeId" required>
								<option value="disabled selected">-- Select an Employee --</option>

								<%-- Display the list of all employees --%>
								<% 	
								 	@SuppressWarnings("unchecked")
									List<Employee> employees = (List<Employee>) request.getAttribute("employees");
									for( Employee employee : employees) {
								%>
								<option value='<%=employee.getId()%>'> 
									<%=employee.getName()%> 
								</option>
								<%						
									} 
								%>
							</select>
						</td>
					</tr>
					<%-- Department List Drop down --%>
					<tr>
						<td style='width: 30%'>
							Department Name
						</td>
						<td>
							<select name='departmentId' required>
								<option value="disabled selected">-- Select a Department --</option>
						
							<%-- Fetch and display departments --%>
							<% 
								@SuppressWarnings("unchecked")
								List<Department> departments = (List<Department>) request.getAttribute("departments");
								if(departments != null) {
								for (Department dept : departments) {
							%>
									<option value="<%= dept.getId() %>">
										<%= dept.getName() %>
									</option>
							<%
								}}
							%>
							</select>
						</td>
					</tr>	
				</tbody>
				<%-- Submit Buttons --%>
				<tfoot>
					<tr>
						<th colspan='2' >
							<input type='submit' value='Assign Employee to Department'>
						</th>
					</tr>
				</tfoot>
			</table>
		</form>
		</div>
	</body>
</html>