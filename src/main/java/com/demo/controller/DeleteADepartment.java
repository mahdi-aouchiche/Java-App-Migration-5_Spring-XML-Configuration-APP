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
import com.demo.service.DepartmentService;

/**
 * Servlet implementation class DeleteADepartment
 */
@WebServlet("/delete-a-department")
public class DeleteADepartment extends BaseServlet {
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
		
		List<Department> departments = this.departmentService.getDepartmentList();
		
		request.setAttribute("message", "");
		request.setAttribute("departments", departments);
		
		request.getRequestDispatcher("WEB-INF/views/delete-a-department.jsp")
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
		
		String message = "";
		
		try {
			// retrieve the selected department-id to delete
			int departmentId = Integer.parseInt((String) request.getParameter("departmentId"));
			
			// Delete the department
			if( this.departmentService.deleteDepartment(departmentId)) {
				message= "<p style='color:green; font-weight:bold; text-align: center'>" + 
						  "Department Deleted Successfully.</p>";
			} else {
				message = "<p style='color:orange; font-weight:bold; text-align: center'>" + 
						  "Department Not Deleted.</p>";
			}
		} catch (NumberFormatException e) {
			message = "<p style='color:blue; font-weight:bold; text-align: center'>" + 
					  		"Incomplete!!! <br> 'Department Name' is Required.</p>";
		} catch (Exception e) {
			System.out.println("Error Occured Deleting a Department.");
			e.printStackTrace();
		}
		
	
		List<Department> departments = this.departmentService.getDepartmentList();
		request.setAttribute("message", message);
		request.setAttribute("departments", departments);
		request.getRequestDispatcher("WEB-INF/views/delete-a-department.jsp")
			   .forward(request, response);
	}
}