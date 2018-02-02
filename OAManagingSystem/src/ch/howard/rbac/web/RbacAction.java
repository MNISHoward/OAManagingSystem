package ch.howard.rbac.web;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import com.opensymphony.xwork2.ActionSupport;

import ch.howard.frame.model.Department;
import ch.howard.frame.model.Resource;
import ch.howard.frame.service.DeptService;
import ch.howard.frame.service.IndexService;
import ch.howard.frame.service.MenuService;
import ch.howard.frame.service.ResourceService;

@Controller
public class RbacAction extends ActionSupport{
	
	private static final transient Logger log = LoggerFactory.getLogger(RbacAction.class);
	
	private Iterable<Resource> resources;
	private Resource resource;
	private Integer rid;
	private Integer mid;
	private Iterable<Department> depts;
	
	@Autowired
	private ResourceService resourceService;
	@Autowired
	private IndexService indexService;
	@Autowired
	private MenuService menuService;
	@Autowired
	private DeptService deptService;
	
	public void setMid(Integer mid) {
		this.mid = mid;
	}
	

	public void setRid(Integer rid) {
		this.rid = rid;
	}

	public Resource getResource() {
		return resource;
	}

	public Iterable<Resource> getResources() {
		return resources;
	}
	
	public Iterable<Department> getDepts() {
		return depts;
	}

	@Override
	public String execute() throws Exception {
		log.info("执行RabcAction.execute");
		//验证session中user项是否有值
		if(!indexService.verifyUserInSession()) {
			return "login";
		}
		
		//获取资源iterable
		resources = resourceService.queryResource();
		
		//获取资源中的菜单项
		if(rid != null) {
			resource = resourceService.queryResourceById(rid, resources);
		}
		
		//跳转到菜单对应的方法
		if(mid != null && resource != null) {
			String method = menuService.queryMenuMethod(mid, resource.getMenus());
			switch (method) {
			case "userExecute":
				//值栈里面存在mid的值，导致访问控制跳到对应方法
				mid = null;
				return userExecute();
			default:
				break;
			}
		}
		
		return "success";
	}
	
	public String userExecute() {
		log.info("执行RbacAction.userExecute");
		
		
		//获取部门
		depts = (deptService.queryAllDept());
		return "user";
	}




	
	
	
}
