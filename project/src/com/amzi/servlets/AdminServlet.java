/*****************************************************************************
Class Name: AdminServlet
Authors:
Purpose:Servlet for admin functions


 *****************************************************************************/

package com.amzi.servlets;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import com.amzi.dao.AdminDao;;

public class AdminServlet extends HttpServlet {

	
	private static final long serialVersionUID = -5665193580805293080L;
	
	
	/*****************************************************************************
	Purpose: Get user information from the db base on email address
	Authors:
	Parameters: 
				@param request - HttpServletRequest
				@param response - HttpServletRespnse
	Return ValueS: void
	@throws ServletException
	@throws IOException
	 *****************************************************************************/
	
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
		
		int retVal,retVal2 = 0;
		response.setContentType("text/html");
		PrintWriter out = response.getWriter();
		List<String> list = new ArrayList<>();
		AdminDao db = new AdminDao();
		list=db.getList();
		HttpSession session = request.getSession();
		db.setEmail(request.getParameter("username"));
		retVal=db.queryByUserEmail();
		
		if(session!=null && retVal == 0){
			for(int i =0 ;i<list.size();i++){
				
			}
			if(retVal2==0){
				session.setAttribute("departTime", list.get(15));
				session.setAttribute("flightDate", list.get(16));
				session.setAttribute("seatCost", list.get(17));
				session.setAttribute("departAirport", list.get(18));
				session.setAttribute("arrivalAirport", list.get(19));
				session.setAttribute("duration", list.get(20));
				session.setAttribute("planeType", list.get(21));
			}
		
		}
		if (retVal==0 || retVal2==0) {

			RequestDispatcher rd = request.getRequestDispatcher("AWelcome.jsp");
			rd.forward(request, response);
		}else{
			out.print("<p style=\"color:red\">Something went wrong in admin servlet...refer to Console for better debuggin information</p>");
			RequestDispatcher rd = request.getRequestDispatcher("/index.jsp");
			rd.forward(request, response);
		}
	}
	
	/*****************************************************************************
	 Purpose: add flights to database 
	 Author:
	 Parameters:
	  			@param request
	  			@param response
	  Return Value: void
	  @throws ServletException
	  @throws IOException
	 *****************************************************************************/
	@Override
	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
		response.setContentType("text/html");
		PrintWriter out = response.getWriter();
		HttpSession session = request.getSession();
		byte aD_planeID = 1;
		String aD_flightDate = request.getParameter("flightDate");
		String aD_flightDepartTime = request.getParameter("flightDepartureTime");
		String aD_flightDuration_S = request.getParameter("flightDuration");
		int aD_flightDuration = Integer.parseInt(aD_flightDuration_S.toString());
		String aD_flightArrivalAirport = request.getParameter("flightArrivalAirport");
		String aD_flightDepartAirport = request.getParameter("flightDepartAirport");
		String flightSeatCost=request.getParameter("flightSeatCost").toString();
		double aD_flightSeatCost = Double.parseDouble(flightSeatCost);
		String table = "<div>TEST</div>";
		
			
			session.setAttribute("planeId", aD_planeID);
			session.setAttribute("flightDate", aD_flightDate);
			session.setAttribute("flightDepart", aD_flightDepartTime);
			session.setAttribute("flightDuration", aD_flightDuration);
			session.setAttribute("flightArrival", aD_flightArrivalAirport);
			session.setAttribute("flightDepartAirport", aD_flightDepartAirport);
			session.setAttribute("flightSeatCost", aD_flightSeatCost);
			session.setAttribute("table", table);
	
		
		
		AdminDao aDao = new AdminDao(aD_planeID, aD_flightDate, aD_flightDepartTime, aD_flightDuration, aD_flightArrivalAirport,
				aD_flightDepartAirport, aD_flightSeatCost);
		
		
		System.out.println("In AdminServlet ... about to call registrFlight");
		session.setAttribute("email", aDao.getEmail());
		int registered = -1;
	     int flightDurationInt = Integer.parseInt(flightSeatCost);
	     double seatCost= Double.parseDouble(flightSeatCost);
		AdminDao registration = new AdminDao(aD_planeID,aD_flightDate,aD_flightDepartTime,flightDurationInt,
				aD_flightArrivalAirport,aD_flightDepartAirport,seatCost);
		
		registered=registration.registerFlight();
			
		if(registered==0){
		    System.out.println("Flight added ");
		    RequestDispatcher rd =request.getRequestDispatcher("AWelcome.jsp");
		     rd.forward(request, response);
		}else{
			registered=-1;
			System.out.println("<p style=\"color:red\">Somethign went wrong when trying to add new flight ...... refer to Console for better debuggin information</p>");
			RequestDispatcher rd =request.getRequestDispatcher("index.jsp");
			rd.forward(request, response);
		}
		
		out.close();
		
	}
	

}