package ch.howard.frame.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import ch.howard.commonClass.BaseJunit4Test;
import ch.howard.rbac.dao.StaffDAO;
import ch.howard.rbac.model.Department;
import ch.howard.rbac.model.Staff;
import ch.howard.rbac.service.StaffService;

public class StaffServiceTest extends BaseJunit4Test {

	@Autowired
	StaffService staffService;
	
	@Autowired
	StaffDAO staffDao;
	
	@Test
	public void userRegister() {
		Map<String, Object> map = new HashMap<>();
		map.put("email", "ch@howard.com");
		map.put("address", "guangdongprovinceguangzhoucity");
		map.put("phone", "10000");
		map.put("staffId", "1");
		staffService.updateStaffEPA(map);
	}
	
	@Test
	public void test() {
		List<Staff> staffs = new ArrayList<Staff>();
		Department dept = new Department();
		dept.setId(8);
//		for(int i = 0; i < 20; i++) {
//			double num = Math.floor(Math.random() * 10000);
//			
//			int num1 = (int) num;
//			int sex = (int) Math.round(Math.random());
//			Staff staff = new Staff(null, "Account Robot" + num1, "财务机器人" + num1 + "号", sex, new Date(), "ch" + num1 + "@howard.com", "1354862" + num1, "guangdongguangzhou" + num1, 4000.00, "财务员工", dept);
//			staffs.add(staff);
//		}
		
		for(int i = 0; i < 20; i++) {
			double num = Math.floor(Math.random() * 10000);
			
			int num1 = (int) num;
			int sex = (int) Math.round(Math.random());
			Staff staff = new Staff(null, "Administrator Robot" + num1, "行政机器人" + num1 + "号", sex, new Date(), "ch" + num1 + "@howard.com", "1584862" + num1, "guangdongguangzhou" + num1, 5000.00, "行政员工", dept);
			staffs.add(staff);
		}
		staffDao.save(staffs);
	}

}
