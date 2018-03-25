
/*****************************************************************************
Class Name: CreditCardDao
Authors:
Purpose: database manager object in order to add credit card into our data base

 *****************************************************************************/
package com.amzi.dao;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;

public class CreditCardDao {

	private String creditCardNum, creditName = null, email = null, expDate = null;
	private byte creditCode = 0;

	private static Master master;

	/*****************************************************************************
	  Purpose: Initialize constructor 
	  Authors: 
	  Parameters:
			  @param creditCardNum - String
			  @param expDate - String
			  @param creditName - String
			  @param creditCode - byte
			  @param email - String
	  Return ValueS: void
	 ****************************************************************************/
	public CreditCardDao(String creditCardNum, String expDate, String creditName, byte creditCode, String email) {
		super();
		this.creditCardNum = creditCardNum;
		this.expDate = expDate;
		this.creditName = creditName;
		this.creditCode = creditCode;
		this.email = email;
		master = new Master();
	}

	/*****************************************************************************
	  	Purpose:add client credit card to database 
	  	Authors: 
	  	Parameters:void
	 	Return ValueS: 
 					0-> at least one record found 
 				   -6-> no record was found
  				   -1-> db issue 
	  			@param retVal
	 *****************************************************************************/

	public int addCreditCard() {
		Connection conn = master.makeConnection();
		@SuppressWarnings("unused")
		boolean connection = true;
		ResultSet rs = null;
		int retVal = -1;
		SimpleDateFormat sdf = new SimpleDateFormat("YYYY-MM-DD");
		java.sql.Date date = null;
		try {
			date = (java.sql.Date) sdf.parse(expDate);
		} catch (ParseException e1) {
			e1.printStackTrace();
		}

		try {
			String query = "{CALL ADDCreditCard(?,?,?,?,?,?)}";
			CallableStatement stmt = conn.prepareCall(query);

			stmt.registerOutParameter(1, java.sql.Types.INTEGER);
			stmt.setString(2, email);
			stmt.setString(3, creditCardNum);
			stmt.setInt(4, creditCode);
			stmt.setDate(5, date);
			stmt.setString(6, creditName);

			rs = stmt.executeQuery();
			retVal = stmt.getInt(1);

		} catch (Exception e) {
			System.out.println(e.getMessage());
		} finally {
			if (rs != null) {
				try {
					rs.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}

		try {
			conn.close();
		} catch (SQLException e) {

			e.printStackTrace();
		}
		return retVal;

	}

}
