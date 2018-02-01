package ch.howard.frame.service;

import java.util.HashMap;
import java.util.Map;

import javax.transaction.Transactional;

import org.apache.shiro.crypto.hash.SimpleHash;
import org.apache.shiro.util.ByteSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ch.howard.frame.dao.UserDAO;
import ch.howard.frame.exception.AppException;
import ch.howard.frame.model.User;

@Service
public class UserService {
	private static final transient Logger log = LoggerFactory.getLogger(UserService.class);
	private transient Map<String,Object> outMap = new HashMap<String, Object>();
	
	@Autowired
	private UserDAO userDao;
	
	@Transactional
	public Map<String, Object> updateUserPass(Map<String, Object> inMap){
		log.info("执行UserService.updateUserPass");
		String userId = (String) inMap.get("userId");
		String oldPassword = (String) inMap.get("oldPassword");
		String newPassword = (String) inMap.get("newPassword");
		String newRePassword = (String) inMap.get("newRePassword");
		if(!newPassword.equals(newRePassword)) {
			throw new AppException("两次输入的密码不一致");
		}
		User user = userDao.findById(Integer.valueOf(userId));
		ByteSource salt = ByteSource.Util.bytes(user.getUsername());
		SimpleHash oldResult = new SimpleHash("MD5", oldPassword, salt, 10);
		if(!user.getPassword().equals(oldResult.toString())) {
			throw new AppException("原密码不一致");
		}
		SimpleHash newResult = new SimpleHash("MD5", newPassword, salt, 10);
		userDao.updateByIdSetPass(user.getId(), newResult.toString());
		log.info("修改密码成功 username :" + user.getUsername());
		outMap.put("message", "密码修改成功");
		return outMap;
	}
	
	
}
