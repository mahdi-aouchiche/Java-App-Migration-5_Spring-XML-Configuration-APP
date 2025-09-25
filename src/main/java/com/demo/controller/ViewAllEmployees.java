package com.demo.controller;

import java.io.IOException;
import java.util.ArrayList;
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
 * Servlet implementation class ViewAllEmployees
 */
@WebServlet("/view-all-employees")
public class ViewAllEmployees extends BaseServlet 
{
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
	* @see HttpServlet#doGet(
	* 	HttpServletRequest request, 
	* 	HttpServletResponse response
	* )
	*/
	protected void doGet(	HttpServletRequest request,
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
			
		/* Get all employees */
		String message = "Employees Information";
		List<Employee> allEmployees = new ArrayList<Employee>();
		List<String> columnLabels = new ArrayList<String>();
		this.employeeService.listOfAllEmployees(columnLabels, allEmployees);
					
		request.setAttribute("tableHeader", columnLabels);
		request.setAttribute("employeeList", allEmployees);
		request.setAttribute("returnURL", request.getServletPath());
		request.setAttribute("informationType", message);
		request.getRequestDispatcher("WEB-INF/views/view-edit-employees.jsp")
			   .forward(request, response);			
	}

	
	/**
	 * @see HttpServlet#doPost(
	 * 	HttpServletRequest request, 
	 * 	HttpServletResponse response
	 * )
	 */
	protected void doPost(	HttpServletRequest request,
							HttpServletResponse response)
		throws ServletException, IOException
	{
		doGet(request, response);
	}
}
