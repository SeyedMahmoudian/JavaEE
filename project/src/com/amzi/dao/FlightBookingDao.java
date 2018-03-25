/*****************************************************************************
Class Name: FlightBookinDao
Authors:
Purpose:Database Access Object to book the flights


 *****************************************************************************/
package com.amzi.dao;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;
import misc.SupportMethods;

public class FlightBookingDao {
	private Master master = new Master();
	private Connection conn = master.makeConnection();
	private String[][] aFlightList  = null;
	private String[][] aBookedSeats  = null;
	private int customerId  ;
	private String[][] resList = null;
	/*****************************************************************************
	Purpose: This method creates a two dimensional array of flights (and associated data fields).  
	   		   Column 0: Flight_Departure_Airport
			   Column 1: Flight_Arrival_Airport
			   Column 2: Flight_Date
			   Column 3: Flight_Depart_Time
			   Column 4: Flight_Duration
			   Column 5: Flight_Seat_Cost
			   Column 6: Plane_Type
			   Column 7: Flight_ID
	Authors:
	Parameters: 
	  		@param DepartureAirport - String
	  		@param ArrivalAirport -  String
	  		@param FlightDate - String
	Return ValueS: 
			 		0-> at least one record found
				   -6-> no record was found
				   -1-> db issue
			@param retVal

	 ****************************************************************************/
	
	public int flightListQuery(String DepartureAirport,String ArrivalAirport, String FlightDate) {
		int retVal = -1;
		
		int numOfColumns = 8; 
		PreparedStatement pst = null;
		ResultSet rs = null;
		
		try {
			
		System.out.println("In FlightBookingDao flightListQuery ... with following supplied values:");
		System.out.println("DepartureAirport: " + DepartureAirport);
		System.out.println("ArrivalAirport: " + ArrivalAirport);
		System.out.println("FlightDate: " + FlightDate);
		
		pst = conn.prepareStatement(
				"SELECT flight.Flight_Departure_Airport, flight.Flight_Arrival_Airport, flight.Flight_Date, flight.Flight_Depart_time, flight.Flight_Duration "
							+ ",flight.Flight_Seat_Cost, plane.Plane_Type,  flight.Flight_ID" + " FROM flightbookdb.flight " +
				"LEFT JOIN plane ON plane.Plane_ID = flight.Plane_ID " +		
				"WHERE Flight_Departure_Airport= ? AND Flight_Arrival_Airport= ? AND date(Flight_Date)= ? AND flight.Flight_Date IS NOT NULL");

		pst.setString(1, DepartureAirport);
        pst.setString(2, ArrivalAirport);
		pst.setString(3, FlightDate);

		rs = pst.executeQuery();
		
		int numOfRows = SupportMethods.getResultSetNumOfRows(rs);
		System.out.println("In FlightBookingDao flightListQuery... about to create a " + numOfColumns + " by " + numOfRows + " + 1 array.");	

		if (numOfRows >= 1) {
			System.out.println("In FlightBookingDao flightListQuery ... numOfRows is: " + numOfRows);
		this.aFlightList = new String[numOfRows + 1][numOfColumns];
		
		rs.beforeFirst();
		aFlightList[0][0] = ("Flight_Departure_Airport");
		aFlightList[0][1] = ("Flight_Arrival_Airport");
		aFlightList[0][2] = ("Flight_Date");		
		aFlightList[0][3] = ("Flight_Depart_Time");
		aFlightList[0][4] = ("Flight_Duration");
		aFlightList[0][5] = ("Flight_Seat_Cost");
		aFlightList[0][6] = ("Plane_Type");
		aFlightList[0][7] = ("Flight_ID");
		
		// Insert data rows
		int i = 1; 
		while (rs.next()) {
			System.out.println("In FlightBookingDao flightListQuery ... adding row number: " + i);
			
			aFlightList[i][0] = rs.getString("Flight_Departure_Airport");
			aFlightList[i][1] = rs.getString("Flight_Arrival_Airport");
			aFlightList[i][2] = rs.getString("Flight_Date");			
			aFlightList[i][3] = rs.getString("Flight_Depart_time");
			aFlightList[i][4] = rs.getString("Flight_Duration");
			aFlightList[i][5] = rs.getString("Flight_Seat_Cost");
			aFlightList[i][6] = rs.getString("Plane_Type");
			aFlightList[i][7] = rs.getString("Flight_ID");

			i++;
		}
		
        retVal = 0;
		} else {
			retVal = -6;
		}		
		
		} catch (SQLException e) {
			e.printStackTrace();
            retVal = -1;
		} catch (Exception e) {
			e.printStackTrace();
            retVal = -1;
		}finally {

            if (pst != null) {
                try {
                    pst.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            if (rs != null) {
                try {
                    rs.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
		System.out.println("In FlightBookingDao flightListQuery ... leaving flightListQuery");		
		
		
		return retVal;
		
		
		
	}	
	/*****************************************************************************
	Purpose: This method adds a single flight booking to the database.
	Authors:
	Parameters: 
	  		@param UserEmailAddress - String
	  		@param FlightID - Integer
	  		@param FlightSeatNum - String
	Return ValueS: 
			 		0 -> seat successfully booked
				   -3 -> unable to find customer in the database
				   -7 -> seat failed to be booked because it is already taken
				   -1 -> database access issue (which caused seat booking failure)
			@param retVal

	 ****************************************************************************/

	public int addFlightBooking(String UserEmailAddress, int FlightID, String FlightSeatNum ) {
		int retVal = -1;
		
		PreparedStatement pst = null;
		ResultSet rs = null;
		int Cust_ID;

		try {
		
			pst = conn.prepareStatement("Select Cust_ID from customer C INNER JOIN user U ON u.User_ID = C.User_ID AND u.User_Email_Address = ?" );
			
			pst.setString(1, UserEmailAddress);

			rs = pst.executeQuery();


			if (!rs.next()) {
				retVal = -3;

			} else {
				
				Cust_ID = rs.getInt(1);
				System.out.println("In FlightBookingDao addFlightBooking ... Custmer ID returned " + Cust_ID);
				
				PreparedStatement stmt = conn.prepareStatement("Insert into flight_seat_booking (Cust_ID, Flight_ID, Flight_Seat_Num, Flight_Seat_Rsvd) VALUES (?,?,?,?);");
			
				stmt.setInt(1, Cust_ID);
				stmt.setInt(2, FlightID);
				stmt.setString(3, FlightSeatNum);
				stmt.setBoolean(4, false);				
				if (stmt.executeUpdate() == 1) {
					retVal = 0;
				}
				
			}

		} catch (SQLIntegrityConstraintViolationException e) {
			System.out.println(e);
			retVal = -7;
		} catch (SQLException e) {
			System.out.println(e);
			retVal = -1;			
		} finally {

			if (pst != null) {
				try {
					pst.close();
				} catch (SQLException e) {
					e.printStackTrace();
					
				}
			}

			if (rs != null) {
				try {
					rs.close();
				} catch (SQLException e) {
					e.printStackTrace();
					
				}
			}
		}
		System.out.println("In FlightBookingDao addFlightBooking ... return value is: " + retVal);
		return retVal;		
	}
	/*****************************************************************************
	Purpose: This method creates a two dimensional array of all of the flight_seat_booking
	Authors:
	Parameters: 
	  		@param FlightID - Integer
	Return ValueS: 
			 		0 --> at least one record was found
				   -6 --> no record was found
				  -1 --> database access issue (which caused no records to be found)
			@param retVal

	 ****************************************************************************/

	public int flightSeatQuery(int FlightID) {
		
		int retVal = -1;
		int numOfColumns = 3; 
		PreparedStatement pst = null;
		ResultSet rs = null;
		
		try {
		System.out.println("In FlightBookingDao flightSeatQuery ... ");

		
		pst = conn.prepareStatement(
				"SELECT flight_seat_booking.flight_seat_num, flight_seat_booking.cust_id, flight_seat_booking.flight_seat_rsvd FROM flightbookdb.flight_seat_booking " +		
				"WHERE flight_seat_booking.Flight_ID= ? ");

		pst.setInt(1, FlightID);

		rs = pst.executeQuery();
		
		int numOfRows = SupportMethods.getResultSetNumOfRows(rs);

		System.out.println("In FlightBookingDao flightSeatQuery ... numOfRows is: " + numOfRows);

		if (numOfRows >= 1) {
	
			System.out.println("In FlightBookingDao flightSeatQuery ... numOfRows is: " + numOfRows);
		this.aBookedSeats = new String[numOfRows + 1][numOfColumns];
		
		rs.beforeFirst();

		aBookedSeats[0][0] = ("Flight_Seat_Num");
		aBookedSeats[0][1] = ("Cust_ID");
		aBookedSeats[0][2] = ("Flight_Seat_Rsvd");		
		
		int i = 1;
		while (rs.next()) {
			System.out.println("In FlightBookingDao flightSeatQuery ... adding row number: " + i);
			
			aBookedSeats[i][0] = rs.getString("Flight_Seat_Num");
			aBookedSeats[i][1] = rs.getString("Cust_ID");
			aBookedSeats[i][2] = rs.getString("Flight_Seat_Rsvd");			

			i++;
		}
		
        retVal = 0;
		} else {
			retVal = -6;
		}		
		
		} catch (SQLException e) {
			e.printStackTrace();
            retVal = -1;
		} catch (Exception e) {
			e.printStackTrace();
            retVal = -1;
		}finally {

            if (pst != null) {
                try {
                    pst.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            if (rs != null) {
                try {
                    rs.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }	
	
		System.out.println("In FlightBookingDao flightSeatQuery ... leaving flightSeatQuery");		
		
		
		return retVal;	
	
		
		
	}
	/*****************************************************************************
	Purpose: Sets the flight_seat_reserved flag for a particular flight/seat/customer entry.
	Authors:
	Parameters: 
	  		@param FlightID - Integer
	  		@param FlightSeatNum - String
	  		@param CustID - Integer
	Return ValueS: 
					 0 -> seat was successfully reserved for the customer
					-2 -> seat was not able to be reserved
					-3 -> seat was not able to be reserved because it has already been purchased by the customer
				@param retVal

	 ****************************************************************************/	
	public int reserveFlightSeat(int FlightID, String FlightSeatNum, int CustID) {	
	
		int retVal = -1;
		CallableStatement stmt = null;
		ResultSet rs = null;
		try {
		
		String query = "{CALL ReserveSeat(?,?,?,?)}";
		stmt = conn.prepareCall(query);
		
		stmt.registerOutParameter(1, java.sql.Types.INTEGER);
		stmt.setInt(2, FlightID);
		stmt.setString(3, FlightSeatNum);
		stmt.setInt(4, CustID);
		
		rs = stmt.executeQuery();

		retVal = stmt.getInt(1);
		
		} catch (Exception e) {
			System.out.println(e);
		} finally {

			if (stmt != null) {
				try {
					stmt.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}

			if (rs != null) {
				try {
					rs.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
		
		return retVal;
	}
	
	/*****************************************************************************
	Purpose: Clears the flight_seat_reserved flag for a particular flight/seat/customer entry.
	Authors:
	Parameters: 
	  		@param FlightID - integer
	  		@param FlightSeatNum - String
	  		@param CustID - integer
	Return ValueS: 
					 0 -> seat was successfully reserved for the customer
					 0 -> seat was already unreserved for the customer (this can
	                      happen if the timer maintained in the database expires
	                      and the entry gets automatically unreserved)
					-1 -> seat was not able to be unreserved because of some unforeseen database exception.
				@param retVal

	 ****************************************************************************/	
	
	public int unReserveFlightSeat(int FlightID, String FlightSeatNum, int CustID) {	
	
		int retVal = -1;
		CallableStatement stmt = null;
		ResultSet rs = null;
		try {
		
		String query = "{CALL UnReserveSeat(?,?,?,?)}";
		stmt = conn.prepareCall(query);
		
		stmt.registerOutParameter(1, java.sql.Types.INTEGER);
		stmt.setInt(2, FlightID);
		stmt.setString(3, FlightSeatNum);
		stmt.setInt(4, CustID);
		
		rs = stmt.executeQuery();

		retVal = stmt.getInt(1);
		
		} catch (Exception e) {
			System.out.println(e);
		} finally {

			if (stmt != null) {
				try {
					stmt.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}

			if (rs != null) {
				try {
					rs.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
		
		return retVal;
	}
	
	

	public String[][] getFlightList() {
		System.out.println("In FlightBookingDao. getFlightList  ... leaving get getFlightList");
		return aFlightList;
	}

	public String[][] getReservationList() {
		return resList;
	}
	
	public String[][] getFlightSeatList() {
		System.out.println("In FlightBookingDao. getFlightSeatList  ... leaving get getFlightSeatList");
		return aBookedSeats;
	}

	/*****************************************************************************
	Purpose: Search the array to check the seat number 
	Authors:
	Parameters: 
	  		@param toSearch - String
	Return ValueS: 
				@param retVal

	 ****************************************************************************/	
	public String searchSeat(String toSearch){
		String retVal="";
		for (int i=0;i< this.aBookedSeats.length;i++){
			if (this.aBookedSeats[i][0].equals(toSearch))
				retVal += aBookedSeats[i][0];
		}
		return retVal;
	}
	/*****************************************************************************
	Purpose: Get customer id from the db 
	Authors:
	Parameters: 
	  		@param UserEmailAddress - String
	Return ValueS: 
				@param customerId

	 ****************************************************************************/	
	public int getCustId(String UserEmailAddress){
		PreparedStatement pst = null;
		ResultSet rs = null;
		int Cust_ID;
		int retVal=0;
		try {

			pst = conn.prepareStatement("Select Cust_ID from customer C INNER JOIN user U ON u.User_ID = C.User_ID AND u.User_Email_Address = ?" );			
			pst.setString(1, UserEmailAddress);
			rs = pst.executeQuery();
			if (!rs.next()) {
				retVal = -3; //no results from querying for CustId

			} else {

				Cust_ID = rs.getInt(1);
				customerId= Cust_ID;
				System.out.println("In FlightBookingDao addFlightBooking ... Custmer ID returned " + Cust_ID);
			} 
		}catch (SQLIntegrityConstraintViolationException e) {
			System.out.println(e);
			retVal = -7;
		} catch (SQLException e) {
			System.out.println(e);
			retVal = -1;			
		} finally {

			if (pst != null) {
				try {
					pst.close();
				} catch (SQLException e) {
					e.printStackTrace();

				}
			}
			if (rs != null) {
				try {
					rs.close();
				} catch (SQLException e) {
					e.printStackTrace();

				}
			}
		}

		return customerId ;
	}
	/*****************************************************************************
	Purpose: Return reservation time out
	Authors:
	Parameters: 
	  		@param customer_id - Integer
	Return ValueS: 
				@param retVal

	 ****************************************************************************/	
	public int getReservationTimeLeft(int customer_id){
		
		PreparedStatement pst = null;
		int numOfColumns = 3; 
		ResultSet rs = null;
        String cust_id = Integer.toUnsignedString(customer_id);
		int retVal=-9;
		
		try {

			pst = conn.prepareStatement("Select Flight_ID, Flight_Seat_Rsvd, Flight_Seat_Rsvd_Time from flightbookdb.flight_seat_booking WHERE Cust_ID = ?" );			
			pst.setString(1, cust_id);
			
			rs = pst.executeQuery();
			
			int numOfRows = SupportMethods.getResultSetNumOfRows(rs);

			if (numOfRows >= 1) {
				this.resList = new String[numOfRows + 1][numOfColumns];
				rs.beforeFirst();
				resList[0][0] = ("Flight_ID");
				resList[0][1] = ("Flight_Seat_Rsvd");
				resList[0][2] = ("Flight_Seat_Rsvd_Time");
				int i = 1; //Need to initialize to 1 ... we already loaded stuff into Row 0
				while (rs.next()) {
				
					resList[i][0] = rs.getString("Flight_ID");
					resList[i][1] = rs.getString("Flight_Seat_Rsvd");
					resList[i][2] = rs.getString("Flight_Seat_Rsvd_Time");			
					i++;
				}
				retVal = 0;
			} else {
				retVal = -6;
			}
			 
		}catch (SQLIntegrityConstraintViolationException e) {
			// This is the exception that is thrown if the seat has already been taken
			System.out.println(e);
			retVal = -7;
		} catch (SQLException e) {
			System.out.println(e);
			retVal = -1;			
		} finally {

			if (pst != null) {
				try {
					pst.close();
				} catch (SQLException e) {
					e.printStackTrace();

				}
			}
			if (rs != null) {
				try {
					rs.close();
					
				} catch (SQLException e) {
					e.printStackTrace();

				}
			}
		}

		return retVal;
	}
	
}