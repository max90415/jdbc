import java.sql.*;


public class HelloJDBC {

	public static void main(String[] args) {
		
		//查詢SQL Server連線數，確認沒有leaking: select sid,serial#,osuser,username,program,machine,status,logon_time from v$session order by logon_time desc
		// kill session ALTER SYSTEM KILL SESSION 'sid,serial#'
		/*
		try {
			Class.forName("oracle.jdbc.OracleDriver");
		} catch (ClassNotFoundException e1) {
			e1.printStackTrace();
		}

		try {
			Driver driver = new oracle.jdbc.driver.OracleDriver();
			DriverManager.registerDriver(driver);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		  */

		Connection conn = null;
		Statement stmt  = null;
		ResultSet rs = null;
		
		try {
			conn = DriverManager.getConnection("jdbc:oracle:thin:@//localhost:1521/xepdb1","scott","tiger");
			stmt  = conn.createStatement();
			rs =  stmt.executeQuery("select * from emp");
			while ( rs.next()){
				int empno = rs.getInt("empno");
				String name = rs.getString("ename");
				System.out.println("empno="+empno+",name="+name);
			}
			System.out.println("query finished");
		} catch (SQLException e) {
			e.printStackTrace();
		}finally{
			try {
				if ( rs != null){
					rs.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
			try {
				if ( stmt != null){
					stmt.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
			try {
				if ( conn!=null){
					conn.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}

		}
	}

}
