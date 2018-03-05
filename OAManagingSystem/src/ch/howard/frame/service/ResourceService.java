package ch.howard.frame.service;

import java.util.ArrayList;
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

import com.opensymphony.xwork2.ActionContext;

import ch.howard.frame.dao.ResourceDAO;
import ch.howard.frame.model.Menu;
import ch.howard.frame.model.Resource;
import ch.howard.frame.util.EhcacheUtil;
import ch.howard.rbac.model.Role;
import net.sf.ehcache.Cache;
import net.sf.ehcache.Element;

@Service
public class ResourceService {
	
	private static final transient Logger log = LoggerFactory.getLogger(ResourceService.class);
	private transient Map<String,Object> outMap = new HashMap<String, Object>();
	
	@Autowired
	private ResourceDAO resourceDao;
	
	/**
	 * 更改缓存用的常量
	 */
	private static final transient String INSERT = "1";
	private static final transient String UPDATE = "2";
	private static final transient String DELETE = "3";
	
	/**
	 * 从资源集合中读取id中的资源
	 * @param rid
	 * @param resources
	 * @return
	 */
	public Resource queryResourceById(Integer rid, Iterable<Resource> resources) {
		Resource resource = null;
		Iterator<Resource> iterator = resources.iterator();
		while(iterator.hasNext()) {
			Resource next = iterator.next();
			if(next.getId().intValue() == rid) {
				 resource = next;
			}
		}
		return resource;
	}
	
	
	//从数据库中读取所有的资源项
	public Iterable<Resource> queryResource() {
		Iterable<Resource> resources;
		Cache cache = EhcacheUtil.getCache("resourceCache");
		Element element = cache.get("resources");
		if(element == null) {
			resources= resourceDao.findAll();
			cache.put(new Element("resources", resources));
			log.info("存储在Ehcache缓存:" + resources);
		}else {
			log.info("从Ehcache缓存中读取:" + element.getObjectKey());
			resources = (Iterable<Resource>) element.getObjectValue();
		}
		
		return resources; 
	}
	
	/**
	 * 新增资源操作
	 * 
	 * @param inMap
	 * @return
	 */
	
	@Transactional
	public Map<String, Object> insertResource(Map<String, Object> inMap) {
		String titleName = (String) inMap.get("titleName");
		String name = (String) inMap.get("name");
		String url = (String) inMap.get("url");
		Resource resource = new Resource(name, titleName, url, 0);
		resourceDao.save(resource);
		
		//重新更新缓存
		modifyCache(resource, INSERT);
		outMap.put("resource", resource);
		outMap.put("message", "增加成功");
		return outMap;
	}
	
	/**
	 * 通过id查找资源
	 * @param inMap
	 * @return
	 */
	public Map<String, Object> findResourceById(Map<String, Object> inMap) {
		String rid = (String) inMap.get("rid");
		Resource resource = resourceDao.findById(Integer.valueOf(rid));
		outMap.put("resource", resource);
		return outMap;
	}
	
	
	/**
	 * 更改资源操作
	 * 
	 * @param inMap
	 * @return
	 */
	
	@Transactional
	public Map<String, Object> updateResource(Map<String, Object> inMap) {
		String rid = (String) inMap.get("rid");
		String titleName = (String) inMap.get("updateTitleName");
		String name = (String) inMap.get("updateName");
		String url = (String) inMap.get("updateUrl");
		Resource resource = resourceDao.findOne(Integer.valueOf(rid));
		resource.setTitleName(titleName);
		resource.setName(name);
		resource.setUrl(url);
		resourceDao.save(resource);
		
		//重新更新缓存
		modifyCache(resource, UPDATE);
		outMap.put("resource", resource);
		outMap.put("message", "修改成功");
		return outMap;
	}
	
	/**
	 * 删除资源
	 * @param inMap
	 * @return
	 */
	@Transactional
	public Map<String, Object> deleteResourceByid(Map<String, Object> inMap) {
		String rid = (String) inMap.get("rid");
		Resource resource = resourceDao.findOne(Integer.valueOf(rid));
		if(resource.getMenus().size() > 0) {
			outMap.put("success", false);
			outMap.put("message", "删除失败，该资源拥有菜单，请前往菜单管理中更改关联");
		}else {
			resourceDao.delete(resource);
			//更改资源
			modifyCache(resource, DELETE);
			outMap.put("resource", resource);
			outMap.put("success", true);
			outMap.put("message", "删除成功");
		}
		
		return outMap;
	}
	
	public void modifyCache(Resource resource, String type) {
		Iterable<Resource> resources;
		Cache cache = EhcacheUtil.getCache("resourceCache");
		Element element = cache.get("resources");
		if(element != null) {
			log.info("从Ehcache缓存中读取:" + element.getObjectKey());
			resources = (Iterable<Resource>) element.getObjectValue();
			List<Resource> resources1 = (List<Resource>)resources;
			if(type == INSERT) {
				resources1.add(resource);
			}else if(type == UPDATE) {
				for(Resource r : resources1) {
					if(r.getId().intValue() == resource.getId().intValue()) {
						int index = resources1.indexOf(r);
						resources1.set(index, resource);
					}
				}
			}else if(type == DELETE) {
				int index = -1;
				for(Resource r : resources1) {
					if(r.getId().intValue() == resource.getId().intValue()) {
						index = resources1.indexOf(r);
					}
				}
				if(index != -1) {
					resources1.remove(index);
				}
			}
			cache.put(new Element("resources", resources1));
			log.info("存储在缓存Ehcache中:" + resources1);
		}
	}
}
