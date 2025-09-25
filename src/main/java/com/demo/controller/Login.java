package com.demo.controller;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Servlet implementation class LoginServlet
 */
@WebServlet("/login")
public class Login extends BaseServlet {
	private static final long serialVersionUID = 1L;
	
	
	/**
	 * @see HttpServlet#doGet(
	 * 			HttpServletRequest request,
	 * 			HttpServletResponse response
	 * 		)
	 */
	protected void doGet(
			HttpServletRequest request, 
			HttpServletResponse response
			) throws ServletException, IOException 
	{
		String initialMessage = null;
			
		request.setAttribute("message", initialMessage);
		request.getRequestDispatcher("/WEB-INF/views/login.jsp")
			   .forward(request, response);
	}
	
	
	/**
	 * @see HttpServlet#doPost(
	 * 			HttpServletRequest request,
	 * 			HttpServletResponse response
	 * 		)
	 */
	protected void doPost(
				HttpServletRequest request, 
				HttpServletResponse response
			 )  throws ServletException, IOException
	{
		String usernameString = request.getParameter("username");
		String passwordString = request.getParameter("password");
			
		// TODO: Disable browser caching to avoid accessing 
		// visited pages after logout using the back button.

		if("Admin".equals(usernameString) && "123".equals(passwordString)) {
			HttpSession session = request.getSession(true);
			session.setAttribute("username", usernameString);
			
			response.sendRedirect("option-menu");	
		} else {
			String invalidLogin = "<p style='color:red; font-weight:bold;'>"
					+ "Invalid User Name or Password.</p>";
			
			request.setAttribute("message", invalidLogin);
			request.getRequestDispatcher("WEB-INF/views/login.jsp")
				   .forward(request, response);
		}
	}
}