package ch.howard.frame.service;

import java.util.HashMap;
import java.util.Map;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import ch.howard.commonClass.BaseJunit4Test;
import ch.howard.rbac.service.StaffService;

public class StaffServiceTest extends BaseJunit4Test {

	@Autowired
	StaffService staffService;
	
	@Test
	public void userRegister() {
		Map<String, Object> map = new HashMap<>();
		map.put("email", "ch@howard.com");
		map.put("address", "guangdongprovinceguangzhoucity");
		map.put("phone", "10000");
		map.put("staffId", "1");
		staffService.updateStaffEPA(map);
		
	}

}
