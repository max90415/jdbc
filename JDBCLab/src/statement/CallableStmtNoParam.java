package statement;

import java.sql.*;
import java.util.PrimitiveIterator.OfDouble;

import org.omg.PortableInterceptor.SUCCESSFUL;

public class CallableStmtNoParam {

	public static void main(String[] args) {
		
		try (Connection connection = DriverManager.getConnection("jdbc:oracle:thin:@//localhost:1521/xepdb1", "scott",
				"tiger");) {
			
			CallableStatement cstmt = connection.prepareCall("{ call QueryEmployee  }");
			cstmt.execute();
			boolean moreResult = cstmt.getMoreResults();//Oracle要先呼叫getMoreResults, SQL Server不用
			System.out.println(moreResult);
			if ( moreResult ) {
				ResultSet rs = cstmt.getResultSet();
				while ( rs.next()) {
					System.out.println(rs.getString("empno")+","+rs.getString("ename"));
				}
			}
			

		} catch (SQLException e) {
			e.printStackTrace();
		}

		
		
		
	}

}

