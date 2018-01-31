package ch.howard.frame.service;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.transaction.Transactional;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.session.Session;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ch.howard.frame.dao.DeptDAO;
import ch.howard.frame.dao.StaffDAO;
import ch.howard.frame.model.Staff;
import ch.howard.frame.shiro.service.UserLoginService;
import ch.howard.frame.web.IndexAction;

@Service
public class StaffService {
	
	private static final transient Logger log = LoggerFactory.getLogger(StaffService.class);
	private transient Map<String,Object> outMap = new HashMap<String, Object>();
	
	
	@Autowired
	private StaffDAO staffDao;
	@Autowired
	private DeptDAO deptDao;
	
	@Transactional
	public void insertStaff() {
		Staff staff = new Staff(null,"ch","总经理",1,new Date(), "ch@howard.com","10000","guangzhoucity",100000.10,deptDao.findOne(1));
		staffDao.save(staff);
	}
	
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
	
}
