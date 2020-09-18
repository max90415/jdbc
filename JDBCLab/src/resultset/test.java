package resultset;

import java.sql.*;


public class test {

	public static void main(String[] args) {
		
		try (Connection connection = DriverManager.getConnection("jdbc:oracle:thin:@//localhost:1521/xepdb1", "scott",
				"tiger");) {

		} catch (SQLException e) {
			e.printStackTrace();
		}

		
		
		
		// TODO Auto-generated method stub

	}

}
