package ch.howard.frame.service;


import java.util.HashMap;
import java.util.Map;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import ch.howard.commonClass.BaseJunit4Test;
import ch.howard.frame.dao.UserDAO;

public class UserServiceTest extends BaseJunit4Test {
	
	@Autowired
	private UserService userService;
	@Autowired
	private	UserDAO userDao;
	
	
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

}
