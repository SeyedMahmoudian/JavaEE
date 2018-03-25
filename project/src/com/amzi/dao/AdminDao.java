/*****************************************************************************
Class Name: AdminDao
Authors:
Purpose:Database Access Object for admin 


 *****************************************************************************/

package com.amzi.dao;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Time;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import misc.SupportMethods;
import com.amzi.dao.Master;
import java.sql.Statement;

public class AdminDao {

	private List<String> list = new ArrayList<>();
	private static Master master;
	private String firstName = null, lastName = null, userPassword = null, email = null, phoneNumber = null,
			streetNum = null, streetName = null, city = null, country = null, postCode = null, aptNum = null,
			creditCardNum = null, creditName = null, expDate = null, creditCode = null, flightDate = null,
			flightDepartTime = null, flightArrivalAirport = null, flightDepartAirport = null;
	private byte planeType;
	private String[][] data = null;
	private double seatCost=0.0;
	private int flightDuration=0;
	/*****************************************************************************
	Purpose: Default constructor needed for servlet
	Authors:
	Parameters: void
	Return ValueS: void
	 *****************************************************************************/
	public AdminDao() {

	}
	/*****************************************************************************
	Purpose: Initialize constructor
	Authors:
	Parameters:
	  		@param planeType - byte
	 		@param flightDate - String
	 		@param flightDepartTime - String
	 		@param flightDuration - integer
	 		@param flightArrivalAirport - String
	 		@param flightDepartAirport - String
	  		@param flightSeatCost - double
	Return ValueS: void
	 ****************************************************************************/
	public AdminDao(byte planeType, String flightDate, String flightDepartTime, int flightDuration, String flightArrivalAirport,
			String flightDepartAirport, double flightSeatCost) {
		System.out.println("In AdminDao constructor ... just entered");
		this.planeType = planeType;
		this.flightDate=flightDate;
		this.flightDepartTime=flightDepartTime;
		this.flightDuration=flightDuration;
		this.flightArrivalAirport=flightArrivalAirport;
		this.flightDepartAirport=flightDepartAirport;
		this.seatCost=flightSeatCost;
		master = new Master();

	}
	/*****************************************************************************
	Purpose:Show all the booked flight for specific client, search by email
	Authors:
	Parameters: void  		
	Return ValueS: 
			 		0-> at least one record found
				   -6-> no record was found
				   -1-> db issue
			@param retVal

	 ****************************************************************************/
	public int queryByUserEmail() {
		int retVal =0;
		int numOfColumns = 7;
		Connection conn=master.makeConnection();
		PreparedStatement pst = null;
		ResultSet rs = null;
		try {
			pst = conn.prepareStatement(
					"SELECT Flight_Date,Flight_Depart_time, Flight_Seat_Cost,Flight_Departure_Airport,"
							+ "Flight_Arrival_Airport,Flight_Duration,Plane_type" + "FROM user"
							+ "LEFT JOIN customer ON customer.User_ID=user.User_ID"
							+ "LEFT JOIN flight_seat_booking ON flight_seat_booking.Cust_ID=customer.Cust_ID"
							+ "	LEFT JOIN flight_seat_booking ON flight_seat_booking.Cust_ID=customer.Cust_ID"
							+ "LEFT JOIN flight ON flight.Flight_Depart_time = flight_seat_booking.Flight_Depart_time AND"
							+ "flight.Flight_Date = flight_seat_booking.Flight_Date AND"
							+ " flight.Plane_ID = flight_seat_booking.Plane_ID"
							+ "LEFT JOIN plane ON plane.Plane_ID = flight_seat_booking.Plane_ID"
							+ "WHERE user.User_Email_Address= ? AND floght.Flight_Date IS NOT NULL;");
			pst.setString(1, email);
			rs = pst.executeQuery();
			System.out.println(
					"In AdminDao getting booked flights from the specific client..... numOfRows:" + rs.getRow());

			if (rs.getRow() >= 1) {
				this.data = new String[rs.getRow() + 1][numOfColumns];

				rs.beforeFirst();

				data[0][0] = ("Flight_Depart_time");
				data[0][1] = ("Flight_Date");
				data[0][2] = ("Flight_Seat_Cost");
				data[0][3] = ("Flight_Departure_Airport");
				data[0][4] = ("Flight_Arrival_Airport");
				data[0][5] = ("Flight_Duration");
				data[0][6] = ("Plane_Type");
				int i = 1;

				while (rs.next()) {

					data[i][0] = SupportMethods.getDatePortion(rs.getString("Flight_Depart_time"));
					data[i][1] = SupportMethods.getDatePortion(rs.getString("Flight_Date"));
					data[i][2] = rs.getString("Flight_Seat_Cost");
					data[i][3] = rs.getString("Flight_Departure_Airport");
					data[i][4] = rs.getString("Flight_Arrival_Airport");
					data[i][5] = rs.getString("Flight_Duration");
					data[i][6] = rs.getString("Plane_Type");
					i++;
				}
				System.out.println("IN AdminDao new row number added: " + i);
				retVal = 0;
			} else {
				retVal = -6;
			}
		} catch (SQLException e) {
			System.out.println("Exception AdminDao line 120"+e.getMessage());
			retVal = -1;
		} catch (Exception e) {
			System.out.println("Exception AdminDao line 123"+e.getMessage());
			retVal = -1;
		} finally {
			if (pst != null) {
				try {
					pst.close();
				} catch (SQLException e) {
					System.out.println("Exception AdminDao line 130"+e.getMessage());
				}
			}
			if (rs != null) {
				try {
					rs.close();
				} catch (SQLException e) {
					System.out.println("Exception AdminDao line 137"+e.getMessage());
				}
			}
		}
		System.out.println("In Admin Dao about to leave querySpecifiClient()");
		return retVal;

	}

	/*****************************************************************************
	Purpose:Register a flight into database
	Authors:
	Parameters: void  		
	Return ValueS: 
			 0-> flight registered
			-6-> failed to register the flight
			-1-> db issue
			@param retVal

	 ****************************************************************************/

	public int registerFlight(){
		Connection conn = master.makeConnection();
		PreparedStatement pst = null;
		ResultSet rs = null;
		int status = 0;

		int retVal=0;		
		System.out.println("In AdminDao registerFlight ... data being sent to database is:");
		System.out.println("flightDateSql: " + flightDate);
		System.out.println("flightDepartTimeSql: " + flightDepartTime);
		System.out.println("flightDuration: " + flightDuration);
		System.out.println("flightArrivalAirport: " + flightArrivalAirport);
		System.out.println("flightDepartAirport: " + flightDepartAirport);
		System.out.println("seatCost: " + seatCost);

		try{


			pst = conn.prepareStatement("INSERT INTO flight(Plane_ID,Flight_Date,Flight_Depart_Time,Flight_Duration,Flight_Arrival_Airport,"
					+ "Flight_Departure_Airport,Flight_Seat_Cost) VALUES (?,?,?,?,?,?,?)");

			pst.setInt(1, 1);
			pst.setString(2,  flightDate);
			pst.setString(3,  flightDepartTime);
			pst.setInt(4, flightDuration);
			pst.setString(5, flightArrivalAirport);
			pst.setString(6, flightDepartAirport);
			pst.setDouble(7, seatCost);			

			status = pst.executeUpdate();
			System.out.println("In AdminDao registerFlight ... retVal is: " + retVal);

			if (status == 1){
				System.out.println("In AdminDao registerFlight ... successfully to registered flight");
				retVal = 0;

			}
			else{
				System.out.println("In AdminDao registerFlight ... failed to register flight");
				retVal = -6;
			}			



		}catch (SQLException e) {
			System.out.println("In AdminDao registerFlight ... database access error: "+e.getMessage());
			retVal=-1;

		}finally {
			if(rs!=null){
				try{
					rs.close();
				}catch (SQLException z) {
					System.out.println("SQLException line 187 AdminDao it happened in finally"+z.getMessage());
					//retVal=-1;
				}
			}
			if (pst != null) {
				try {
					pst.close();
				} catch (SQLException e) {
					System.out.println("Exception AdminDao line 130"+e.getMessage());
				}
			}
		}

		System.out.println("In Admin Dao about to leave registerFlight()");
		return retVal;
	}


	/**
	 * @return the list
	 */
	public List<String> getList() {
		return list;
	}

	/**
	 * @return the firstName
	 */
	public String getFirstName() {
		return firstName;
	}


	/**
	 * @return the lastName
	 */
	public String getLastName() {
		return lastName;
	}

	/**
	 * @return the userPassword
	 */
	public String getUserPassword() {
		return userPassword;
	}

	/**
	 * @return the email
	 */
	public String getEmail() {
		return email;
	}
	/**
	 * @param email
	 *            the email to set
	 */
	public void setEmail(String email) {
		this.email = "admin@admin.com";
	}


	/**
	 * @return the phoneNumber
	 */
	public String getPhoneNumber() {
		return phoneNumber;
	}

	/**
	 * @return the streetNum
	 */
	public String getStreetNum() {
		return streetNum;
	}

	/**
	 * @return the streetName
	 */
	public String getStreetName() {
		return streetName;
	}

	/**
	 * @return the city
	 */
	public String getCity() {
		return city;
	}

	/**
	 * @return the country
	 */
	public String getCountry() {
		return country;
	}

	/**
	 * @return the postCode
	 */
	public String getPostCode() {
		return postCode;
	}

	/**
	 * @return the aptNum
	 */
	public String getAptNum() {
		return aptNum;
	}

	/**
	 * @return the creditCardNum
	 */
	public String getCreditCardNum() {
		return creditCardNum;
	}

	/**
	 * @return the creditName
	 */
	public String getCreditName() {
		return creditName;
	}

	/**
	 * @return the expDate
	 */
	public String getExpDate() {
		return expDate;
	}

	/**
	 * @return the creditCode
	 */
	public String getCreditCode() {
		return creditCode;
	}

	/**
	 * @return the flightDate
	 */
	public String getFlightDate() {
		return flightDate;
	}

	/**
	 * @return the seatCost
	 */
	public Double getSeatCost() {
		return seatCost;
	}

	/**
	 * @return the planeType
	 */
	public byte getPlaneType() {
		return planeType;
	}

	/**
	 * @return the data
	 */
	public String[][] getData() {
		return data;
	}

}