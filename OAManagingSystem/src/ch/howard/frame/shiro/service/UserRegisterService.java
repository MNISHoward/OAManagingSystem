package ch.howard.frame.shiro.service;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.crypto.hash.SimpleHash;
import org.apache.shiro.session.Session;
import org.apache.shiro.util.ByteSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ch.howard.frame.dao.UserDAO;
import ch.howard.frame.exception.AppException;
import ch.howard.frame.model.User;
import ch.howard.rbac.dao.StaffDAO;
import ch.howard.rbac.model.Staff;

@Service
public class UserRegisterService {
	private static final transient Logger log = LoggerFactory.getLogger(UserLoginService.class);
	private transient Map<String,Object> outMap = new HashMap<String, Object>();
	@Autowired
	private StaffDAO staffDao;
	
	@Autowired
	private UserDAO userDao;
	
	public Map<String, Object> userRegister(Map<String, Object> inMap) {
		log.info("执行UserRegisterService.userRegister");
		String username = (String) inMap.get("reginame");
		String password = (String) inMap.get("regipass");
		Staff staff = staffDao.findByName(username);
		if(staff == null) {
			log.info("注册失败，公司不存在该员工" + username);
			throw new AppException("注册失败，公司不存在该员工");
		}else {
			User user = userDao.findByUsername(username);
			if(user == null) {
				ByteSource salt = ByteSource.Util.bytes(username);
				int hashIterations = 10;
				SimpleHash encodePass = new SimpleHash("MD5", password, salt, hashIterations);
				User u = new User(username,encodePass.toString(), new Date(), 0,staff);
				userDao.save(u);
				log.info("注册成功 :" + username);
				outMap.put("message", "注册成功 : " + username + "，请登录。");
			}else {
				log.info("注册失败，用户已经存在" + username);
				throw new AppException("注册失败，用户已经存在");
			}
		}
		return outMap;
	}
	
}
