package resultset;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Calendar;
import java.util.GregorianCalendar;

import oracle.net.aso.i;

public class ResultUpdateLab {

	public static void main(String[] args) {
		//jdk7, AutoCloseable
		try (   Connection connection =
				DriverManager.getConnection("jdbc:oracle:thin:@//localhost:1521/xepdb1", "scott", "tiger");
				Statement stmt = connection.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_UPDATABLE);
				ResultSet rs = stmt.executeQuery("select e.* from emp e"); )  {

			/*
			rs.absolute(5);
			rs.updateString("ename", "John");
			rs.updateRow();
			*/
			
			/*
			rs.absolute(5);
			rs.deleteRow();
			*/
			
			
			rs.moveToInsertRow();
			rs.updateInt("empno", 1100); //請從1100開始往後加,第⼆次執⾏請改⽤1101
			//empno是primary key，執⾏第⼆次時不能⽤相同的primary key，否則新增會失敗
			rs.updateString("ename", "小明");
			rs.updateString("job", "工讀生");
			//rs.updateDate()中，需要的是java.sql.Date物件，非java.util.Date
			Calendar calendar = new GregorianCalendar(2020, 0, 1); //2020/1/1
			java.sql.Date date = new java.sql.Date(calendar.getTimeInMillis());
			rs.updateDate("hiredate", date);
			rs.updateLong("salary", 10000);
			rs.updateInt("commission",0);
			rs.updateInt("deptno", 30);
			rs.insertRow();
			
			System.out.println("finish");
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

}