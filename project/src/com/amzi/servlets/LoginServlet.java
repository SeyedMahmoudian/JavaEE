/*****************************************************************************
Class Name: AdminDao
Authors:
Purpose:Servlet to login into the app


 *****************************************************************************/
package com.amzi.servlets;
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import com.amzi.dao.LoginDao;


public class LoginServlet extends HttpServlet {

	private static final long serialVersionUID = -5465193580807654321L;

	
	/*****************************************************************************
	Purpose: Process the login 
	Authors:
	Parameters: 
				@param request - HttpServletRequest
				@param response - HttpServletRespnse
	Return ValueS: void
	@throws ServletException
	@throws IOException
	 *****************************************************************************/
	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		LoginDao loginDao = new LoginDao();
		
		response.setContentType("text/html");
		PrintWriter out = response.getWriter();

		String n = request.getParameter("username");
		String p = request.getParameter("password");

		String signInButton = request.getParameter("sign_in");
		String subscibeButton = request.getParameter("subscribe");
		
		System.out.println("in LoginServlet ... username: " + n);
		System.out.println("in LoginServlet ... signInButton: " + signInButton + "subscibeButton" + subscibeButton);
		
		if (signInButton != null) {
			HttpSession session = request.getSession(false);
			if (session != null)
				session.setAttribute("username", n);
			// found status;
			int found = -1;
			found = loginDao.validate(n, p);
			System.out.println("Back from validate with found = :" + found);
			if (found == 1) {

				RequestDispatcher rd = request.getRequestDispatcher("/CustomerServlet");

				rd.forward(request, response);
			} else if (found == 0) {
				RequestDispatcher rd = request.getRequestDispatcher("AWelcome.jsp");
				rd.forward(request, response);
			} else {
				out.print("<p style=\"color:red\">Sorry username or password error</p>");
				RequestDispatcher rd = request.getRequestDispatcher("index.jsp");
				rd.include(request, response);
			}
			
		} else if (subscibeButton != null) {
			RequestDispatcher rd = request.getRequestDispatcher("subscribe.jsp");
			rd.forward(request, response);
		}

		out.close();
	}

}