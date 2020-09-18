package orm;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;// x : extension
import org.apache.commons.dbcp2.BasicDataSource;

public class EmployeeJDBCDAO {
	
	public EmployeeJDBCDAO() {
		
	}
	
	private DataSource dataSource;
	public DataSource getDataSource() {
		//lazy init
		if ( dataSource == null ) {
			 BasicDataSource ds = new BasicDataSource();
			 ds.setDriverClassName("oracle.jdbc.OracleDriver");
			 ds.setUrl("jdbc:oracle:thin:@//localhost:1521/xepdb1");
			 ds.setUsername("scott");
			 ds.setPassword("tiger");
			 ds.setMaxTotal(50); //設定最多connection上線,超過使用量必須等待
			 ds.setMaxIdle(50);
			 dataSource = ds;//把BasicDataSource放在屬性上
		}
		return dataSource;
	}

	//java.util.List
	//取得所有的emp table的資料，轉換成Employee物件，回傳List因為會有多筆
	public List<Employee> listEmployees() {
		
		//select * from emp -> Employee
		List<Employee> list = new ArrayList<>();
		try (   Connection connection = getDataSource().getConnection();
				Statement stmt = connection.createStatement();
				ResultSet rs = stmt.executeQuery("select * from emp"); )  {
			while( rs.next() ) {
				//把每一筆資料轉成Employee物件
				Employee emp = new Employee();
				String name = rs.getString("ename");
				emp.setName(name);
				int empno = rs.getInt("empno");
				emp.setEmpNO(empno);
				//job 
				emp.setJob(rs.getString("job"));
				//hiredata
				emp.setHiredate(rs.getDate("hiredate"));
				//commission
				emp.setCommission(rs.getBigDecimal("commission"));
				emp.setSalary(rs.getBigDecimal("salary"));
				list.add(emp);
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return list;
	}
	
	//更新Employee物件回資料庫
	public void updateEmployee(Employee emp) {
		
		try (Connection connection = getDataSource().getConnection();) {
			
			PreparedStatement pstmt = 
			connection.prepareStatement("update emp set ename=?,job=?,hiredate=?,salary=?,commission=? where empno=?");
			pstmt.setString(1, emp.getName());
			//1.留意？的順序
			//2.hiredate -> java.sql.Date
			pstmt.setString(2, emp.getJob());
			pstmt.setDate(3, new java.sql.Date(emp.getHiredate().getTime()));
			pstmt.setBigDecimal(4, emp.getSalary());
			pstmt.setBigDecimal(5, emp.getCommission());
			pstmt.setInt(6, emp.getEmpNO());
			pstmt.executeUpdate();
			pstmt.clearParameters();
			
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
}


