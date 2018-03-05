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
import ch.howard.frame.util.EhcacheUtil;
import ch.howard.frame.web.IndexAction;
import ch.howard.rbac.dao.DeptDAO;
import ch.howard.rbac.dao.StaffDAO;
import ch.howard.rbac.model.Department;
import ch.howard.rbac.model.Role;
import ch.howard.rbac.model.Staff;
import net.sf.ehcache.Cache;
import net.sf.ehcache.Element;

@Service
public class StaffService {
	
	private static final transient Logger log = LoggerFactory.getLogger(StaffService.class);
	private transient Map<String,Object> outMap = new HashMap<String, Object>();
	
	
	@Autowired
	private StaffDAO staffDao;
	
	/**
	 * 根据staffid来更新员工邮箱，手机，地址信息
	 * 
	 * @param inMap
	 * @return
	 */
	
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
	
	/**
	 * 员工分页排序查询功能
	 * 
	 * @param inMap
	 * @return
	 */
	
	public Map<String, Object> queryStaffByDeptAndPageable(Map<String, Object> inMap) {
		log.info("执行StaffService.queryStaffByDeptAndPageable");
		Page<Staff> pageStaff;
		String did = (String) inMap.get("deptId");
		Integer page = (Integer) inMap.get("page");
		Sort sort = new Sort(Direction.ASC, "id");
		Pageable pageable = new PageRequest(page, 5, sort);
		log.info("对staff进行分页，根据id排序操作");
		Cache cache = EhcacheUtil.getCache("resourceCache");
		Element element = cache.get("staffs" + page + did);
		if(element == null) {
			Department dept = new Department();
			dept.setId(Integer.valueOf(did));
			pageStaff = staffDao.findByDepartment(pageable, dept);
			cache.put(new Element("staffs" + page + did, pageStaff));
			log.info("存储在缓存Ehcache中:" + pageStaff);
		}else {
			pageStaff = (Page<Staff>) element.getObjectValue();
			log.info("从缓存Ehcache中获取" + element.getObjectKey());
		}
		outMap.put("staffs", pageStaff.getContent());
		outMap.put("totalPages", pageStaff.getTotalPages());
		return outMap;
	}
	
}
