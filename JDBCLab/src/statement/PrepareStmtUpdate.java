package statement;

import java.math.BigDecimal;
import java.sql.*;

public class PrepareStmtUpdate {

	public static void main(String[] args) {
		
		try (Connection connection = DriverManager.getConnection("jdbc:oracle:thin:@//localhost:1521/xepdb1", "scott",
				"tiger");) {
			
			PreparedStatement pstmt = connection.prepareStatement("update emp set salary=? where empno=?");
			BigDecimal salary = new BigDecimal("22000.3");
			BigDecimal newSalary = salary.divide(new BigDecimal("3"), 10, BigDecimal.ROUND_DOWN);
			System.out.println(newSalary);
			pstmt.setBigDecimal(1, salary);
			pstmt.setInt(2, 1001);
			pstmt.executeUpdate();
			
			pstmt.clearParameters();//清成？才能再次執行
			
			pstmt.setBigDecimal(1, new BigDecimal("10000"));
			pstmt.setInt(2, 1002);
			pstmt.executeUpdate();
			
			
			System.out.println("done");
			
		} catch (SQLException e) {
			e.printStackTrace();
		}

		
	}

}