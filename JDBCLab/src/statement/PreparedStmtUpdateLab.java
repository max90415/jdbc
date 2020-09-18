package statement;

import java.math.BigDecimal;
import java.sql.*;

public class PreparedStmtUpdateLab {

	public static void main(String[] args) {
		// jdbc
		try (Connection connection = DriverManager.getConnection("jdbc:oracle:thin:@//localhost:1521/xepdb1", "scott",
				"tiger");) {

			Statement stmt = connection.createStatement();
			ResultSet rs = stmt.executeQuery("select * from emp, dept where emp.deptno=dept.deptno");
			PreparedStatement predstmt = connection.prepareStatement("update emp set commission=? where empno=?");
			
			while ( rs.next()) {
				
				String location = rs.getString("location");
				if ( location.equals("台北")) {
					int empno = rs.getInt("empno");//shift alt + l 
					BigDecimal commission = rs.getBigDecimal("commission");
					System.out.println(empno);
					BigDecimal newCommission =  commission.add(new BigDecimal(100));
					predstmt.setBigDecimal(1, newCommission);
					predstmt.setInt(2, empno);
					predstmt.executeUpdate();
					predstmt.clearParameters();
					
					
					
				}
			}
			
			
			
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
	}

}

