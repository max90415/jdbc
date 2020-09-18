package statement;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class StatementUpdate {

	public static void main(String[] args) {

			try (Connection conn = DriverManager.getConnection("jdbc:oracle:thin:@//localhost:1521/xepdb1", "scott", "tiger");
				 Statement stmt = conn.createStatement();) {

				int updated = stmt.executeUpdate("update emp set salary = 20000 where empno=1001");
				if ( updated > 0 ){
					System.out.println("update successed");
				}else{
					System.out.println("update failed");					
				}
				System.out.println("query finished");
			} catch (SQLException e) {
				e.printStackTrace();
			}
	}

}
