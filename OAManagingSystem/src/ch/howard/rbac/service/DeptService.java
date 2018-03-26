package ch.howard.rbac.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
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
	
	/**
	 * 查询所有部门，存储缓存
	 * 
	 * @return
	 */
	public Iterable<Department> queryAllDept() {
		Iterable<Department> depts;
		Cache cache = EhcacheUtil.getCache("resourceCache");
		Element element = cache.get("departments");
		Element element2 = cache.get("deptUpdate");
		if(element == null || (element2 != null && (Boolean)element2.getObjectValue() == true )) {
			depts = deptDao.findByDepartmentsIsNull();
			cache.put(new Element("departments", depts));
			log.info("存储在缓存Ehcache中:" + depts);
			
			cache.put(new Element("deptUpdate", Boolean.FALSE));
			log.info("dept缓存已经更新");
		}else {
			depts = (Iterable<Department>) element.getObjectValue();
			log.info("从缓存Ehcache中获取" + element.getObjectKey());
		}
		return depts;
	}
	/**
	 * 把所有子部门都放进list
	 * @param depts
	 * @param depts1
	 */
	public void allDept(Iterable<Department> depts, List<Department> depts1) {
		Iterator<Department> iterator = depts.iterator();
		while(iterator.hasNext()) {
			Department next = iterator.next();
			if(next.getDepartments() == null || next.getDepartments().size() <= 0) {
				depts1.add(next);
			}else {
				depts1.add(next);
				allDept(next.getDepartments(), depts1);
			}
			
		}
	}
	
}
