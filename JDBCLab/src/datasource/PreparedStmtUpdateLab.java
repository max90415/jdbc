package datasource;

import java.math.BigDecimal;
import java.sql.*;

import org.apache.commons.dbcp2.BasicDataSource;

public class PreparedStmtUpdateLab {

	public static void main(String[] args) {
		
		BasicDataSource ds = new BasicDataSource();//apache dbcp
		ds.setDriverClassName("oracle.jdbc.OracleDriver");
		ds.setUrl("jdbc:oracle:thin:@//localhost:1521/xepdb1");
		ds.setUsername("scott");
		ds.setPassword("tiger");
		ds.setMaxTotal(50);//設定pool中最多可以有多少個connection
		ds.setMaxIdle(50);//最多多少是處於idle狀態
		
		// jdbc
		try (   
				//BasicDataSource ds1 = ds; //小技巧
				Connection connection = ds.getConnection();
				
				) {
			
			System.out.println(connection.getAutoCommit());
			connection.setAutoCommit(false);//begin 
			try { //ORACLE, JDBC( ),, spring(transaction) 
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
							BigDecimal commission = rs.getBigDecimal("commission");
							System.out.println(empno);
							//BigDecimal newCommission =  commission.add(one_hundred);//commission + 100
							commission =  commission.add(one_hundred);//commission + 100
							predstmt.setBigDecimal(1, commission);//設定第一個問號
							predstmt.setInt(2, empno);//設定第二個問號
							predstmt.addBatch();//加到批次, data fix					
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
				e.printStackTrace();
				connection.rollback();
			}
			
			
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		
		
	}

}


