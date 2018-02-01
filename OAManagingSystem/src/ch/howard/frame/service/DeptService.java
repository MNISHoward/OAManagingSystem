package ch.howard.frame.service;

import java.util.HashMap;
import java.util.Map;

import javax.transaction.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ch.howard.frame.dao.DeptDAO;

@Service
public class DeptService {
	
	private static final transient Logger log = LoggerFactory.getLogger(DeptService.class);
	private transient Map<String,Object> outMap = new HashMap<String, Object>();
	
	@Autowired
	private	DeptDAO deptDao;
	
	
	@Transactional
	public void insertDept() {
		
	}
	
	
}
