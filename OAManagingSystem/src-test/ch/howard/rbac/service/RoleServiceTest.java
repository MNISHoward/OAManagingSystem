/**
 * 
 */
package ch.howard.rbac.service;

import java.util.HashMap;
import java.util.Map;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import ch.howard.commonClass.BaseJunit4Test;

/**
 * @author Administrator
 *
 */
public class RoleServiceTest extends BaseJunit4Test{

	@Autowired
	RoleService roleService;
	
	@Test
	public void test() {
		System.out.println(roleService.getAllRole());
		
	}
	
	

}
