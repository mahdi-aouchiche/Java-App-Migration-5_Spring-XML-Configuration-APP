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
 * Servlet implementation class ViewAverageSalary
 */
@WebServlet("/view-average-salary")
public class ViewAverageSalary extends BaseServlet {
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
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
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
		
		String message = "Company Average Salary";
		
		double averageSalary = this.employeeService.getEmployeesAverageSalary();
		
		request.setAttribute("informationType", message);
		request.setAttribute("averageSalary", averageSalary );
		request.getRequestDispatcher("WEB-INF/views/view-average-salary.jsp").forward(request, response);	
	}
}
