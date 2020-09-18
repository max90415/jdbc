package batchUpdate;

import java.sql.*;

public class Statement_BatchUpdate {

    public static void main(String[] args) {


        try (Connection conn = DriverManager.getConnection(
                "jdbc:oracle:thin:@//localhost:1521/xepdb1", "scott", "tiger");
             Statement stmt = conn.createStatement()
        ) {
            conn.setAutoCommit(false);
            stmt.addBatch("insert into emp (empno,ename,salary,job,hiredate,deptno,commission) values (emp_seq.nextval,'john',100,'sales',to_date('1980-11-1','yyyy-MM-dd'),30,0.0)");
            stmt.addBatch("insert into emp (empno,ename,salary,job,hiredate,deptno,commission) values (emp_seq.nextval,'Mary',100,'sales',to_date('1980-11-1','yyyy-MM-dd'),30,0.0)");
            stmt.addBatch("select * FROM EMP");//執行select會發生BatchUpdateException,Oracle server第三筆執行失敗,第四筆不會執行
            stmt.addBatch("insert into emp (empno,ename,salary,job,hiredate,deptno,commission) values (emp_seq.nextval,'Ken',100,'sales',to_date('1980-11-1','yyyy-MM-dd'),30,0.0)");
            int[] updated = stmt.executeBatch();

            Statement_BatchUpdate.printResult(updated);
            stmt.clearBatch();
            conn.commit();
        } catch (BatchUpdateException be) {
            be.printStackTrace();
            int[] updated = be.getUpdateCounts();
            Statement_BatchUpdate.printResult(updated);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void printResult(int[] result) {
        for (int i = 0; i < result.length; i++) {
            if (result[i] == Statement.SUCCESS_NO_INFO) {
                System.out.println("第 " + (i + 1) + " SQL執行結果成功,無回傳筆數");
            } else if (result[i] == Statement.EXECUTE_FAILED) {
                System.out.println("第 " + (i + 1) + " SQL執行結果失敗");
            } else {
                System.out.println("第 " + (i + 1) + " SQL執行結果,筆數 =" + result[i]);
            }
        }
    }

}
