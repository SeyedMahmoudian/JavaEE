/*****************************************************************************
Class Name: Master
Authors:
Purpose: Master class which is responsible to make database connection 


 *****************************************************************************/
package com.amzi.dao;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Master {

	private static String url;
	private static String dbName; 
	private static String dbOptions;
	private static String driver;
	private static String userName;
	private static String dbPassword;
	private Connection conn = null;

	public Master() {
		url = "jdbc:mysql://localhost:3306/";
		dbName = "flightBookDB";
		dbOptions = "?verifyServerCertificate=false&useSSL=false";
		driver = "com.mysql.jdbc.Driver";
		userName = "root";
		dbPassword = "supersecret";
	}
	
	/*****************************************************************************
	Purpose: Make connection happens and if it was successful return connection object 
	  		 this function will check the conn object for being null or not if it is not null that means
	  		 we already have a connection and just return that if it is null that means there is no connection
	  		 to the database 
	Authors:
	Parameters: void
	Return ValueS:
			@param conn

	 ****************************************************************************/
	public Connection makeConnection() {

		if (conn == null) {
			try {
				Class.forName(driver).newInstance();
				conn = DriverManager.getConnection(url + dbName + dbOptions, userName, dbPassword);
				//return conn;
			} catch (Exception e) {
				System.out.println("Error detected during makeConnection");
				e.printStackTrace();
				
			}
		}
		return conn;
	}
	/*****************************************************************************
	Purpose: close connection when it get called 
	Authors:
	Parameters: void
	Return ValueS:
			@param boolean

	 ****************************************************************************/

	public boolean closeConnection(){
		try {
			conn.close();
			return true;
		} catch (SQLException e) {
			return false;
		}
		
	}
}