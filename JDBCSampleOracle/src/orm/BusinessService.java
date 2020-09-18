package orm;

import org.apache.commons.dbcp2.BasicDataSource;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;
import java.math.BigDecimal;
import java.sql.*;
import java.util.List;
import java.util.Properties;

/**
 * Created with IntelliJ IDEA.
 * User: vincent
 * Date: 13/8/15
 * Time: 上午11:13
 * To change this template use File | Settings | File Templates.
 */
public class BusinessService {

    public static void main(String[] args) {


        BasicDataSource dataSource = new BasicDataSource();
        dataSource.setDriverClassName("oracle.jdbc.OracleDriver");
        dataSource.setUrl("jdbc:oracle:thin:@//localhost:1521/xepdb1");
        dataSource.setUsername("scott");
        dataSource.setPassword("tiger");
        dataSource.setMaxTotal(50); //設定最多connection上線,超過使用量必須等待
        dataSource.setMaxIdle(50);   //設定最多idle的connection,超過的connection會被執行connection.close()

        try (Connection conn = dataSource.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery("select * from emp ");
             PreparedStatement pstmt = conn.prepareStatement("update emp set commission=? where empno=?")
        ) {
            //試算job=經理commission二千,業務commission一千,行銷commission二百,其餘為0
            while (rs.next()) {
                String job = rs.getString("JOB");
                int empno = rs.getInt("empno");
                if ( job.equals("經理")){
                    pstmt.setBigDecimal(1,new BigDecimal("2000"));
                }else if ( job.equals("業務")){
                    pstmt.setBigDecimal(1,new BigDecimal("1000"));
                }else if ( job.equals("行銷") ){
                    pstmt.setBigDecimal(1,new BigDecimal("200"));
                }else {
                    pstmt.setBigDecimal(1,new BigDecimal("0"));
                }
                pstmt.setInt(2,empno);
                pstmt.executeUpdate();
                pstmt.clearParameters();
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }

        
        /*
        EmployeeJDBCDAO employeeDAO = new EmployeeJDBCDAO();
//        EmployeeDAO employeeDAO = new EmployeeCSVDAO();

        List<Employee> employees = employeeDAO.listEmployees();
        for ( Employee employee: employees){
            if ( employee.getJob().equals("經理")){
                employee.setCommission(new BigDecimal("3000"));
            }else if ( employee.getJob().equals("業務")){
                employee.setCommission(new BigDecimal("2000"));
            }else if ( employee.getJob().equals("行銷")){
                employee.setCommission(new BigDecimal("100"));
            }else {
                employee.setCommission(new BigDecimal("0"));
            }
            employeeDAO.updateEmployee(employee);
        }
    */
        System.out.println("BusinessService finished");
    }
}
