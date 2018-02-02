package ch.howard.rbac;

import java.util.HashSet;

import javax.sql.DataSource;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import ch.howard.commonClass.BaseJunit4Test;
import ch.howard.frame.dao.DeptDAO;
import ch.howard.frame.dao.MenuDAO;
import ch.howard.frame.dao.ResourceDAO;
import ch.howard.frame.dao.UserDAO;
import ch.howard.frame.model.Menu;
import ch.howard.frame.model.Resource;
import ch.howard.frame.model.User;
import ch.howard.rbac.dao.RoleDAO;
import ch.howard.rbac.model.Role;

public class InsertDataTest extends BaseJunit4Test {

	@Autowired
	ResourceDAO resourceDao;
	
	@Autowired
	DataSource dataSource;
	
	@Autowired
	DeptDAO deptDao;
	
	@Autowired
	MenuDAO menuDao;
	
	@Autowired
	RoleDAO roleDao;
	
	@Autowired
	private	UserDAO userDao;
	
	@Test
	public void roleData() {
		User u = userDao.findById(1);
		Role r = new Role("admin", "系统管理员", 0);
		r.addUser(u);
		u.addRole(r);
		r.addMenu(menuDao.findOne(1));
		roleDao.save(r);
		userDao.save(u);
	}
	
	@Test
	public void MenuData() {
		Menu m = new Menu("userManage", "用户管理", "/web/jsp/rbac/user.jsp", 0, resourceDao.findOne(1));
		menuDao.save(m);
	}
	
	@Test
	public void resourceData() {
		Resource resource = new Resource("rbac", "基于角色的权限访问控制", "/rbac.do", 0);
		resourceDao.save(resource);
	}

}
