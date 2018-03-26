package ch.howard.rbac.web;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import ch.howard.frame.model.Menu;
import ch.howard.frame.model.Resource;
import ch.howard.frame.model.User;
import ch.howard.frame.service.MenuService;
import ch.howard.frame.service.ResourceService;
import ch.howard.frame.service.UserService;
import ch.howard.frame.web.BaseAction;
import ch.howard.rbac.model.Department;
import ch.howard.rbac.model.Role;
import ch.howard.rbac.service.DeptService;
import ch.howard.rbac.service.RoleService;

@Controller
public class RbacAction extends BaseAction{
	
	private static final transient Logger log = LoggerFactory.getLogger(RbacAction.class);
	
	private Iterable<Department> depts;
	private Iterable<Role> roles;
	private Iterable<User> users;
	private Iterable<Menu> menus;
	
	@Autowired
	private MenuService menuService;
	@Autowired
	private DeptService deptService;
	@Autowired
	private RoleService roleService;
	@Autowired
	private UserService userService;
	

	
	public Iterable<Department> getDepts() {
		return depts;
	}
	
	public Iterable<Role> getRoles() {
		return roles;
	}
	
	public Iterable<User> getUsers() {
		return users;
	}

	public Iterable<Menu> getMenus() {
		return menus;
	}

	@Override
	public String execute() throws Exception {
		
		//获取所有资源值
		initMethod();
		return "success";
	}
	
	/**
	 * 用户管理
	 * 
	 * @return
	 */
	public String userExecute() {
		
		//获取部门
		depts = deptService.queryAllDept();
		//获取角色
		roles = roleService.getAllRole();
		return "user";
	}
	
	/**
	 * 角色管理
	 * 
	 * @return
	 */
	public String roleExecute() {
		//获取角色
		roles = roleService.getAllRole();
		users = userService.queryAllUserNoStaffAndRoles();
		menus = menuService.queryAllMenusNoResourceAndRoles();
		return "role";
	}
	
	/**
	 * 菜单管理
	 * @return
	 */
	public String menuExecute() {
		menus = menuService.queryAllMenusNoResourceAndRoles();
		resources = resourceService.queryResource();
		return "menu";
	}
	
	/**
	 * 资源管理
	 * @return
	 */
	public String resourceExecute() {
		resources = resourceService.queryResource();
		return "resource";
	}
	
	
}
