package ch.howard.frame.service;

import java.util.Iterator;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ch.howard.frame.dao.ResourceDAO;
import ch.howard.frame.model.Resource;
import ch.howard.frame.util.EhcacheUtil;
import net.sf.ehcache.Cache;
import net.sf.ehcache.Element;

@Service
public class ResourceService {
	
	private static final transient Logger log = LoggerFactory.getLogger(ResourceService.class);
	
	@Autowired
	private ResourceDAO resourceDao;
	
	//从资源集合中读取id中的资源
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
		log.info("执行IndexService.queryResource");
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
	
}
