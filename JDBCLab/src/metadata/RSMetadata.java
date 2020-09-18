package metadata;

import java.sql.*;

public class RSMetadata {

	public static void main(String[] args) {
		
		try (Connection connection = DriverManager.getConnection("jdbc:oracle:thin:@//localhost:1521/xepdb1", "scott",
				"tiger");) {

			Statement stmt = connection.createStatement();
			ResultSet rs = stmt.executeQuery("select * from emp");
			
			ResultSetMetaData rsMD = rs.getMetaData();
			for( int i=1 ; i <= rsMD.getColumnCount(); i++) {
				String columnName = rsMD.getColumnName(i);
				System.out.println(columnName);
				
				System.out.println("type="+rsMD.getColumnTypeName(i));
				System.out.println("size="+rsMD.getColumnDisplaySize(i));
				
				//測試是否能為空值null
				int nullable = rsMD.isNullable(i);
				if ( nullable == ResultSetMetaData.columnNullable ) {
					System.out.println("可以是空值");
				}else if ( nullable == ResultSetMetaData.columnNoNulls ) {
					System.out.println("不可以是空值");
				}else if ( nullable == ResultSetMetaData.columnNullableUnknown ) {
					System.out.println("Unknow");
				}

				
			}
		    
		} catch (SQLException e) {
			e.printStackTrace();
		
		System.out.println(e.getMessage());
		System.out.println(e.getErrorCode());
		
		
}
	}

}