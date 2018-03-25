/*****************************************************************************
Class Name: ReservationServlet 
Authors:
Purpose:Servlet to reserver seat


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
import com.amzi.dao.FlightBookingDao;

public class ReservationServlet extends HttpServlet {	

	private static final long serialVersionUID = 1L;
	private 	String flight_id ;
	private 	String flight_id2 ;
	

	/*****************************************************************************
	Purpose: Reserve the seat in flight
	Authors:
	Parameters: 
				@param request - HttpServletRequest
				@param response - HttpServletRespnse
	Return ValueS: void
	@throws ServletException
	@throws IOException
	 *****************************************************************************/
	@Override
	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		FlightBookingDao flightbookingDao = new FlightBookingDao();
		response.setContentType("text/html");
		PrintWriter out = response.getWriter();	
		HttpSession session = request.getSession();
		System.out.println("In seat servlet ");
		String picker = "<table  style=' margin-left:400px'>" ;
		String UserEmailAddress = session.getAttribute("username").toString();
		String decision = request.getParameter("decision");
		String decision2 = request.getParameter("decision2");
	
		if (decision == null){
			decision ="";
		}
		if(decision.equals("back")){
			RequestDispatcher rd = request.getRequestDispatcher("/CustomerServlet");
			rd.forward(request, response);
		}
		else {
		
		flight_id  =  request.getParameter("flight");
		flight_id2 = request.getParameter("flight2");
		String user_selection = request.getParameter("user_selection");	
		String seat = request.getParameter("seat");
		String seat2 = request.getParameter("seat2");
		String cardForm = request.getParameter("card-form");
		String stripe = request.getParameter("cardholder-name");
		System.out.println("Stripe" + stripe);
	
		int retValFlightSeatQuery;
		System.out.println("THE PARSED INT "+ flight_id);
		if (flight_id== null){
			 retValFlightSeatQuery = flightbookingDao.flightSeatQuery(Integer.parseInt(flight_id2));
		}
		else{
			retValFlightSeatQuery = flightbookingDao.flightSeatQuery(Integer.parseInt(flight_id));	
		}
	
		if (retValFlightSeatQuery == 0) {
			System.out.println("In FlightBookingServlet ... the seats booked on the flight are:");
			String[][] aFlightSeatList = flightbookingDao.getFlightSeatList();
			int count=0 ;
			for (int i = 0; i < 5; i++) {
				picker += "<tr>";
				for (int j = 0; j < 12; j++,count++) {
					
					if (flightbookingDao.searchSeat(count+"").equals(""))
						picker += "<td>" + "<a href='#' ><span id ='"+count+"'class='glyphicon glyphicon-ok-sign'  style='font-size: 1.9em' onClick = seatClick(document.getElementById("+count+")) ></span></a></td>";
					else 
						picker += "<td>" + "<a href='#'><span id ='"+count+"'class='glyphicon glyphicon-remove-sign' style='font-size: 1.9em ' onClick = seatClick(document.getElementById("+count+"))></span></a></td>";   	
				}
				picker += "</tr> ";
			
			}
			picker += "</table>";
			session.setAttribute("picker",picker);
		} 
		else if (retValFlightSeatQuery == -6) {
			int count =0;
			System.out.println("No seats are booked on the flight.");
			for(int i=0; i< 5; i++) {
				picker += "<tr>";
				for(int j=0; j< 12; j++,count++){
					picker += "<td>" + "<a href='#'><span id ='"+count+"'class='glyphicon glyphicon-ok-sign' style='font-size: 1.9em'  onClick = seatClick(document.getElementById("+count+")) ></span></a>" +"</td>";
				}
				picker += "</tr> ";
			}
			picker += "</table>";
			session.setAttribute("picker",picker);
		} 
		else {
			RequestDispatcher rd = request.getRequestDispatcher("seatPicker.jsp");
			rd.forward(request, response);
			System.out.println("There was an issue accessing the database.");
		}
		session.setAttribute("flight", flight_id);
		session.setAttribute("flight2", flight_id2);
		session.setAttribute("seat2", seat2);
		int retValReserveSeat =-1;
		if (seat != "" ){
			if(decision2 == null)
				decision2 ="";
			if ( decision2.equals("purchase") ){
				int retValFlightBooking = flightbookingDao.addFlightBooking(UserEmailAddress,Integer.parseInt(flight_id2), seat2.toString());
				System.out.println("In FlightBookingServlet ... the return value for addFlightBooking is: " + retValFlightBooking);		
				if (retValFlightBooking != -1) { 
					if (retValFlightBooking == 0){
						String display = "yes";
						session.setAttribute("display", display);
					}
					RequestDispatcher rd = request.getRequestDispatcher("seatPicker.jsp");
					rd.forward(request, response);
				} 
				else {
					out.print("<p style=\"color:red\">Sorry -1 or -3 </p>");
					RequestDispatcher rd = request.getRequestDispatcher("seatPicker.jsp");
					rd.forward(request, response);
				}
				out.close();
			}
			else{
				retValReserveSeat = flightbookingDao.reserveFlightSeat(Integer.parseInt(flight_id), seat , flightbookingDao.getCustId(UserEmailAddress) );
				if (retValReserveSeat != -1) { 
					RequestDispatcher rd = request.getRequestDispatcher("/CustomerServlet");
					rd.forward(request, response);
				} 
				else {
					out.print("<p style=\"color:red\">Sorry -1 or -3 </p>");
					RequestDispatcher rd = request.getRequestDispatcher("seatPicker.jsp");
					rd.forward(request, response);
				}
				out.close();
			}
		}
		else {
			RequestDispatcher rd = request.getRequestDispatcher("seatPicker.jsp");
			rd.forward(request, response);
		}	
		}
	}	


}
