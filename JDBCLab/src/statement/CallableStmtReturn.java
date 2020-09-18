package statement;

import java.sql.*;

public class CallableStmtReturn {

	public static void main(String[] args) {
		
		try (Connection connection = DriverManager.getConnection("jdbc:oracle:thin:@//localhost:1521/xepdb1", "scott",
				"tiger");) {
				
			CallableStatement cstmt = connection.prepareCall("{ ? = call checkmanager(?) }");
			cstmt.registerOutParameter(1, java.sql.Types.INTEGER);
			cstmt.setInt(2, 1002);
			cstmt.execute();
			int result = cstmt.getInt(1);//取得第一個問號回傳值
			if ( result == 1) {
				System.out.println("經理");
			}else {
				System.out.println("不是經理");				
			}
			
			
		} catch (SQLException e) {
			e.printStackTrace();
		}

		
		
		
		

	}

}

