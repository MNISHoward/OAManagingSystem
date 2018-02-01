package ch.howard.frame.shiro.service.test;


import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import ch.howard.commonClass.BaseJunit4Test;
import ch.howard.frame.shiro.service.UserLoginService;

public class UserLoginServiceTest extends BaseJunit4Test {

	@Resource
	UserLoginService userLoginService;
	
	@Test
	public void test() {
		Map<String,Object> map = new HashMap<>();
		map.put("username", "");
		map.put("password", "");
		userLoginService.userlogin(map);
	}

}
