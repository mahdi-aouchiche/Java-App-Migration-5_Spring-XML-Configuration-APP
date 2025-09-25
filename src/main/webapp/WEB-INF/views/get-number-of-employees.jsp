<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
	<head>
		<meta charset="UTF-8">
		<title>Get Minimum Number Of Employee</title>
		
		<link rel='stylesheet' href='https://maxcdn.bootstrapcdn.com/bootstrap/3.4.1/css/bootstrap.min.css'>
		<script 
			src='https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js' type="text/javascript">
		</script>
		<script 
			src='https://maxcdn.bootstrapcdn.com/bootstrap/3.4.1/js/bootstrap.min.js' type="text/javascript">
		</script>
		
		<style>
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
				height: 50%;
			}
			
			tr {
				height: 40px;	
			}
			
			th{
				text-align : center;
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
								
			input:hover {
				background-color: #00fbb0;
			}
			
			thead tr {
				background-color: #ff6347;
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
		<form method='post' action='view-departments-with-at-least-a-number-of-employees'>
			<table>
				<thead>
					<tr>
						<th>
							<%=request.getAttribute("message").toString()%>
						</th>
					</tr>
				</thead>
				<tbody>
					<tr>
						<td>
							<input type='number' name='numEmployees' placeholder='Number of Employees' min='0' required>
						</td>
					</tr>
				</tbody>
				<tfoot>
					<tr>
						<th>
							<input type='submit' value='Enter' style='width: 100%;' min='0' required>
						</th>
					</tr>
				</tfoot>
			</table>
		</form>
		</div>
	</body>
</html>