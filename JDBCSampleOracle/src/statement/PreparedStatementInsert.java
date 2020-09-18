package statement;

import java.math.BigDecimal;
import java.sql.*;
import java.util.GregorianCalendar;

public class PreparedStatementInsert {

    public static void main(String[] args) {

        //
        try ( Connection conn = DriverManager.getConnection(
                "jdbc:oracle:thin:@//localhost:1521/xepdb1",
                "scott", "tiger");
              PreparedStatement stmt = conn.prepareStatement("insert into emp (empno,ename,salary,job,hiredate,deptno,commission) " +
                      "values (?,?,?,?,?,?,?)");
        ) {

            stmt.setInt(1,9999);
            stmt.setString(2,"Mary");
            stmt.setBigDecimal(3,new BigDecimal("2000"));
            stmt.setString(4,"Manager");
            GregorianCalendar calendar = new GregorianCalendar(2020,2,10);
            Date date = new Date(calendar.getTimeInMillis());
            stmt.setDate(5,date);
            stmt.setInt(6,30);
            stmt.setBigDecimal(7,new BigDecimal(0.0));
            stmt.executeUpdate();

            //如果不執行23,24兩行,則會延用18行設定的22000
            stmt.clearParameters();

            System.out.println("query finished");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}
