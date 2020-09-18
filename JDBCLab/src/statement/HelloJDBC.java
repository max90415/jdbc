package statement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class HelloJDBC {

	public static void main(String[] args) {
		//jdk7, AutoCloseable
		try (   Connection connection =
				DriverManager.getConnection("jdbc:oracle:thin:@//localhost:1521/xepdb1", "scott", "tiger");
				Statement stmt = connection.createStatement();
				 )  {
			
			int updated = stmt.executeUpdate("update emp set salary = 1000 where empno=1012");
			if ( updated > 0 ) {
				System.out.println("更新成功");//empno = 1001
			}else {
				System.out.println("更新不成功，資料沒有被影響");// empno=1012
			}
			
			
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

}

