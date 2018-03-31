package ch.howard.frame.service;

import java.util.HashMap;
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
import ch.howard.frame.dao.ResourceDAO;
import ch.howard.frame.model.Menu;
import ch.howard.frame.model.Resource;
import ch.howard.frame.util.EhcacheUtil;
import ch.howard.rbac.dao.RoleDAO;
import ch.howard.rbac.model.Role;
import net.sf.ehcache.Cache;
import net.sf.ehcache.Element;

@Service
public class MenuService {
	private static final transient Logger log = LoggerFactory.getLogger(MenuService.class);
	private transient Map<String,Object> outMap = new HashMap<String, Object>();
	
	@Autowired
	private MenuDAO menuDao;
	
	@Autowired
	private ResourceDAO resourceDao;
	
	@Autowired
	private ResourceService resourceService;
	
	@Autowired
	private RoleDAO roleDao;
	
	/**
	 * 更改缓存用的常量
	 */
	private static final transient String INSERT = "1";
	private static final transient String UPDATE = "2";
	private static final transient String DELETE = "3";
	
	/**
	 * 查询所有菜单除了资源和角色
	 * 
	 * @return
	 */
	
	public Iterable<Menu> queryAllMenusNoResourceAndRoles() {
		Iterable<Menu> menus;
		Cache cache = EhcacheUtil.getCache("resourceCache");
		Element element = cache.get("menus");
		if(element == null) {
			menus = menuDao.findAllMenuNoResourceAndRoles();
			cache.put(new Element("menus", menus));
			log.info("存储在缓存Ehcache中:" + menus);
		}else {
			menus = (Iterable<Menu>) element.getObjectValue();
			log.info("从缓存Ehcache中获取" + element.getObjectKey());
		}
		return menus;
	}
	
	/**
	 * 新增菜单操作
	 * 
	 * @param inMap
	 * @return
	 */
	
	@Transactional
	public Map<String, Object> insertMenu(Map<String, Object> inMap) {
		String titleName = (String) inMap.get("titleName");
		String name = (String) inMap.get("name");
		String method = (String) inMap.get("url");
		String iconClass = (String) inMap.get("iconClass");
		String resourceId = (String) inMap.get("resource");
		Resource resource = resourceDao.findOne(Integer.valueOf(resourceId));
		String url = resource.getUrl();
		url = url.substring(0, url.indexOf(".do")) + "!"+ method +".do"; //根据选择的资源对url进行拼接
		Menu m = new Menu(name, titleName, url, 0, resource);
		m.setIconClass(iconClass);
		if(!resourceId.equals("1")) { 
			//默认拥有admin
			Role r = roleDao.findById(1);
			r.addMenu(m);
			m.getRoles().add(r);
			menuDao.save(m);
			roleDao.save(r);
		}else {
			menuDao.save(m);
		}
		
		//重新更新缓存
		modifyCache(m, resourceId, INSERT);
		outMap.put("menu", m);
		outMap.put("message", "增加成功");
		return outMap;
	}
	
	
	/**
	 * 通过id查找菜单
	 * 
	 * @param inMap
	 * @return
	 */
	public Map<String, Object> findMenuById(Map<String, Object> inMap) {
		String mid = (String) inMap.get("mid");
		Menu menu = menuDao.findMenuNoRoles(Integer.valueOf(mid));
		outMap.put("menu", menu);
		return outMap;
	}
	
	/**
	 * 修改菜单操作
	 * 
	 * @param inMap
	 * @return
	 */
	
	@Transactional
	public Map<String, Object> updateMenu(Map<String, Object> inMap) {
		String mid = (String) inMap.get("mid");
		String titleName = (String) inMap.get("updateTitleName");
		String name = (String) inMap.get("updateName");
		String method = (String) inMap.get("updateUrl");
		String iconClass = (String) inMap.get("updateIconClass");
		String resourceId = (String) inMap.get("updateResource");
		Resource resource = resourceDao.findOne(Integer.valueOf(resourceId));
		String url = resource.getUrl();
		url = url.substring(0, url.indexOf(".do")) + "!"+ method +".do"; //根据选择的资源对url进行拼接
		Menu m = menuDao.findMenuNoRoles(Integer.valueOf(mid));
		m.setName(name);m.setTitleName(titleName);m.setUrl(url);m.setResource(resource);m.setIconClass(iconClass);
		menuDao.save(m);
		
		//重新更新缓存
		modifyCache(m, resourceId, UPDATE);
		outMap.put("menu", m);
		outMap.put("message", "修改成功");
		return outMap;
	}
	
	/**
	 * 删除菜单功能
	 * @param inMap
	 * @return
	 */
	@Transactional
	public Map<String, Object> deleteMenuByid(Map<String, Object> inMap) {
		String mid = (String) inMap.get("mid");
		Menu m = menuDao.findOne(Integer.valueOf(mid));
		String resourceId = m.getResource().getId().toString();
		Set<Role> roles = m.getRoles();
		Iterator<Role> roleIter = roles.iterator();
		
		//解除角色关系
		while(roleIter.hasNext()) {
			Role r = roleIter.next();
			r.removeMenu(m);
			roleDao.save(r);
		}
		menuDao.delete(m);
		//改变缓存
		modifyCache(m, resourceId, DELETE);
		
		outMap.put("menu", m);
		outMap.put("message", "修改成功");
		return outMap;
	}
	/**
	 * 改变缓存
	 * @param m
	 * @param resourceId
	 * @param type
	 */
	public void modifyCache(Menu m, String resourceId, String type) {
		Iterable<Menu> menus;
		Cache cache = EhcacheUtil.getCache("resourceCache");
		Element element = cache.get("menus");
		if(element != null) {
			menus = (Iterable<Menu>) element.getObjectValue();
			List<Menu> menus1 = (List<Menu>)menus;
			log.info("从缓存Ehcache中获取" + element.getObjectKey());
			if(type == INSERT) {
				menus1.add(m);
			}else if (type == UPDATE) {
				for(Menu m1 : menus1) {
					if(m1.getId().intValue() == m.getId().intValue()) {
						int index = menus1.indexOf(m1);
						menus1.set(index, m);
					}
				}
			}else if(type == DELETE) {
				int index = -1;
				for(Menu m1 : menus1) {
					if(m1.getId().intValue() == m.getId().intValue()) {
						index = menus1.indexOf(m1);
					}
				}
				if(index != -1)
					menus1.remove(index);
			}
			cache.put(new Element("menus", menus));
			log.info("存储在缓存Ehcache中:" + menus);
		}
		
		//改变资源缓存
		element = cache.get("resources");
		if(element != null) {
			Iterable<Resource> resources = (Iterable<Resource>) element.getObjectValue();
			log.info("从缓存Ehcache中获取" + element.getObjectKey());
			Resource resource1 = resourceService.queryResourceById(Integer.valueOf(resourceId), resources);
			List<Menu> menus2 = resource1.getMenus();
			if(type == INSERT) {
				menus2.add(m);
			}else if (type == UPDATE) {
				for(Menu m1 : menus2) {
					if(m1.getId().intValue() == m.getId().intValue()) {
						int index = menus2.indexOf(m1);
						menus2.set(index, m);
					}
				}
			}else if(type == DELETE) {
				int index = -1;
				for(Menu m1 : menus2) {
					if(m1.getId().intValue() == m.getId().intValue()) {
						index = menus2.indexOf(m1);
					}
				}
				if(index != -1)
					menus2.remove(index);
			}
			List<Resource> resources1 = (List<Resource>)resources;
			for(Resource r : resources1) {
				if(r.getId().intValue() == resource1.getId().intValue()) {
					int index = resources1.indexOf(r);
					resources1.set(index, resource1);
				}
			}
			cache.put(new Element("resources", resources1));
			log.info("存储在缓存Ehcache中:" + resources1);
		}
	}
}
