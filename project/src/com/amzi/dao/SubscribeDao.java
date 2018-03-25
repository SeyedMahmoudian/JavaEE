/*****************************************************************************
Class Name: LoginDao
Authors:
Purpose:Database Access Object to Subscribe to our services


 *****************************************************************************/
package com.amzi.dao;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;


public class SubscribeDao {
	private String firstName, lastName, userPassword, email, phoneNumber, streetNum, streetName, city, country,
			postCode, aptNum;

	private static Master master;

	/*****************************************************************************
	Purpose: Initialize constructor
	Authors:
	Parameters:
	  @param firstName - String
	  @param lastName - String
	  @param userPassword - String
	  @param email - String
	  @param phoneNumber - String
	  @param streetNum - String
	  @param streetName - String
	  @param city - String
	  @param country - String
	  @param aptNum - String
	  @param creditName - String
	  @param expDate - String
	  @param creditNum - String
	  @param creditCode - String
	  @param postCode - String
	Return ValueS: void
	 ****************************************************************************/
       
	public SubscribeDao(String firstName, String lastName, String userPassword, String email, String phoneNumber,
			String streetNum, String streetName, String city, String country, String aptNum,String postCode) {
		this.firstName = firstName;
		this.lastName = lastName;
		this.userPassword = userPassword;
		this.email = email;
		this.phoneNumber = phoneNumber;
		this.streetNum = streetNum;
		this.streetName = streetName;
		this.city = city;
		this.country = country;
		this.aptNum = aptNum;
		this.postCode = postCode;
		master=new Master();

	}
	/*****************************************************************************
	Purpose: Add client to database
	Authors:
	Parameters:void
	Return ValueS: 
					client row number from the db
					-1 -> in case of failure 
				@param retVal
	 ****************************************************************************/
	public int registerUser() {
		Connection conn = master.makeConnection();
	
		@SuppressWarnings("unused")
		ResultSet rs = null;
		int retVal = -1;
		
		try {
			String query = "{CALL AddClient(?,?,?,?,?,?,?,?,?)}";
			CallableStatement stmt = conn.prepareCall(query);

			stmt.registerOutParameter(1, java.sql.Types.INTEGER);
			/*insert into user*/
			stmt.setString(2, email);
			stmt.setString(3, userPassword);
			/*insert into customer*/
			stmt.setString(4, firstName);
			stmt.setString(5, lastName);
			stmt.setString(6, aptNum);
			stmt.setString(7, streetNum);
			stmt.setString(8, streetName);
			stmt.setString(9, city);
			stmt.setString(10, country);
			stmt.setString(11, postCode);
			stmt.setString(12, phoneNumber);
			
			
			rs = stmt.executeQuery();
			retVal = stmt.getInt(1);

		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		return retVal;
	}

}
