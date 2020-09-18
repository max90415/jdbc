package resultset;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class ResultLab {

	public static void main(String[] args) {
		//jdk7, AutoCloseable
		try (   Connection connection =
				DriverManager.getConnection("jdbc:oracle:thin:@//localhost:1521/xepdb1", "scott", "tiger");
				Statement stmt = connection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
				ResultSet rs = stmt.executeQuery("select * from emp"); )  {

			rs.absolute(5);
			System.out.println(rs.getString("ename"));
			
			
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
