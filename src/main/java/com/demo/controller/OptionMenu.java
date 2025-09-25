package com.demo.controller;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Servlet implementation class OptionMenu
 */
@WebServlet("/option-menu")
public class OptionMenu extends BaseServlet {
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
		HttpSession session = request.getSession(false);
		
		if(session == null || session.getAttribute("username")== null) {
			String needToLogin = "<p style='color:blue; font-weight:bold;'>"
					+ "Please enter your credentials to access menu.</p>";
			
			request.setAttribute("message", needToLogin);
			request.getRequestDispatcher("WEB-INF/views/login.jsp")
				   .forward(request, response);
			return;
		} else {
			request.getRequestDispatcher("WEB-INF/views/option-menu.jsp")
				   .forward(request, response);
		}
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
			) throws ServletException, IOException 
	{
		doGet(request, response);
	}
}
