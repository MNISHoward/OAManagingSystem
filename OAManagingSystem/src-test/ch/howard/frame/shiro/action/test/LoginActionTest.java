package ch.howard.frame.shiro.action.test;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.opensymphony.xwork2.ActionProxy;

import ch.howard.commonClass.StrutsSpringTest;
import ch.howard.frame.shiro.action.LoginAction;

public class LoginActionTest extends StrutsSpringTest {

	@Autowired
	LoginAction loginAction;
	
	@Test
	public void test() throws Exception {
		ActionProxy proxy = getActionProxy("/login"); //对应action url地址 获取 action代理
		LoginAction action = (LoginAction) proxy.getAction(); // 真正的action对象
		action.execute();
	}

}
