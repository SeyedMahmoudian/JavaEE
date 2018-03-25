/*****************************************************************************
Class Name: AdminDao
Authors:
Purpose:Servlet to login out from the app


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

public class LogoutServlet extends HttpServlet {  
	private static final long serialVersionUID = 1L;
	
	/*****************************************************************************
	Purpose: Process the login out
	Authors:
	Parameters: 
				@param request - HttpServletRequest
				@param response - HttpServletRespnse
	Return ValueS: void
	@throws ServletException
	@throws IOException
	 *****************************************************************************/
	@Override
	public void doPost(HttpServletRequest request, HttpServletResponse response)  
			throws ServletException, IOException { 
		
		HttpSession session=request.getSession();  
		PrintWriter out=response.getWriter();  
		System.out.println("..from LogoutServlet ..returing to login.jsp");
		RequestDispatcher rd = request.getRequestDispatcher("index.jsp");
		rd.include(request, response);  
		session.invalidate();  
		out.close();  
	}  

	
}

