package ch.howard.frame.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.transaction.Transactional;

import org.apache.shiro.crypto.hash.SimpleHash;
import org.apache.shiro.util.ByteSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ch.howard.frame.dao.UserDAO;
import ch.howard.frame.exception.AppException;
import ch.howard.frame.model.User;
import ch.howard.frame.shiro.service.FilterChainDefinitionsService;
import ch.howard.frame.util.EhcacheUtil;
import ch.howard.rbac.dao.RoleDAO;
import ch.howard.rbac.dao.StaffDAO;
import ch.howard.rbac.model.Department;
import ch.howard.rbac.model.Role;
import ch.howard.rbac.model.Staff;
import net.sf.ehcache.Cache;
import net.sf.ehcache.Element;

@Service
public class UserService {
	private static final transient Logger log = LoggerFactory.getLogger(UserService.class);
	private transient Map<String,Object> outMap = new HashMap<String, Object>();
	
	@Autowired
	private UserDAO userDao;
	
	@Autowired
	private RoleDAO roleDao;
	
	@Autowired
	private StaffDAO staffDao;
	
	@Autowired
	private FilterChainDefinitionsService filterChainService;
	
	
	/**
	 * 
	 * 通过用户id更改用户密码
	 */
	@Transactional
	public Map<String, Object> updateUserPass(Map<String, Object> inMap){
		String userId = (String) inMap.get("userId");
		String oldPassword = (String) inMap.get("oldPassword");
		String newPassword = (String) inMap.get("newPassword");
		String newRePassword = (String) inMap.get("newRePassword");
		if(!newPassword.equals(newRePassword)) {
			throw new AppException("两次输入的密码不一致");
		}
		User user = userDao.findById(Integer.valueOf(userId));
		ByteSource salt = ByteSource.Util.bytes(user.getUsername());
		SimpleHash oldResult = new SimpleHash("MD5", oldPassword, salt, 10);
		if(!user.getPassword().equals(oldResult.toString())) {//与原密码对比
			throw new AppException("原密码不一致");
		}
		updatePass(user, newPassword);
		outMap.put("message", "密码修改成功");
		return outMap;
	}
	
	/**
	 * 
	 * 通过用户员工id修改用户密码
	 * 
	 * @param inMap
	 * @return
	 */
	@Transactional
	public Map<String, Object> updateUserPassByStaff(Map<String, Object> inMap) {
		String sid = (String) inMap.get("sid");
		String newPassword = (String)inMap.get("password");
		String newRePassword = (String) inMap.get("repassword");
		if(!newPassword.equals(newRePassword)) {
			throw new AppException("两次输入的密码不一致");
		}
		User user = userDao.findByStaffId(Integer.valueOf(sid));
		updatePass(user, newPassword);
		outMap.put("message", "密码修改成功");
		return outMap;
	}
	
	
	/**
	 * 通用修改用户密码
	 * @param user
	 * @param newPassword
	 */
	@Transactional
	private void updatePass(User user, String newPassword) {
		ByteSource salt = ByteSource.Util.bytes(user.getUsername());
		SimpleHash newResult = new SimpleHash("MD5", newPassword, salt, 10);
		userDao.updateByIdSetPass(user.getId(), newResult.toString());
		log.info("修改密码成功 username :" + user.getUsername());
	}
	
	
	/**
	 * 
	 * 通过员工id来更新用户角色信息
	 * 
	 * @param inMap
	 * @return
	 */
	
	@Transactional
	public Map<String, Object> updateUserRoleByStaff(Map<String, Object> inMap) {
		String sid = (String) inMap.get("sid");
		List<String> rolesIds = (List<String>) inMap.getOrDefault("roles", null);
		Set<Role> roles = new HashSet();
		//判断用户是否需要存储角色信息
		if(rolesIds != null) {
			List<Integer> rolesIntIds = new ArrayList<Integer>();
			for(int i = 0; i < rolesIds.size(); i++) {
				rolesIntIds.add(Integer.valueOf(rolesIds.get(i)));
			}
			roles = roleDao.findByIdIn(rolesIntIds);
		}
		User user = userDao.findByStaffId(Integer.valueOf(sid));
		user.setRoles(roles);
		userDao.save(user);
		
		
		filterChainService.reloadFilterChains();
		outMap.put("message", "更新成功");
		return outMap;
	}
	
	/**
	 * 
	 * 通过员工号来删除用户信息
	 * 
	 * @param inMap
	 * @return
	 */
	@Transactional
	public Map<String, Object> deleteUserByStaff(Map<String, Object> inMap) {
		String sid = (String) inMap.get("sid");
		User user = userDao.findByStaffId(Integer.valueOf(sid));
		Staff staff = staffDao.findById(Integer.valueOf(sid));
		staff.setHasUser(0);
		staffDao.save(staff);
		if(user != null) {
			userDao.delete(user);
			outMap.put("message", "删除成功");
		}else {
			outMap.put("message", "该员工还没有注册用户");
		}
		filterChainService.reloadFilterChains();
		return outMap;
	}
	
	/**
	 * 通过员工号来锁定用户登录
	 * 
	 * 
	 * @param inMap
	 * @return
	 */
	@Transactional
	public Map<String, Object> lockUserByStaff (Map<String, Object> inMap) {
		String sid = (String) inMap.get("sid");
		User user = userDao.findByStaffId(Integer.valueOf(sid));
		user.setState(1);
		userDao.save(user);
		outMap.put("message", "锁定成功");
		return outMap;
	}
	
	/**
	 * 查询所有用户，不包含员工，角色信息
	 * 
	 * @return
	 */
	
	public Iterable<User> queryAllUserNoStaffAndRoles() {
		Iterable<User> users;
		Cache cache = EhcacheUtil.getCache("resourceCache");
		Element element = cache.get("users");
		if(element == null) {
			users = userDao.findAllUserNoStaffAndRoles();
			cache.put(new Element("users", users));
			log.info("存储在缓存Ehcache中:" + users);
		}else {
			users = (Iterable<User>) element.getObjectValue();
			log.info("从缓存Ehcache中获取" + element.getObjectKey());
		}
		return users;
	}
	
	/**
	 * 通过id和name来查询User
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
		
		outMap.put("users", userDao.findByIdOrNameContaining(id, name));
		return outMap;
	}
	
	/**
	 * 通过id和name来查询User
	 * @param inMap
	 * @return
	 */
	public Map<String, Object> findStaffwithNameAndEmail(Map<String, Object> inMap) {
		String param = (String) inMap.get("param");
		
		outMap.put("users", userDao.findByNameOrEmailContaining(param));
		return outMap;
	}
}
