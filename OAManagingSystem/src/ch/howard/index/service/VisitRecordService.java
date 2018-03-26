package ch.howard.index.service;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.transaction.Transactional;

import org.apache.shiro.SecurityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import ch.howard.frame.model.User;
import ch.howard.index.dao.VisitRecordDAO;
import ch.howard.index.model.VisitRecord;

@Service
public class VisitRecordService {
	private static final transient Logger log = LoggerFactory.getLogger(VisitRecordService.class);
	private transient Map<String,Object> outMap = new HashMap<String, Object>();
	
	@Autowired
	private VisitRecordDAO visitRecordDao;
	
	/**
	 * 查找全部的访问记录
	 * @return
	 */
	public List<VisitRecord> findVisitRecord() {
		User u = (User) SecurityUtils.getSubject().getSession().getAttribute("user");
		int limit = 3;
		Pageable pageble = new PageRequest(0, limit);
		Page<VisitRecord> visit = visitRecordDao.findVisitRecordByUserId(u.getId(), pageble);
		
		return visit.getContent();
	}
	
	
	/**
	 * 新的访问记录
	 * @param inMap
	 */
	@Transactional
	public void saveVisitRecord(Map<String, Object> inMap) {
		String name = (String) inMap.get("name");
		User u = (User) SecurityUtils.getSubject().getSession().getAttribute("user");
		
		VisitRecord record = new VisitRecord();
		record.setVisitTime(new Date());
		record.setName(name);
		record.setUser(new User(u.getId()));
		
		visitRecordDao.save(record);
	}
	
}
