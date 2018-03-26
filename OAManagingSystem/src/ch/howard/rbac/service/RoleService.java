package ch.howard.rbac.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.transaction.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ch.howard.frame.dao.MenuDAO;
import ch.howard.frame.dao.UserDAO;
import ch.howard.frame.model.Menu;
import ch.howard.frame.model.User;
import ch.howard.frame.shiro.service.FilterChainDefinitionsService;
import ch.howard.frame.util.EhcacheUtil;
import ch.howard.rbac.dao.RoleDAO;
import ch.howard.rbac.model.Role;
import net.sf.ehcache.Cache;
import net.sf.ehcache.Element;

@Service
public class RoleService {
	
	@Autowired
	private RoleDAO roleDao;
	
	@Autowired
	private UserDAO userDao;
	
	@Autowired
	private MenuDAO menuDao;
	
	@Autowired
	private FilterChainDefinitionsService filterChainService;
	
	/**
	 * 更改缓存用的常量
	 */
	private static final transient String INSERT = "1";
	private static final transient String UPDATE = "2";
	private static final transient String DELETE = "3";
	
	private static final transient Logger log = LoggerFactory.getLogger(RoleService.class);
	private transient Map<String,Object> outMap = new HashMap<String, Object>();
	
	/**
	 * 获取所有的角色，不包含菜单和用户项
	 * @return
	 */
	public Iterable<Role> getAllRole(){
		log.info("执行RoleService.getAllRole");
		Iterable<Role> roles;
		Cache cache = EhcacheUtil.getCache("resourceCache");
		Element element = cache.get("roles");
		if(element == null) {
			roles = roleDao.findAllRoleNoMenuAndUser();
			cache.put(new Element("roles", roles));
			log.info("存储在缓存Ehcache中:" + roles);
		}else {
			roles = (Iterable<Role>) element.getObjectValue();
			log.info("从缓存Ehcache中获取" + element.getObjectKey());
		}
		return roles;
	}
	
	/**
	 * 根据员工id查找该用户具有的所有角色
	 * 
	 * @param inMap
	 * @return
	 */
	public Map<String, Object> findRolesByUserStaffId(Map<String,Object> inMap) {
		log.info("执行RoleService.findRolesByUserStaffId");
		String sid = (String) inMap.get("sid");
		User u = userDao.findByStaffId(Integer.valueOf(sid));
		Set<Role> roles = u.getRoles();
		outMap.put("roles", roles);
		return outMap;
	}
	
	/**
	 * 保存新的角色
	 * 
	 * @param inMap
	 * @return
	 */
	
	@Transactional
	public Map<String, Object> insertRole(Map<String,Object> inMap) {
		log.info("执行RoleService.insertRole");
		String titleName = (String) inMap.get("titleName");
		String name = (String) inMap.get("name");
		List<String> usersIds = (List<String>) inMap.getOrDefault("users", null);
		Set<User> users = new HashSet<User>();
		List<String> menusIds = (List<String>) inMap.getOrDefault("menus", null);
		Set<Menu> menus = new HashSet<Menu>();
		//判断role是否存储用户信息
		if(usersIds != null) {
			for(int i = 0; i < usersIds.size(); i++) {
				User u = userDao.findById(Integer.valueOf(usersIds.get(i)));
				users.add(u);
			}
		}
		Role role = new Role(name, titleName, 0);
		role.setUsers(users);
		
		//判断role是否存储用户信息
		if(menusIds != null) {
			for(int i = 0; i < menusIds.size(); i++) {
				menus.add(new Menu(Integer.valueOf(menusIds.get(i))));
			}
		}
		
		role.setMenus(menus);
		
		roleDao.save(role);
		
		
		
		//设置用户的级联增加
		if(usersIds != null) {
			for(int i = 0; i < usersIds.size(); i++) {
				User u = userDao.findById(Integer.valueOf(usersIds.get(i)));
				u.addRole(role);
				userDao.save(u); 
			}
		}
		
		//重新改变role的缓存
		modifyCache(role, INSERT);
		outMap.put("role", role);
		outMap.put("message", "新增成功");
		return outMap;
	}
	
	/**
	 * 根据角色id寻找role
	 * @param inMap
	 * @return
	 */
	public Map<String, Object> findRoleById(Map<String, Object> inMap) {
		String rid = (String) inMap.get("rid");
		Role role = roleDao.findById(Integer.valueOf(rid));
		outMap.put("role", role);
		return outMap;
	}
	
	/**
	 * 通过角色id修改角色
	 * @param inMap
	 * @return
	 */
	@Transactional
	public Map<String, Object> updateRoleById(Map<String, Object> inMap) {
		log.info("执行RoleService.updateRoleById");
		String titleName = (String) inMap.get("updateTitleName");
		String name = (String) inMap.get("updateName");
		String rid = (String) inMap.get("rid");
		List<String> usersIds = (List<String>) inMap.getOrDefault("updateUsers", new ArrayList<String>());
		Set<User> users = new HashSet<User>();
		List<String> menusIds = (List<String>) inMap.getOrDefault("updateMenus", new ArrayList<String>());
		Set<Menu> menus = new HashSet<Menu>();
		
		//判断role是否存储用户信息
		if(usersIds != null && usersIds.size() > 0) {
			for(int i = 0; i < usersIds.size(); i++) {
				User u = new User(Integer.valueOf(usersIds.get(i)));
				users.add(u);
			}
		}
		
		Role role = roleDao.findById(Integer.valueOf(rid));
		
		//修改role的中文名
		role.setTitleName(titleName);
		
		//修改role的英文名
		role.setName(name);
		
		//判断原来的user和修改的user，如果原来的user中已经包含修改的user 则将修改的user移出list，
		//如果不包含修改的user,则将原本user移除该role
		Set<User> users2 = role.getUsers();
		Iterator<User> user2Iter = users2.iterator();
		while(user2Iter.hasNext()) {
			User user = user2Iter.next();
			if(usersIds.contains(user.getId().toString())) {
				usersIds.remove(user.getId().toString());
			}else {
				User u = userDao.findById(user.getId());
				u.removeRole(role);
				userDao.save(u);
			}
		}
		role.setUsers(users);
		
		//判断role是否存储用户信息
		if(menusIds != null) {
			for(int i = 0; i < menusIds.size(); i++) {
				menus.add(new Menu(Integer.valueOf(menusIds.get(i))));
			}
		}
		
		role.setMenus(menus);
		
		roleDao.save(role);
		
		//剩下的list(即新增加的user)设置用户的级联增加
		if(usersIds != null) {
			for(int i = 0; i < usersIds.size(); i++) {
				User u = userDao.findById(Integer.valueOf(usersIds.get(i)));
				u.addRole(role);
				userDao.save(u); 
			}
		}
		
		//重新改变role的缓存
		modifyCache(role, UPDATE);
		outMap.put("message", "修改成功");
		outMap.put("role", role);
		return outMap;
	}
	
	/**
	 * 删除角色
	 * @param inMap
	 * @return
	 */
	@Transactional
	public Map<String, Object> deleteRoleByid(Map<String, Object> inMap) {
		String rid = (String) inMap.get("rid");
		Role role = roleDao.findById(Integer.valueOf(rid));
		Set<User> users = role.getUsers();
		
		//解除user关系
		Iterator<User> userIter = users.iterator();
		while(userIter.hasNext()) {
			User u = userIter.next();
			u.removeRole(role);
			userDao.save(u);
		}
		
		//解除menu关系
		Set<Menu> menus = role.getMenus();
		Iterator<Menu> menuIter = menus.iterator();
		while(menuIter.hasNext()) {
			Menu m = menuIter.next();
			m.removeRole(role);
			menuDao.save(m);
		}
		roleDao.delete(role);
		modifyCache(role, DELETE);
		outMap.put("role", role);
		outMap.put("message", "删除成功");
		return outMap;
	}
	
	/**
	 * 改变缓存
	 * @param role
	 * @param type
	 */
	public void modifyCache(Role role, String type) {
		Iterable<Role> roles;
		Cache cache = EhcacheUtil.getCache("resourceCache");
		Element element = cache.get("roles");
		if(element != null) {
			roles = (Iterable<Role>) element.getObjectValue();
			log.info("从缓存Ehcache中获取" + element.getObjectKey());
			List<Role> roles1 = (List<Role>)roles;
			if(type == INSERT) {
				roles1.add(role);
			}else if(type == UPDATE) {
				for(Role role1 : roles1) {
					if(role1.getId().intValue() == role.getId().intValue()) {
						int index = roles1.indexOf(role1);
						roles1.set(index, role);
					}
				}
			}else if (type == DELETE) {
				int index = -1;
				for(Role role1 : roles1) {
					if(role1.getId().intValue() == role.getId().intValue()) {
						index = roles1.indexOf(role1);
					}
				}
				if(index != -1)
				roles1.remove(index);
			}
			
			filterChainService.reloadFilterChains();
			
			cache.put(new Element("roles", roles));
			log.info("存储在缓存Ehcache中:" + roles);
		}
	}
} 
