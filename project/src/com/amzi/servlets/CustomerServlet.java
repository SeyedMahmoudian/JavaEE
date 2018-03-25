/*****************************************************************************
Class Name: CustomerServlet
Authors:
Purpose:Servlet for customer functions


 *****************************************************************************/
package com.amzi.servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.amzi.dao.CustomerDao;

public class CustomerServlet extends HttpServlet {
 private String [][] array; 
 private List<String> alAirportNames;
	private static final long serialVersionUID = -3022082694214328393L;

	/*****************************************************************************
	Purpose: Get user information from the db 
	Authors:
	Parameters: 
				@param request - HttpServletRequest
				@param response - HttpServletRespnse
	Return ValueS: void
	@throws ServletException
	@throws IOException
	 *****************************************************************************/
	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		CustomerDao custDao = new CustomerDao();

		int retValCustBasicInfo;
		int retValCustFlights;
		int retValAirportNames;
		String result = null;
		String dep_airportListHTML = null;
		String arr_airportListHTML = null;
		
		response.setContentType("text/html");
		PrintWriter out = response.getWriter();
		HttpSession session = request.getSession();
		custDao.setEmail(session.getAttribute("username").toString());
		retValCustBasicInfo = custDao.customerBasicInformationQuery();			
		retValCustFlights = custDao.customerBookedFlightQuery();
		retValAirportNames = custDao.airportQuery();
		

		
		if (session != null) {
			
			if (retValCustBasicInfo == 0) {
				session.setAttribute("firstname", custDao.getFirstName());
				session.setAttribute("lastname", custDao.getLastName());
				session.setAttribute("aptNumber", custDao.getAptNum());
				session.setAttribute("streetNum", custDao.getStreetNum());
				session.setAttribute("streetName", custDao.getStreetName());
				session.setAttribute("city", custDao.getCity());
				session.setAttribute("country", custDao.getCountry());
				session.setAttribute("userEmail", custDao.getEmail());
				session.setAttribute("phoneNum", custDao.getPhoneNumber());
				session.setAttribute("cardNumber", custDao.getCreditCardNum());
				session.setAttribute("expiry", custDao.getExpDate());
				session.setAttribute("cvv", custDao.getCreditCode());
				
				///////////////////////////////////////////////////////
				///// The following console prints are for debug only
				//////////////////////////////////////////////////////
				System.out.println("firstname" + custDao.getFirstName());
				System.out.println("lastname" + custDao.getLastName());
				System.out.println("aptNumber" + custDao.getAptNum());
				System.out.println("streetNum" + custDao.getStreetNum());
				System.out.println("streetName" + custDao.getStreetName());
				System.out.println("city" + custDao.getCity());
				System.out.println("country" + custDao.getCountry());
				System.out.println("userEmail" + custDao.getEmail());
				System.out.println("phoneNum" + custDao.getPhoneNumber());
				System.out.println("cardNumber" + custDao.getCreditCardNum());
				System.out.println("expiry" + custDao.getExpDate());
				System.out.println("cvv" + custDao.getCreditCode());	
				

				if (retValAirportNames == 0) {
					this.alAirportNames = custDao.getAirportNames();
					
					///////////////////////////////////////////////////////
					//// The following console dump code is for debug only
					///////////////////////////////////////////////////////
					
					/*System.out.println("There are " + alAirportNames.size() + " airports in the database.  They are:");
					for (int i =0; i < alAirportNames.size(); i++) {
				         System.out.printf("%s\n",alAirportNames.get(i).toUpperCase());  // Debug dump to the console
					}
			        */
			       
			        dep_airportListHTML = "<select id='departure-selection' name='departure-selection' class='form-group' >"; 
			        for (int j = 0; j < alAirportNames.size(); j++){
			        	dep_airportListHTML += "<option>" + alAirportNames.get(j).toUpperCase() + "</option>";
			        }
			        dep_airportListHTML += "</select>";
			        
			        arr_airportListHTML = "<select id='arrival-selection' name='arrival-selection' class='form-group' >";  
			        for (int j = 0; j < alAirportNames.size(); j++){
			        	arr_airportListHTML += "<option>" + alAirportNames.get(j).toUpperCase() + "</option>";
			        }
			        arr_airportListHTML += "</select>";
	
			        
			        
				} else if (retValAirportNames == -6) {
					result = "No airports available.";
					
				} else {
					System.out.println("In CustomerServlet ... error code returned from airportQuery is: " + retValCustFlights);
				    result = "Database access error when trying to retrieve available airports.  Please try again.";
			    }
				
				if (retValCustFlights == 0) {
					this.array= custDao.getData(); 
					result = "<table border=1 id='flight_table' >";
					System.out.println("Client's Flightbooking history is:");
				    for(int i=0; i< array.length; i++) {
				        result += "<tr>";
				        for(int j=0; j< array[i].length; j++){
				         result += "<td>" + array[i][j] +"</td>";
				         System.out.printf("%s",array[i][j]); 
				        }
				        result += "</tr>";
				        System.out.println(""); 
				    }
				    result += "</table>";

				} else if (retValCustFlights == -6) {
					result = "No flight history available.";
				} else {
			        System.out.println("In CustomerServlet ... error code returned from customerBookedFlightQuery is: " + retValCustFlights); 
			        result = "Database access error when trying to retrieve flight information.  Please try again.";
				}
				session.setAttribute("airport-departure-dropdown", dep_airportListHTML);
				session.setAttribute("airport-arrival-dropdown", arr_airportListHTML);
				session.setAttribute("data", result);	
			    System.out.println(result);
			    
				System.out.println("In CustomerServlet ... next call Loading userWelcom.jsp");
				RequestDispatcher rd = request.getRequestDispatcher("userWelcome.jsp");
				rd.include(request, response);

			} else {
				out.print("<p style=\"color:red\">ERROR - Client Query</p>");
		        System.out.println("In CustomerServlet ... error code returned from customerBasicInformationQuery is: " + retValCustBasicInfo);
				RequestDispatcher rd = request.getRequestDispatcher("index.jsp");
				rd.include(request, response);
			}
	

		}
		
		out.close();
	}
	
	
	/*****************************************************************************
	Purpose: Get user information from the db 
	Authors:
	Parameters: 
				@param request - HttpServletRequest
				@param response - HttpServletRespnse
	Return ValueS: void
	@throws ServletException
	@throws IOException
	 *****************************************************************************/
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		int retVal, retVal2;
		response.setContentType("text/html");
		//PrintWriter out = response.getWriter();

		CustomerDao db = new CustomerDao();
		HttpSession session = request.getSession();
		db.setEmail(session.getAttribute("username").toString());
		retVal = db.customerBasicInformationQuery();
		retVal2 = db.customerBookedFlightQuery();
		db.setEmail(session.getAttribute("username").toString());
		if (session != null) {
			session.setAttribute("firstname", db.getFirstName());
			session.setAttribute("lastname", db.getLastName());
			session.setAttribute("aptNumber", db.getAptNum());
			session.setAttribute("streetNum", db.getStreetNum());
			session.setAttribute("city", db.getCity());
			session.setAttribute("phoneNum", db.getPhoneNumber());
			session.setAttribute("cardNumber", db.getCreditCardNum());
			session.setAttribute("expiry", db.getExpDate());
			session.setAttribute("cvv", db.getCreditCode());
			session.setAttribute("departTime", db.getDepartTime());
			session.setAttribute("flightDate", db.getFlightDate());
			session.setAttribute("seatCost", db.getSeatCost());
			session.setAttribute("departAirport", db.getDepartAirport());
			session.setAttribute("arrivalAirport", db.getArrivalAirport());
			session.setAttribute("duration", db.getDuration());
			session.setAttribute("planeType", db.getPlaneType());
		}
		
		if (retVal == 0 || retVal2 == 0) {
			RequestDispatcher rd = request.getRequestDispatcher("userWelcome.jsp");
			rd.forward(request, response);
		} else {
			RequestDispatcher rd = request.getRequestDispatcher("index.jsp");
			rd.forward(request, response);
		}

	}
}