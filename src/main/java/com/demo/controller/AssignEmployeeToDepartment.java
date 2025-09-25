package com.demo.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.Servlet;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.demo.entity.Department;
import com.demo.entity.Employee;
import com.demo.service.DepartmentService;
import com.demo.service.EmployeeService;

/**
 * Servlet implementation class AssignEmployeeToDepartment
 */
@WebServlet("/assign-employee-to-department")
public class AssignEmployeeToDepartment extends BaseServlet {
	private static final long serialVersionUID = 1L;
	private EmployeeService employeeService;
	private DepartmentService departmentService;
	
	
	/**
	 * @see Servlet#init(ServletConfig)
	 */
	public void init(ServletConfig config) throws ServletException {
		super.init(config);
	    this.employeeService = (EmployeeService) context.getBean("employeeService");
	    this.departmentService = (DepartmentService) context.getBean("departmentService");
	}
	
		
	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException
	{
		/* Check if the user is logged in to be able to have access*/
		HttpSession session = request.getSession(false);
		
		if (session == null || session.getAttribute("username") == null) {
			String needToLogin = "<p style='color:blue; font-weight:bold;'>"
					+ "Please enter your credentials to access menu.</p>";
			
			request.setAttribute("message", needToLogin);
			request.getRequestDispatcher("WEB-INF/views/login.jsp")
				   .forward(request, response);
			return;
		}
		
		List<Employee> employees = this.employeeService.getEmployeeList();
		List<Department> departments = this.departmentService.getDepartmentList();
		
		request.setAttribute("message", "");
		request.setAttribute("employees", employees);
		request.setAttribute("departments", departments);
		request.getRequestDispatcher(
				"WEB-INF/views/assign-existing-employee-to-department.jsp"
				).forward(request, response);
	}

	
	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		/* Check if the user is logged in to be able to have access*/
		HttpSession session = request.getSession(false);
		
		if (session == null || session.getAttribute("username") == null) {
			String needToLogin = "<p style='color:blue; font-weight:bold;'>"
					+ "Please enter your credentials to access menu.</p>";
			
			request.setAttribute("message", needToLogin);
			request.getRequestDispatcher("WEB-INF/views/login.jsp")
				   .forward(request, response);
			return;
		}
		
		int numUpdatedRecords = -1;
		String message = "";
				
		try {	
			// Retrieve the selected employee and department from the form
			int departmentId = Integer.parseInt((String)request.getParameter("departmentId"));
			int employeeId   = Integer.parseInt((String)request.getParameter("employeeId"));
						
			// Associate the employee to the department
			numUpdatedRecords = employeeService.addEmployeeToDepartment(departmentId, employeeId);
		} catch (Exception e) {
			System.err.println(e.getMessage());
		}
		
		switch (numUpdatedRecords) {
			case 0: // FAILURE: No records were updated.
				message = "<p style='color:white; font-weight:bold; text-align: center'> " + 
						  "Error!!! <br>Invalid Input.</p>";
				break;
				
			case 1: // SUCCESS: One record was updated.
				message = "<p style='color:green; font-weight:bold; text-align: center'>" + 
						  "Employee Assigned To The Department Successfully.</p>";
				break;
				
			case 2: // SUCCESS: Record exists already.
				message = "<p style='color:blue; font-weight:bold; text-align: center'>" + 
						  "Employee Is Already Assigned To This Department.</p>";
				break;
			
			default:
				message = "<p style='color:orange; font-weight:bold; text-align: center'>" + 
						  "Incomplete!!! <br>" + 
						  "Both 'Employee Name' And 'Department Name' Are Required.</p>";
				break;
		}
				
		List<Employee> employees = this.employeeService.getEmployeeList();
		List<Department> departments = this.departmentService.getDepartmentList();
		
		request.setAttribute("message", message);
		request.setAttribute("employees", employees);
		request.setAttribute("departments", departments);
		request.getRequestDispatcher(
				"WEB-INF/views/assign-existing-employee-to-department.jsp"
				).forward(request, response);
	}
}
