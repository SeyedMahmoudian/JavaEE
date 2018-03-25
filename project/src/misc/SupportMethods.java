package misc;

import java.sql.ResultSet;
import java.sql.SQLException;

public class SupportMethods {


	// Helper method to count the number of rows of data in a ResultSetMethod
	public static int getResultSetNumOfRows(ResultSet rs) {
		int numOfRows = 0;

		try {
			// rs.beforeFirst();
			while (rs.next()) {
				//if (rs.getString("Flight_Depart_time") != null) {
					numOfRows++;

				//}
			}
			System.out.println("in customerBookedFlightQuery ... number of rows found are:" + numOfRows);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			numOfRows = -1; // Indicate to the calling function that an SQL error occured
		}
		return numOfRows;
	}
	
	// Helper method to quickly peel out the date portion of a DATETIME data type
	public static String getDatePortion(String input) {
		String retStr = null;
		String[] pieces=input.split("\\s");

		if (pieces.length == 2) {
			retStr = pieces[0];
		}	
		return retStr;

	}

	// Helper method to quickly peel out the time portion of a DATETIME data type
	public static String getTimePortion(String input) {
		String retStr = null;
		String[] pieces=input.split("\\s");

		if (pieces.length == 2) {
			retStr = pieces[1];
		}	
		return retStr;

	}
	
	

}
