package statement;

import java.math.BigDecimal;
import java.sql.*;

public class PreparedStmtUpdateLabWrong {

	public static void main(String[] args) {
		// jdbc
		try (Connection connection = DriverManager.getConnection("jdbc:oracle:thin:@//localhost:1521/xepdb1", "scott",
				"tiger");) {

			Statement stmt = connection.createStatement();
			ResultSet rs = stmt.executeQuery("select * from emp");
			
			PreparedStatement deptStmt = connection.prepareStatement("select * from dept where deptno=?");
			PreparedStatement predstmt = connection.prepareStatement("update emp set commission=? where empno=?");
			
			while ( rs.next()) {
				
				int deptno = rs.getInt("deptno");//部門編號
				deptStmt.setInt(1, deptno);
				ResultSet deptRs = deptStmt.executeQuery();//利用另外的sql查詢部門的資訊
				if ( deptRs.next()) {
					String location = deptRs.getString("location");//取得位置
					if ( location.equals("台北")) {//只處理台北的員工
						int empno = rs.getInt("empno");//shift alt + l 
						BigDecimal commission = rs.getBigDecimal("commission");
						System.out.println(empno);
						BigDecimal newCommission =  commission.add(new BigDecimal(100));//commission + 100
						predstmt.setBigDecimal(1, newCommission);//設定第一個問號
						predstmt.setInt(2, empno);//設定第二個問號
						predstmt.executeUpdate();
						predstmt.clearParameters();
					}
				}				
			}
			
			
			
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
	}

}



