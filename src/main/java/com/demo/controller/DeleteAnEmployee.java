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

import com.demo.entity.Employee;
import com.demo.service.EmployeeService;

/**
 * Servlet implementation class DeleteAnEmployee
 */
@WebServlet("/delete-an-employee")
public class DeleteAnEmployee extends BaseServlet {
	private static final long serialVersionUID = 1L;
	private EmployeeService employeeService;
	
	
	/**
	 * @see Servlet#init(ServletConfig)
	 */
	public void init(ServletConfig config) throws ServletException {
		super.init(config);
	    this.employeeService = (EmployeeService) context.getBean("employeeService");
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
		
		String returnURL = request.getParameter("returnURL");
		if(returnURL != null) {	
			// The delete request is from view-edit-employees.jsp has returnURL	
			try {
				int employeeId = Integer.parseInt((String) request.getParameter("employeeId"));
				
				// Delete the employee
				if(this.employeeService.deleteEmployee(employeeId)) {
					System.out.println("Delete Employee ID = " + employeeId + " Successful!");	
				} else {
					System.out.println("Delete employee id= " + employeeId + " Unsuccessful!");
				}
			} catch (Exception e) {
				System.out.println("Error Occured Deleting an Employee.");
				e.printStackTrace();
			}
			
			request.getRequestDispatcher(returnURL).forward(request, response);
		} else {	
			// The delete request is from option-menu.jsp send to doPost
			
			List<Employee> employees = this.employeeService.getEmployeeList();
			request.setAttribute("message", "");
			request.setAttribute("employees", employees);
			
			request.getRequestDispatcher("WEB-INF/views/delete-an-employee.jsp")
				   .forward(request, response);
		}
	}

	
	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, 
			HttpServletResponse response) 
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
		
		String message = "";
		Boolean isDeleted = false;
		
		try {
			// retrieve the selected employee-id to delete
			int employeeId = Integer.parseInt((String) request.getParameter("employeeId"));
			
			// Delete the employee
			isDeleted = this.employeeService.deleteEmployee(employeeId);
			
			if(isDeleted) {
				System.out.println("Delete Employee ID = " + employeeId + " Successful!");
				message= "<p style='color:green; font-weight:bold; text-align: center'>" + 
						  "Employee Deleted Successfully.</p>";
			} else {
				System.out.println("Delete employee id= " + employeeId + " Unsuccessful!");
				message = "<p style='color:orange; font-weight:bold; text-align: center'>" + 
						  "Employee Not Deleted.</p>";
			}
			
		} catch (NumberFormatException e) {
			message = "<p style='color:blue; font-weight:bold; text-align: center'>" + 
					  		"Incomplete!!! <br> 'Employee Name' is Required.</p>";
			
		} catch (Exception e) {
			System.out.println("Error Occured Deleting an Employee.");
			e.printStackTrace();
		}
			
		List<Employee> employees = this.employeeService.getEmployeeList();
		request.setAttribute("message", message);
		request.setAttribute("employees", employees);
		request.getRequestDispatcher("WEB-INF/views/delete-an-employee.jsp")
			   .forward(request, response);
	}
}
