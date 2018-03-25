/*****************************************************************************
Class Name: FlightBookingServlet
Authors:
Purpose:Servlet for booking flights


 *****************************************************************************/
package com.amzi.servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.apache.commons.lang3.ArrayUtils;

import com.amzi.dao.FlightBookingDao;

public class FlightBookingServlet extends HttpServlet {
	ArrayList<Integer> takenSeats  = new ArrayList<Integer>();
	private static final long serialVersionUID = -3022082694214328393L;

	/*****************************************************************************
	Purpose: send seats from db to jsp
	Authors:
	Parameters: 
				@param request - HttpServletRequest
				@param response - HttpServletRespnse
	Return ValueS: void
	@throws ServletException
	@throws IOException
	 *****************************************************************************/

	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		FlightBookingDao flightbookingDao = new FlightBookingDao();
		response.setContentType("text/html");
		PrintWriter out = response.getWriter();	
		HttpSession session = request.getSession();
		System.out.println("In FlightBookingServlet");
		String UserEmailAddress = session.getAttribute("username").toString();

		String departureAiport = request.getParameter("departure-selection");
		String depDate = request.getParameter("departure-date");
		String arrivalAirport = request.getParameter("arrival-selection");
		String arrDate = request.getParameter("arrival-date");
		String picker="<table  style=' margin-left:400px'>" ;

		String testDepartureAirport = "YOW";
		String testArrivalAirport = "YUL";
		String testDepDate = "2017-05-01";

		System.out.print("Generate SEAT PICKER");

		
		int returnValue= flightbookingDao.flightListQuery(testDepartureAirport, testArrivalAirport, testDepDate) ;
		
		if ( returnValue == 0){

			for(int i=0; i< 5; i++) {
				picker += "<tr>";
				for(int j=0; j< 5; j++){
					picker += "<td>" + "<a href='#'><span class='glyphicon glyphicon-ok-sign'></span></a>" +"</td>";
				}
				picker += "</tr> ";
			}
			picker += "</table>";
			session.setAttribute("picker",picker);
		}
		else {
			
			 takenSeats.add(2) ;
			 takenSeats.add(5) ;
			 takenSeats.add(9) ;
			 takenSeats.add(17) ;
			
			int count =0;
			for(int i=0; i< 5; i++){
				picker += "<tr>";
				for(int j=0; j< 5; j++,count++){
					if (takenSeats.contains(count))
						picker += "<td>" + "<a href='#'><span id ='"+count+"'class='glyphicon glyphicon-remove-sign' style='font-size: 1.9em ' onClick=seatClick(document.getElementById("+count+"))></span></a></td>";
					else 
					    picker += "<td>" + "<a href='#' ><span id ='"+count+"'class='glyphicon glyphicon-ok-sign'  style='font-size: 1.9em' onClick=seatClick(document.getElementById("+count+"))></span></a></td>";
								
				}
				picker += "</tr> ";
			}
			picker += "</table>  ";
			session.setAttribute("picker", picker);
		}


		int retValFlightQuery = flightbookingDao.flightListQuery(testDepartureAirport, testArrivalAirport, testDepDate);
		System.out.println("In FlightBookingServlet ... the return value for flightListQuery with test values is: " + retValFlightQuery);
		if (retValFlightQuery == 0) {

			String[][] aFlightList = flightbookingDao.getFlightList();
			System.out.println("In FlightBookingServlet ... there are " + (aFlightList.length - 1)
					+ " flights that match the test value criteria in the database.  They are:");
			for (int i = 0; i < aFlightList.length; i++) {
				for (int j = 0; j < aFlightList[i].length; j++) {
					System.out.printf("%s ", aFlightList[i][j]);

				}
				System.out.println("");
			}

		} else if (retValFlightQuery == -6) {
			System.out.println("No flights in the database match the test value criteria");

		} else {
			System.out.println("There was an issue accessing the database.");
		}
		int testFlightID = 9;
		int retValFlightSeatQuery = flightbookingDao.flightSeatQuery(testFlightID );
		System.out.println("In FlightBookingServlet ... the return value for flightSeatQuery is: " + retValFlightSeatQuery);
		if (retValFlightSeatQuery == 0) {
			System.out.println("In FlightBookingServlet ... the seats booked on the flight are:");
			String[][] aFlightSeatList = flightbookingDao.getFlightSeatList();
			
			for (int i = 0; i < aFlightSeatList.length; i++) {
				for (int j = 0; j < aFlightSeatList[i].length; j++) {
					System.out.printf("%s ", aFlightSeatList[i][j]); 

				}
				System.out.println("");
			}

		} else if (retValFlightSeatQuery == -6) {
			System.out.println("No seats are booked on the flight.");

		} else {
			System.out.println("There was an issue accessing the database.");
		}

		testFlightID = 9;
		String testFlightSeatNum = "3"; 
		int retValFlightBooking = flightbookingDao.addFlightBooking(UserEmailAddress, testFlightID, testFlightSeatNum);
		System.out.println("In FlightBookingServlet ... the return value for addFlightBooking is: " + retValFlightBooking);		

		int tCustID = 1;
		int tFlightID = 10;
		String tSeatNum = "23";

		
		int retValReserveSeat = flightbookingDao.reserveFlightSeat(tFlightID, tSeatNum , tCustID );
		System.out.println("In FlightBookingServlet ... the return value for reserveFlightSeat is: " + retValReserveSeat);	
		
		int retValunReserveSeat = flightbookingDao.unReserveFlightSeat(tFlightID, tSeatNum , tCustID );
		System.out.println("In FlightBookingServlet ... the return value for unReserveFlightSeat is: " + retValunReserveSeat);		
		
		
        double testFlightSeatCost = 78.22;
		if (session != null){
			session.setAttribute("seat", testFlightSeatNum); 
			session.setAttribute("flightSeatPrice", testFlightSeatCost);
			session.setAttribute("departureAirport", departureAiport);
			session.setAttribute("depDate", depDate.concat(":00"));
			session.setAttribute("arrivalAirport", arrivalAirport);
			session.setAttribute("arrDate", arrDate.concat(":00"));

		}
		int success = -1;
		System.out.println("USER FLIGHT SELECTIONS : " + departureAiport + " " +  depDate + " " +  arrivalAirport + " " +   arrDate);
		System.out.println();

		if (success == -1) {
			RequestDispatcher rd = request.getRequestDispatcher("seatPicker.jsp");
			rd.forward(request, response);
		} 
		else {
			out.print("<p style=\"color:red\">Sorry -1 or -3 </p>");
			RequestDispatcher rd = request.getRequestDispatcher("userWelcome.jsp");
			rd.forward(request, response);
		}
		out.close();
	}
	
	


	


}