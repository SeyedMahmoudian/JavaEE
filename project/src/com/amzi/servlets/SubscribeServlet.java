/*****************************************************************************
Class Name: SubscribeServlet
Authors:
Purpose:Subscribtion servlet

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

import com.amzi.dao.CreditCardDao;
import com.amzi.dao.SubscribeDao;

public class SubscribeServlet extends HttpServlet {

	private static final long serialVersionUID = 68495426871256580L;


	/*****************************************************************************
	Purpose: Allow user to register by getting information from the jsp and send it to dao
	Authors:
	Parameters: 
  				@param request HttpServletRequest
  				@param response HttpServletResponse
	Return ValueS: void
 	@exception IOException
 	@exception ServletException

	 ****************************************************************************/
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		response.setContentType("text/html");
		PrintWriter out = response.getWriter();
		//Date date = null;
		
		String firstName = request.getParameter("Firstname");
		String lastName = request.getParameter("LastName");
		String password = request.getParameter("customerpass");
		String email = request.getParameter("customeremai");
		String phoneNumber = request.getParameter("phoneNumber");
		String streetNum = request.getParameter("StreetNum");
		String streetName = request.getParameter("StreetName");
		String city = request.getParameter("city");
		String country = request.getParameter("country");
		String postCode = request.getParameter("postalcode");
		String aptNum = request.getParameter("AptNum");
	
		String creditName = request.getParameter("creditName");
		String creditCode = request.getParameter("creditCode");
		byte code = Byte.parseByte(creditCode);
		String expDate = request.getParameter("date");
		String creditNum=request.getParameter("creditNum");
		
		
		HttpSession session = request.getSession();
		
		if(session!=null){
			session.setAttribute("firstName", firstName);
			session.setAttribute("lastName", lastName);
			session.setAttribute("password",password);
			session.setAttribute("email", email);
			session.setAttribute("phoneNumber", phoneNumber);
			session.setAttribute("streetNum", streetNum);
			session.setAttribute("streetName", streetName);
			session.setAttribute("city", city);
			session.setAttribute("country", country);
			session.setAttribute("postCode", postCode);
			session.setAttribute("AptNum", aptNum);
			session.setAttribute("creditName", creditName);
			session.setAttribute("creditCode", code);
			session.setAttribute("date", expDate);
			session.setAttribute("creditNum", creditNum);
		
		}
		int registered=-1;
		SubscribeDao registration = new SubscribeDao(firstName,lastName,password,email,phoneNumber,streetNum,streetName,city,
													country,aptNum,postCode);
		registered=registration.registerUser();
		
		
		if(registered==1){
			int creditRegister=-1;
			CreditCardDao credit = new CreditCardDao(creditNum, expDate, creditName, code, email);
			credit.addCreditCard();
			
			if(creditRegister==1){
				out.println("you have successfully registered...");
			}else{
				registered=-1;
				
			}
		}else{
			out.print("<p style=\"color:red\">Somethign went wrong try again</p>");
			RequestDispatcher rd = request.getRequestDispatcher("index.jsp");
			rd.include(request, response);
		}	
	}

}
