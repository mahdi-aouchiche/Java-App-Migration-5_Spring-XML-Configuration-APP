package com.demo.controller;

import java.io.IOException;

import javax.servlet.Servlet;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.demo.service.EmployeeService;

/**
 * Servlet implementation class UpdateEmployee
 */
@WebServlet("/update-employee")
public class UpdateEmployee extends BaseServlet {
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
	 * @see HttpServlet#doPost( 
	 * 		HttpServletRequest request, 
	 * 		HttpServletResponse response )
	 */
	protected void doPost(
			HttpServletRequest request, 
			HttpServletResponse response)
			throws ServletException, IOException 
	{
		/* Check if the user is logged in to be able to have access */
		HttpSession session = request.getSession(false);

		if (session == null || session.getAttribute("username") == null) {
			String needToLogin = "<p style='color:blue; font-weight:bold;'>"
					+ "Please enter your credentials to access menu.</p>";

			request.setAttribute("message", needToLogin);
			request.getRequestDispatcher("WEB-INF/views/login.jsp")
				   .forward(request, response);
			return;
		}

		int employeeID = 0;
		String returnURL = "";

		try {
			// get from URL parameters
			employeeID = Integer.parseInt(request.getParameter("id"));
			returnURL = request.getParameter("returnURL");

			// Get employee details from updateEmplpyee.html form
			String name = request.getParameter("name");
			int age = Integer.parseInt(request.getParameter("age"));
			double salary = Double.parseDouble(request.getParameter("salary"));

			int updatedEmployees = employeeService.updateEmployee(employeeID, name, age, salary);

			if (updatedEmployees < 1) {
				System.out.println("Update employee id= " + employeeID + " unsuccessful!");
			}

			request.getRequestDispatcher(returnURL).forward(request, response);
			return;
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * @see HttpServlet#doPost(
	 * 			HttpServletRequest request, 
	 * 			HttpServletResponse response)
	 */
	protected void doGet(
			HttpServletRequest request, 
			HttpServletResponse response)
			throws ServletException, IOException 
	{
		doPost(request, response);
	}

	/**
	 * @see HttpServlet#doPost(
	 * 			HttpServletRequest request, 
	 * 			HttpServletResponse response)
	 */
	protected void doPut(
			HttpServletRequest request, 
			HttpServletResponse response)
			throws ServletException, IOException 
	{
		doPost(request, response);
	}
}