package batchupdate;

import java.math.BigDecimal;
import java.sql.*;

public class prestmtbatchupdate {

	public static void main(String[] args) {
		// jdbc
		try (Connection connection = DriverManager.getConnection("jdbc:oracle:thin:@//localhost:1521/xepdb1", "scott",
				"tiger");) {
			System.out.println(connection.getAutoCommit());
			connection.setAutoCommit(false);//begin 
			try { //ORACLE, JDBC
					Statement stmt = connection.createStatement();
					ResultSet rs = stmt.executeQuery("select * from emp, dept where emp.deptno=dept.deptno");
					PreparedStatement predstmt = connection.prepareStatement("update emp set commission=? where empno=?");
					BigDecimal one_hundred = new BigDecimal(100);
					int counter = 0;
					//for(  ; rs.next() ;  ) {
					while ( rs.next() ) {
						String location = rs.getString("location");
						if ( location.equals("台北")) {//只處理台北的員工
							int empno = rs.getInt("empno");//shift alt + l 
							BigDecimal commission = rs.getBigDecimal("commission1");
							System.out.println(empno);
							//BigDecimal newCommission =  commission.add(one_hundred);//commission + 100
							commission =  commission.add(one_hundred);//commission + 100
							predstmt.setBigDecimal(1, commission);//設定第一個問號
							predstmt.setInt(2, empno);//設定第二個問號
							predstmt.addBatch();//加到批次					
							predstmt.clearParameters();
							//50筆送批次一次
							counter ++;
							if ( counter % 50 == 0  ) {
								predstmt.executeBatch();
								//predstmt.executeLargeBatch();//21億
								predstmt.clearBatch();//清除批次
							}
						}
					}
					predstmt.executeBatch();//送出批次中的更新
					predstmt.clearBatch();//清除批次
					
					connection.commit(); //commit
			}catch (Exception e) {
				connection.rollback();
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
	}

}












