package ch.howard.rbac.service;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.transaction.Transactional;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.session.Session;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;

import ch.howard.frame.shiro.service.UserLoginService;
import ch.howard.frame.web.IndexAction;
import ch.howard.rbac.dao.DeptDAO;
import ch.howard.rbac.dao.StaffDAO;
import ch.howard.rbac.model.Department;
import ch.howard.rbac.model.Staff;

@Service
public class StaffService {
	
	private static final transient Logger log = LoggerFactory.getLogger(StaffService.class);
	private transient Map<String,Object> outMap = new HashMap<String, Object>();
	
	
	@Autowired
	private StaffDAO staffDao;
	@Autowired
	private DeptDAO deptDao;
	
	@Transactional
	public Map<String,Object> updateStaffEPA(Map<String, Object> inMap) {
		log.info("执行StaffService.updateStaff()");
		Session session = SecurityUtils.getSubject().getSession();
		String staffId =  (String) inMap.get("staffId");
		Staff staff = staffDao.findById(Integer.valueOf(staffId));
		String email = (String)inMap.get("email");
		String phone = (String)inMap.get("phone");
		String address = (String)inMap.get("address");
		if(email != null) {
			staff.setEmail(email);
		}
		if(phone != null) {
			staff.setPhone(phone);
		}
		if(address != null) {
			staff.setAddress(address);
		}
		staffDao.updateByIdSetEmailAndPhoneAndAddress(staff.getEmail(), staff.getPhone(), staff.getAddress(), staff.getId());
		session.setAttribute("staff", staff);
		log.info("修改成功: email :" + staff.getEmail() + ", phone :" + staff.getPhone() + ", address :" + staff.getAddress());
		outMap.put("message", "修改成功");
		return outMap;
	}
	
	
	public List<Staff> queryStaffByDeptAndPageable(Map<String, Object> inMap) {
		log.info("执行StaffService.queryStaffByDeptAndPageable");
		String did = (String) inMap.get("deptId");
		Sort sort = new Sort(Direction.ASC, "id");
		Pageable pageable = new PageRequest(0, 7, sort);
		log.info("对staff进行分页，根据id排序操作");
		Integer deptid = (Integer) deptDao.findDeptIdNotStaffs(Integer.valueOf(did));
		Department dept = new Department();
		dept.setId(deptid);
		Page<Staff> pageStaff = staffDao.findByDepartment(pageable, dept);
		List<Staff> staffs = pageStaff.getContent();
		return staffs;
	}
	
}
