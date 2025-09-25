package com.demo.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
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
import com.demo.service.DepartmentService;

/**
 * Servlet implementation class ViewDepartmentsWithAtLeastANumberOfEmployees
 */
@WebServlet("/view-departments-with-at-least-a-number-of-employees")
public class ViewDepartmentsWithAtLeastANumberOfEmployees extends BaseServlet {
	private static final long serialVersionUID = 1L;
	private DepartmentService departmentService;
	
	
	/**
	 * @see Servlet#init(ServletConfig)
	 */
	public void init(ServletConfig config) throws ServletException {
		super.init(config);
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
		
		String message = "Enter The Least Number Of Employees Per Department";
		request.setAttribute("message", message);
		request.getRequestDispatcher("WEB-INF/views/get-number-of-employees.jsp")
			   .forward(request, response);
	}

	
	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
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
		
		long numEmployees = 0;
		String message = "";
		List<String> columnLabel = new ArrayList<String>();
		LinkedHashMap<Department, Integer> records = new LinkedHashMap<>();
		
		/* Get the least number of employees */
		try {
			numEmployees = Long.parseLong(request.getParameter("numEmployees"));
		} catch (Exception e) {
			/* Ask user for correct input*/
			message = "<p style='color:orange; font-weight:bold; text-align:center;'>" + 
				 			"Please Enter A Valid Minimum Number Of Employees" +
				 	  "</p>";
			request.setAttribute("message", message);	
			request.getRequestDispatcher(
					"WEB-INF/views/get-number-of-employees.jsp"
					).forward(request, response);
			
			// redirected to get a valid input
			return;
		}
		
		if(numEmployees == 0) {
			message = "Employee Count By Department";
		} else if(numEmployees == 1) {
			message = "Departments With At Least " + numEmployees + " Employee";
		} else {
			message = "Departments With At Least " + numEmployees + " Employees";
		}
		
		/* Send info to service layer */
		this.departmentService.departmentsWithAtLeastACertainNumberOfEmployees(
				numEmployees, columnLabel, records);
		
		/* Dispatch to view-jsp*/
		request.setAttribute("informationType", message);
		request.setAttribute("departmentList", records);
		request.setAttribute("columnLabel", columnLabel);
		request.getRequestDispatcher(
				"WEB-INF/views/view-departments-by-number-of-employees.jsp")
			   .forward(request, response);
	}
}
