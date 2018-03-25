
/*****************************************************************************
Class Name: CustomerDao
Authors:
Purpose:Database Access Object to get customer information from db


 *****************************************************************************/
package com.amzi.dao;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import misc.SupportMethods;

public class CustomerDao {
	private String firstName = null, lastName = null, userPassword = null, email = null, phoneNumber = null,
			streetNum = null, streetName = null, city = null, country = null, postCode = null, aptNum = null;
	private String creditCardNum = null, creditName = null, expDate = null, creditCode = null;
	private String departTime = null, flightDate = null, seatCost = null, departAirport = null, arrivalAirport = null,
			duration = null, planeType = null;
	private String[][] data  = null;
	private List<String> al_AirportNames = null;
	private Master master = new Master();
	private Connection conn = master.makeConnection();

	/*****************************************************************************
	Purpose:Get client information from database 
	Authors:
	Parameters: void  		
	Return ValueS: 
			 		0-> at least one record found
				   -6-> no record was found
				   -1-> db issue
			@param retVal

	 ****************************************************************************/

	public int customerBasicInformationQuery() {
		int retVal = -1;

		try {

			PreparedStatement pst = null;
			pst = conn.prepareStatement("SELECT Cust_First_Name ,Cust_Last_Name,Cust_Apartment_Num,"
					+ "Cust_Street_Num,Cust_Street_Name,Cust_City,Cust_Phone_Number,Cust_Postal_Code,"
					+ "Cust_Country,User_Email_Address,User_Password,"
					+ "Credit_Card_Num,Credit_Card_Code,Credit_Card_Expiry," + "Credit_Card_Name"
					+ " FROM customer LEFT JOIN `user` ON customer.User_ID=`user`.User_ID "
					+ " LEFT JOIN credit_card on customer.Credit_Card_ID=credit_card.Credit_Card_ID "
					+ " WHERE `user`.User_Email_Address= ?");
			pst.setString(1, email);

			ResultSet rs = pst.executeQuery();

			if (rs.next()) {
				firstName = rs.getString("Cust_First_Name");
				lastName = rs.getString("Cust_Last_Name");
				userPassword = rs.getString("User_Password");
				phoneNumber = rs.getString("Cust_Phone_Number");
				streetNum = rs.getString("Cust_Street_Num");
				streetName = rs.getString("Cust_Street_Name");
				city = rs.getString("Cust_City");
				country = rs.getString("Cust_Country");
				postCode = rs.getString("Cust_Postal_Code");
				aptNum = rs.getString("Cust_Apartment_Num");
				creditCardNum = rs.getString("Credit_Card_Num");
				creditName = rs.getString("Credit_Card_Name");
				expDate = rs.getString("Credit_Card_Expiry");
				creditCode = rs.getString("Credit_Card_Code");
				retVal = 0;
			} else {
				retVal = -1;
			}
			rs.close();
			pst.close();
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return retVal;

	}
	/*****************************************************************************
	Purpose:Get all the booked flights from database for the client
	Authors:
	Parameters: void  		
	Return ValueS: 
			 		0-> at least one record found
				   -6-> no record was found
				   -1-> db issue
			@param retVal

	 ****************************************************************************/

	public int customerBookedFlightQuery() {
		int retVal = -1;
		int numOfColumns = 7;  
		
		PreparedStatement pst = null;
		ResultSet rs = null;

		try {
			System.out.println("In customerBookedFlightQuery ... ");
			pst = conn.prepareStatement(
					"SELECT flight.Flight_Date,flight.Flight_Depart_time, flight_seat_booking.Flight_Seat_Cost,flight.Flight_Departure_Airport, "
							+ "flight.Flight_Arrival_Airport,flight.Flight_Duration,plane.Plane_Type " + "FROM user "
							+ "LEFT JOIN customer ON customer.User_ID=user.User_ID "
							+ "LEFT JOIN flight_seat_booking ON flight_seat_booking.Cust_ID=customer.Cust_ID "
							+ "LEFT JOIN flight ON flight.Flight_Depart_time = flight_seat_booking.Flight_Depart_time AND "
							+ "flight.Flight_Date = flight_seat_booking.Flight_Date AND "
							+ "flight.Plane_ID = flight_seat_booking.Plane_ID "
							+ "LEFT JOIN plane ON plane.Plane_ID = flight_seat_booking.Plane_ID "
							+ "WHERE user.User_Email_Address= ? AND flight.Flight_Date IS NOT NULL;");

			pst.setString(1, email);

			rs = pst.executeQuery();
			
			int numOfRows = SupportMethods.getResultSetNumOfRows(rs);
			System.out.println("In customerDao customerBookedFlightQuery... about to create a " + numOfColumns + " by " + numOfRows + " + 1 array.");	

			if (numOfRows >= 1) {
				System.out.println("In customerDao ... numOfRows is: " + numOfRows);
			this.data = new String[numOfRows + 1][numOfColumns];
			
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
				System.out.println("In customerDao ... adding row number: " + i);
				
				data[i][0] = SupportMethods.getTimePortion(rs.getString("Flight_Depart_time"));
				data[i][1] = SupportMethods.getDatePortion(rs.getString("Flight_Date"));
				data[i][2] = rs.getString("Flight_Seat_Cost");
				data[i][3] = rs.getString("Flight_Departure_Airport");
				data[i][4] = rs.getString("Flight_Arrival_Airport");
				data[i][5] = rs.getString("Flight_Duration");
				data[i][6] = rs.getString("Plane_Type");

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
		System.out.println("In customerDao ... leaving customerBookedFlightQuery");
		return retVal;
	}
	/*****************************************************************************
	Purpose:Get all the unique airport name from the db
	Authors:
	Parameters: void  		
	Return ValueS: 
			 		0-> at least one record found
				   -6-> no record was found
				   -1-> db issue
			@param retVal

	 ****************************************************************************/
	public int airportQuery() {
		int retVal = -1;

		PreparedStatement pst = null;
		ResultSet rs = null;

		try {

			pst = conn.prepareStatement("SELECT DISTINCT Flight_Arrival_Airport FROM flight");

			rs = pst.executeQuery();
			int numOfRows = SupportMethods.getResultSetNumOfRows(rs);

			System.out.println("In FlightBookingDao airportQuery ... numOfRows is: " + numOfRows);

			if (numOfRows >= 1) {
				al_AirportNames = new ArrayList<String>();
				rs.beforeFirst();
				while (rs.next()) {
					al_AirportNames.add(rs.getString("Flight_Arrival_Airport"));
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

	
	/**
	 * @return list of Airport Names
	 */
	public List<String> getAirportNames() {
		return al_AirportNames;
	}


	/**
	 * @return the email
	 */
	public String getEmail() {
		return email;
	}

	/**
	 * @return the departTime
	 */
	public String getDepartTime() {
		return departTime;
	}

	/**
	 * @return the flightDate
	 */
	public String getFlightDate() {
		return flightDate;
	}
	
	public String[][] getData() {
		System.out.println("In customerDao ... leaving get Data");
		return data;
	}

	/**
	 * @return the seatCost
	 */
	public String getSeatCost() {
		return seatCost;
	}

	/**
	 * @return the departAirport
	 */
	public String getDepartAirport() {
		return departAirport;
	}

	/**
	 * @return the arrivalAirport
	 */
	public String getArrivalAirport() {
		return arrivalAirport;
	}

	/**
	 * @return the duration
	 */
	public String getDuration() {
		return duration;
	}

	/**
	 * @return the planeType
	 */
	public String getPlaneType() {
		return planeType;
	}

	/**
	 * @return the master
	 */
	public Master getMaster() {
		return master;
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
	 * @param email
	 *            the email to set
	 */
	public void setEmail(String email) {
		this.email = email;
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


	
}