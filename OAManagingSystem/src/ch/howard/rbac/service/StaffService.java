package ch.howard.rbac.service;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
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

import ch.howard.frame.service.UserService;
import ch.howard.frame.util.EhcacheUtil;
import ch.howard.frame.util.Util;
import ch.howard.rbac.dao.StaffDAO;
import ch.howard.rbac.model.Department;
import ch.howard.rbac.model.Staff;
import net.sf.ehcache.Cache;
import net.sf.ehcache.Element;

@Service
public class StaffService {
	
	private static final transient Logger log = LoggerFactory.getLogger(StaffService.class);
	private transient Map<String,Object> outMap = new HashMap<String, Object>();
	
	
	@Autowired
	private StaffDAO staffDao;
	
	@Autowired
	private UserService userService;
	
	/**
	 * 根据sid 查找员工
	 * @param inMap
	 * @return
	 */
	public Map<String, Object> findStaffById(Map<String, Object> inMap) {
		String sid = (String) inMap.get("sid");
		Staff staff = staffDao.findOne(Integer.valueOf(sid));
		outMap.put("staff", staff);
		return outMap;
	}
	
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
		Element element2 = cache.get("staffsUpdate");
		if(element == null || (element2 != null && (Boolean)element2.getObjectValue() == true)) {
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
	
	/**
	 * 通过id来更新staff
	 * @param inMap
	 * @return
	 * @throws Exception
	 */
	@Transactional
	public Map<String, Object> updateStaffById(Map<String, Object> inMap) throws Exception {
		//转换时间格式
		SimpleDateFormat time=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date date = time.parse((String)inMap.get("birthday"));
		inMap.put("birthday", date);
		
		//部门号重新赋值
		String did = (String)inMap.get("departmentId");
		Department dept = new Department();
		dept.setId(Integer.valueOf(did));
		Staff staff = (Staff) Util.mapToObject(inMap, Staff.class);
		staff.setDepartment(dept);
		staffDao.save(staff);
		
		
		motifyCache();
		outMap.put("message", "更新成功");
		return outMap;
	}
	
	/**
	 * 新增一个新的staff, 与更新不同就是 更新 需要给用户一个通知
	 * @param inMap
	 * @return
	 * @throws Exception 
	 */
	
	@Transactional
	public Map<String, Object> insertNewStaff(Map<String, Object> inMap) throws Exception {
		SimpleDateFormat time=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date date = time.parse((String)inMap.get("birthday"));
		inMap.put("birthday", date);
		
		//部门号重新赋值
		String did = (String)inMap.get("departmentId");
		Department dept = new Department();
		dept.setId(Integer.valueOf(did));
		Staff staff = (Staff) Util.mapToObject(inMap, Staff.class);
		staff.setDepartment(dept);
		staffDao.save(staff);
		
		motifyCache();
		outMap.put("message", "新增成功");
		return outMap;
	}
	/**
	 * 通过id和name来查询staff
	 * @param inMap
	 * @return
	 */
	public Map<String, Object> findStaffwithIdAndName(Map<String, Object> inMap) {
		String param = (String) inMap.get("param");
		Integer id;
		String name;
		try {
			id = Integer.valueOf(param);
			name = "unknown";
		}catch (NumberFormatException e) {
			id = null;
			name = param;
		}
		
		outMap.put("staffs", staffDao.findByIdOrNameContaining(id, name));
		return outMap;
	}
	
	/**
	 * 解雇员工
	 * @param inMap
	 * @return
	 */
	@Transactional
	public Map<String, Object> deleteStaffById(Map<String, Object> inMap) {
		String id = (String) inMap.get("id");
		
		
		//删除员工对应的用户
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("sid", id);
		userService.deleteUserByStaff(map);
		
		staffDao.delete(Integer.valueOf(id));
		outMap.put("message", "解雇成功");
		motifyCache();
		return outMap;
	}
	
	/**
	 * 更改缓存标志
	 */
	public void motifyCache() {
		//更新缓存
		Cache cache = EhcacheUtil.getCache("resourceCache");
		log.info("staff缓存将永远失效");
		cache.put(new Element("staffsUpdate", Boolean.TRUE));
		log.info("department缓存需要更新");
		cache.put(new Element("deptUpdate", Boolean.TRUE));
		
	}
	 
}
