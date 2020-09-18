package metadata;

import java.sql.*;

public class DBMetadata {

	public static void main(String[] args) {
		
		try (Connection connection = DriverManager.getConnection("jdbc:oracle:thin:@//localhost:1521/xepdb1", "scott",
				"tiger");) {
			
			DatabaseMetaData dbmd = connection.getMetaData();
			System.out.println(dbmd.getDatabaseProductName());//資料庫廠商名稱
			System.out.println(dbmd.getDatabaseProductVersion());//版本號碼, 18c, expr
			//driver
			System.out.println(dbmd.getDriverName());
			System.out.println(dbmd.getDriverVersion());
			
			ResultSet rs = dbmd.getTables("xepdb1", "SCOTT", null, new String[] {"TABLE","VIEW"});
			//String[] types = {"TABLE"};
			//ResultSet rs = dbmd.getTables("xepdb1", "SCOTT", null, types);
			
			while ( rs.next()) {
				String name = rs.getString("table_name");
				System.out.println(name+","+rs.getString("table_type"));
				
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}
		
	}

}
