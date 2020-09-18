package statement;

import java.math.BigDecimal;
import java.sql.*;

public class CallableStmtInOutParam {

	public static void main(String[] args) {
		
		try (Connection connection = DriverManager.getConnection("jdbc:oracle:thin:@//localhost:1521/xepdb1", "scott",
				"tiger");) {
			
			CallableStatement cstmt = connection.prepareCall("{ call sum_salary(?,?)     }");
			cstmt.setInt(1, 10);//查詢部門編號10
			cstmt.registerOutParameter(2, java.sql.Types.NUMERIC);
			cstmt.execute();
			
			BigDecimal sum = cstmt.getBigDecimal(2);//取得第二個問號的結果，因為前面有註冊第2問號是out，有回傳
			System.out.println(sum);

		} catch (SQLException e) {
			e.printStackTrace();
		}

		
		
		
		
		
	}

}




