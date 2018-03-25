/*****************************************************************************
Class Name: LoginDao
Authors:
Purpose:Database Access Object to Login 


 *****************************************************************************/

package com.amzi.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;


public class LoginDao {

	private static Master master;

	public LoginDao() {

		master = new Master();

	}

	/*****************************************************************************
	Purpose:This method will invoke the login action 
	Authors:
	Parameters: 
	  		@param name - String
	  		@param pass - String
	Return ValueS: 
			 		0-> at least one record found
				   -6-> no record was found
				   -1-> db issue
			@param retVal

	 ****************************************************************************/
	public int validate(String name, String pass) {
		boolean status = false;
		
		Connection conn = master.makeConnection();
		boolean connection = true;
		
		PreparedStatement pst = null;
		ResultSet rs = null;
		int found = -1;
		String saltedPass = pass;

		try {
		
			pst = conn.prepareStatement(
					"select User_Type from user where User_Email_Address= ? and User_Password = BINARY ?");
			pst.setString(1, name);
			pst.setString(2, saltedPass);

			rs = pst.executeQuery();
			status = rs.next();

			if (status) {
				found = rs.getInt(1);
			} else {
				found = -1;
			}
		} catch (Exception e) {
			System.out.println(e);
		} finally {
			connection=master.closeConnection();
			if(connection!=true){
				connection=master.closeConnection();
			}
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
		return found;
	}
}