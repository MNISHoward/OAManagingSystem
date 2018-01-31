package ch.howard.frame.shiro.service;

import java.util.HashMap;
import java.util.Map;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.LockedAccountException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ch.howard.frame.dao.UserDAO;
import ch.howard.frame.exception.AppException;
import ch.howard.frame.model.User;

@Service
public class UserLoginService {
	private static final transient Logger log = LoggerFactory.getLogger(UserLoginService.class);
	private transient Map<String,Object> outMap = new HashMap<String, Object>();
	
	@Autowired
	private UserDAO userDao;
	
	public Map<String, Object> userlogin(Map<String, Object> inMap) {
		String username = (String) inMap.getOrDefault("username","");
		String password = (String) inMap.getOrDefault("password","");
		String rememberMe = (String) inMap.getOrDefault("rememberMe", "off");
		boolean reMe;
		if(rememberMe.equals("on")) {
			reMe = true;
		}else {
			reMe = false;
		}
		Subject currentUser = SecurityUtils.getSubject();
		if (!currentUser.isAuthenticated()) {
            UsernamePasswordToken token = new UsernamePasswordToken(username, password);
            token.setRememberMe(reMe);
            try {
                currentUser.login(token);
                Session session = currentUser.getSession();
                User user = userDao.findByUsername(username);
                session.setAttribute("user", user);
                session.setAttribute("staff", user.getStaff());
                log.info(token.getPrincipal() + "：登录成功" + ",是否记住我:" + reMe);
                outMap.put("message", "登录成功");
            }catch (UnknownAccountException uae) {
                log.info("用户不存在：" + token.getPrincipal());
                throw new AppException("登录失败，用户不存在:" + token.getPrincipal());
            } catch (IncorrectCredentialsException ice) {
                log.info("密码错误：" + token.getPrincipal());
                throw new AppException("登录失败，密码错误");
            } catch (LockedAccountException lae) {
                log.info("用户已经被锁定！" + token.getPrincipal());
                throw new AppException("登录失败，用户被锁定");
            }catch (AuthenticationException ae) {
            	log.info("登录失败" + ae.getMessage());
            	throw new AppException("登录失败");
            }
        }
		return outMap;
	}
}
