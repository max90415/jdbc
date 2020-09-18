package transaction;

import java.math.BigDecimal;
import java.sql.*;

public class TransactionLab {

    public static void main(String[] args) {


        try (Connection conn = DriverManager.getConnection(
                        "jdbc:oracle:thin:@//localhost:1521/xepdb1", "scott", "tiger");
             Statement stmt = conn.createStatement();
             PreparedStatement pstmt = conn.prepareStatement("update emp set commission=? where empno=?");
             ResultSet rs = stmt.executeQuery("select * from emp")
        ) {
            conn.setAutoCommit(false);
            try {
                while (rs.next()) {
                    int empno = rs.getInt("empno");
                    BigDecimal commission = rs.getBigDecimal("commission");
                    commission = commission.add(new BigDecimal(100));
                    System.out.println("Update empno=" + empno + " commission=" + commission);
                    pstmt.clearParameters();
                    pstmt.setBigDecimal(1, commission);
                    pstmt.setInt(2, empno);
                    pstmt.execute();
                }
                if (true) {
                    throw new SQLException();
                }
                conn.commit();
            }catch(Exception ex){
                conn.rollback();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}
