package statement;

import java.sql.*;

public class CallableStmtNoParameter
{

	public static void main(String[] args) {
		

		try (Connection conn = DriverManager
				.getConnection(
						"jdbc:oracle:thin:@//localhost:1521/xepdb1",
						"scott", "tiger");
			 CallableStatement stmt = conn.prepareCall("{ call QueryEmployee}");
		) {
			stmt.execute();
			boolean hasResult = stmt.getMoreResults();
			if ( hasResult) {
				ResultSet rs = stmt.getResultSet();
				//SQL Developer: exec QueryEmployee;

				//Procedure QueryEmployee沒有回傳東西，只是單純select from emp
				while (rs.next()) {
					int empno = rs.getInt("empno");
					String name = rs.getString("ename");
					System.out.println("empno=" + empno + ",name=" + name);
				}
			}
			System.out.println("CallableStmtNoParameter finished");
		} catch (SQLException e) {
			e.printStackTrace();
		}

	}

}
