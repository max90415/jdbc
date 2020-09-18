package resultset;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class ResultSetCursorInSensitive {

    public static void main(String[] args) {

        try (Connection conn = DriverManager.getConnection("jdbc:oracle:thin:@//localhost:1521/xepdb1", "scott", "tiger");
             Statement stmt = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);//預設值，等同於conn.createStatement()
             ResultSet rs = stmt.executeQuery("select * from emp order by empno")) {
            for ( int i=0 ; i < 10 ;i++){
                rs.next();
                System.out.println("empno=" + rs.getInt("empno") + ",name=" + rs.getString("ename"));
            }
            //中斷點設在rs.next()
            rs.next();
            System.out.println("empno=" + rs.getInt("empno") + ",name=" + rs.getString("ename"));
            System.out.println("ResultSetCursorInSensitive finished");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}
