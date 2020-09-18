import java.sql.*;

public class sss {

 public static void main(String[] args) {
  // TODO Auto-generated method stub

  try (Connection connection = DriverManager.getConnection("jdbc:oracle:thin:@//localhost:1521/xepdb1", "scott",
    "tiger");) {
   Statement stmt = connection.createStatement();
   ResultSet rs = stmt.executeQuery("select ename, empno from emp");
   
   while (rs.next()) {
    String ename = rs.getString("ename");
    if (ename != (null) ) {
    int empno = rs.getInt("empno");
    System.out.println(ename);
    System.out.println(empno);
    }
   }
   
  } catch (SQLException e) {
   e.printStackTrace();
  }

  
 }

}