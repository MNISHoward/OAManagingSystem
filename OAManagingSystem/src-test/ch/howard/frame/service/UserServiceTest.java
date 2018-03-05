package ch.howard.frame.service;


import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import ch.howard.commonClass.BaseJunit4Test;
import ch.howard.frame.dao.UserDAO;
import ch.howard.rbac.dao.RoleDAO;
import ch.howard.rbac.model.Role;

public class UserServiceTest extends BaseJunit4Test {
	
	@Autowired
	private UserService userService;
	@Autowired
	private	UserDAO userDao;
	@Autowired
	private RoleDAO roleDao;
	
	@Test
	public void test2() {
		//Map<String,Object> map = new HashMap<String, Object>();
		//map.put("sid", "1");
		//Map<String, Object> queryUIRByStaffid = userService.queryUIRByStaffid(map);
		//List<Role> findByUserStaffId = roleDao.findByUserStaffId(1);
		//System.out.println(findByUserStaffId.get(0).getTitleName());
	}
	
	@Test
	public void updateUserPass() {
		Map<String, Object> map = new HashMap<>();
		map.put("oldPassword", "123");
		map.put("newPassword", "1234");
		map.put("newRePassword", "1234");
		map.put("userId", "1");
		userService.updateUserPass(map);
	}
	
	@Test
	public void findUser() {
		userDao.findById(1);
	}
	
	@Test
	public void test1() {
		Map<String, Object> inMap = new HashMap<>();
		Integer[] ints = {1};
		List<Integer> asList = Arrays.asList(ints);
		inMap.put("roles", asList);
		userService.updateUserRoleByStaff(inMap);
	}

}
