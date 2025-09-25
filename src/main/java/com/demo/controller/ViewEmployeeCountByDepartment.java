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
 * Servlet implementation class ViewEmployeeCountByDepartment
 */
@WebServlet("/view-employee-count-by-department")
public class ViewEmployeeCountByDepartment extends BaseServlet {
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
		} else {
			String message = "Employee Count For Each Department";
			
			List<String> columnLabel = new ArrayList<String>();
			LinkedHashMap<Department, Integer> records = new LinkedHashMap<>();
			
			// Get column labels and employee counts by department
			this.departmentService.getEmployeeCountByDepartment(columnLabel, records);
			
			// Send data to JSP
			request.setAttribute("informationType", message);
			request.setAttribute("columnLabel", columnLabel );
			request.setAttribute("records", records );
			request.getRequestDispatcher("WEB-INF/views/view-employee-count-by-department.jsp")
				   .forward(request, response);
		}
	}
}
