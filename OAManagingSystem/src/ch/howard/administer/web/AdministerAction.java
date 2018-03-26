package ch.howard.administer.web;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import ch.howard.administer.model.Daily;
import ch.howard.administer.service.DailyService;
import ch.howard.frame.web.BaseAction;
import ch.howard.rbac.model.Department;
import ch.howard.rbac.service.DeptService;

@Controller
public class AdministerAction extends BaseAction {
	private static final transient Logger log = LoggerFactory.getLogger(AdministerAction.class);

	@Autowired
	private DeptService deptService;
	@Autowired
	private DailyService dailyService;
	
	
	private Iterable<Department> depts;
	
	private List<Department> departments;
	
	private List<Daily> dailys;
	
	private int totalPages;
	
	
	public Iterable<Department> getDepts() {
		return depts;
	}
	
	public List<Department> getDepartments() {
		return departments;
	}
	
	public List<Daily> getDailys() {
		return dailys;
	}

	public int getTotalPages() {
		return totalPages;
	}

	@Override
	public String execute() throws Exception {
		//获取所有资源值
		initMethod();
		return "success";
	}
	
	
	public String staffExecute() {
		//获取所有总部门
		depts = deptService.queryAllDept();
		departments = new ArrayList<Department>();
		//获取所有子部门
		deptService.allDept(depts, departments);
		return "staff";
	}
	
	
	public String recruitExecute() {
		
		//获取所有总部门
		depts = deptService.queryAllDept();
		departments = new ArrayList<Department>();
		//获取所有子部门
		deptService.allDept(depts, departments);
		
		return "recruit";
	}
	
	public String dailyExecute() {
		Map<String, Object> inMap = new HashMap<String, Object>();
		inMap.put("page", 0);
		inMap = dailyService.getDailyPage(inMap);
		dailys = (List<Daily>) inMap.getOrDefault("dailys", new ArrayList<Daily>());
		totalPages = (int) inMap.get("totalPages");
		return "daily";
	}
	
	public String fireExecute() {
		//获取所有总部门
		depts = deptService.queryAllDept();
		departments = new ArrayList<Department>();
		//获取所有子部门
		deptService.allDept(depts, departments);
		
		
		return "fire";
	}
	
}
