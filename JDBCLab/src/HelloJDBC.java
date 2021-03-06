import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class HelloJDBC {

	public static void main(String[] args) {
		
		try (Connection connection = DriverManager.getConnection("jdbc:oracle:thin:@//localhost:1521/xepdb1", "scott",
				"tiger");) {

		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		
		//jdk7, AutoCloseable
		try (   Connection connection =
				DriverManager.getConnection("jdbc:oracle:thin:@//localhost:1521/xepdb1", "scott", "tiger");
				Statement stmt = connection.createStatement();
				ResultSet rs = stmt.executeQuery("select * from emp"); )  {
			
			while( rs.next() ) {
				String name = rs.getString("ename");
				int empno = rs.getInt("empno");
				System.out.println(empno+"="+name);
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

}