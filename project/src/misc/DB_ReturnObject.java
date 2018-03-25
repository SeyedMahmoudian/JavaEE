package misc;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;

public class DB_ReturnObject {
	private int status;
	private ResultSet rs;

	public DB_ReturnObject(int status, ResultSet rs) {
		this.status = status;
		this.rs = rs;
		try {
			ResultSetMetaData rsmd = rs.getMetaData();
			
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
			

		
	}

	public int getStatus() {
		return status;
	}
	public ResultSet getResultSet() {
		System.out.println("in DB_ReturnObject.ResultSet ... rs is: " + rs);
		try {
			System.out.println("in DB_ReturnObject.ResultSet ... rs.next() is: " + rs.next());
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return rs;
	}
	
	public void closeResultSet() {		
		System.out.println("in DB_ReturnObject.closeResultSet ...");
		if (rs != null) {
			try {
				rs.close();
			} catch (SQLException e) {
				e.printStackTrace();
				System.out.println("in DB_ReturnObject.closeResultSet ... SQL exception occured");
			}
		}

	}	
	

}

