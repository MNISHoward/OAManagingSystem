package ch.howard.frame.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ch.howard.frame.util.EhcacheUtil;
import ch.howard.frame.web.IndexAction;
import ch.howard.rbac.dao.ResourceDAO;
import ch.howard.rbac.model.Resource;
import net.sf.ehcache.Cache;
import net.sf.ehcache.CacheManager;
import net.sf.ehcache.Element;

@Service
public class IndexService {
	
	private static final transient Logger log = LoggerFactory.getLogger(IndexAction.class);
	
	@Autowired
	private ResourceDAO resourceDao;
	
	@Autowired
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
