/*****************************************************************************
Class Name: UnReservationServlet 
Authors:
Purpose:Servlet to unreserver seat


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

public class UnReserveServlet extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/*****************************************************************************
	Purpose: Un-Reserve the seat in flight
	Authors:
	Parameters: 
				@param request - HttpServletRequest
				@param response - HttpServletRespnse
	Return ValueS: 
				retVal - Integer: 0 -> seat successfully booked
				              	 -3 -> unable to find customer in the database
				                 -7 -> seat failed to be booked because it is already taken
				                 -1 -> database access issue (which caused seat booking failure)
	@throws ServletException
	@throws IOException
	 *****************************************************************************/
	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		
		FlightBookingDao flightDao = new FlightBookingDao();
		response.setContentType("text/html");
		PrintWriter out = response.getWriter();
	
		HttpSession session = request.getSession();	

		int retValUnReserveBooking = -1;
		int retValPurchaseBooking = -1;
		String purchaseButton = request.getParameter("purchase_booking");
		String releaseBookingButton = request.getParameter("release_booking");
		
		
		String flight_inner = request.getParameter("flight_inner").toString();
        String seat_inner = request.getParameter("seat_inner").toString();
        String userEmail = session.getAttribute("username").toString();
        int CustID = flightDao.getCustId(userEmail);	
	
		System.out.println("in UnReserveServlet ... purchaseButton: " + purchaseButton + " releaseBookingButton: " + releaseBookingButton);

		if (session != null){
		
			session.setAttribute("flight_inner", flight_inner);
			session.setAttribute("seat_inner", seat_inner);
			
			int flight_inner_int = Integer.parseInt(flight_inner);
			
			if (releaseBookingButton != null) {

				retValUnReserveBooking = flightDao.unReserveFlightSeat(flight_inner_int, seat_inner, CustID);
				System.out.println("retValUnReserveBooking " +  retValUnReserveBooking);
				if (retValUnReserveBooking == 0) {
					System.out.println("SUCCESS -> UNRESERVED BOOKING WITH flightid, seat#, custid : " + seat_inner + " , " + flight_inner  + " , " + CustID);
					RequestDispatcher rd = request.getRequestDispatcher("/CustomerServlet");
					rd.forward(request, response);
				}
				else{ // ERROR WITH UNRESERVING
					System.out.println("FAILURE -> SEAT NOT UNRSERVED");
					RequestDispatcher rd = request.getRequestDispatcher("/CustomerServlet");
					rd.forward(request, response);

				}

			} else if (purchaseButton != null) {

				retValPurchaseBooking = flightDao.unReserveFlightSeat(flight_inner_int, seat_inner, CustID);
				retValPurchaseBooking = flightDao.addFlightBooking(userEmail, flight_inner_int, seat_inner);
				System.out.println("retValPurchaseBooking" +  retValPurchaseBooking);
				
				if (retValPurchaseBooking  == 0) {
					System.out.println("SUCCESS -> Purchase BOOKING WITH flightid, seat#, custid : " + flight_inner  + " , " + seat_inner  + " , " + CustID);
					RequestDispatcher rd = request.getRequestDispatcher("/CustomerServlet");
					rd.forward(request, response);
				}
				else{
					System.out.println("FAILURE -> SEAT NOT PURCHASED");
					RequestDispatcher rd = request.getRequestDispatcher("/CustomerServlet");
					rd.forward(request, response);
				}
			}
		}

		out.close();

	}

}