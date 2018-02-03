package ch.howard.rbac.service;

import java.util.HashMap;
import java.util.Map;

import javax.transaction.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ch.howard.frame.util.EhcacheUtil;
import ch.howard.rbac.dao.DeptDAO;
import ch.howard.rbac.model.Department;
import net.sf.ehcache.Cache;
import net.sf.ehcache.Element;

@Service
public class DeptService {
	
	private static final transient Logger log = LoggerFactory.getLogger(DeptService.class);
	private transient Map<String,Object> outMap = new HashMap<String, Object>();
	
	@Autowired
	private	DeptDAO deptDao;
	
	
	@Transactional
	public void insertDept() {
		
	}
	
	public Iterable<Department> queryAllDept() {
		Iterable<Department> depts;
		Cache cache = EhcacheUtil.getCache("resourceCache");
		Element element = cache.get("departments");
		if(element == null) {
			depts = deptDao.findByDepartmentsIsNull();
			cache.put(new Element("departments", depts));
			log.info("存储在缓存Ehcache中:" + depts);
		}else {
			depts = (Iterable<Department>) element.getObjectValue();
			log.info("从缓存Ehcache中获取" + element.getObjectKey());
		}
		return depts;
	}
	
}
