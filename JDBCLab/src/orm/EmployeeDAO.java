package orm;

import java.util.List;

public interface EmployeeDAO {

	//java.util.List
	//取得所有的emp table的資料，轉換成Employee物件，回傳List因為會有多筆
	List<Employee> listEmployees();

	//更新Employee物件回資料庫
	void updateEmployee(Employee emp);

}