package ch.howard.frame.shiro.filter;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.filter.authc.FormAuthenticationFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import ch.howard.frame.dao.UserDAO;
import ch.howard.frame.model.User;

/*
 * 
 * shiro rememberMe session设置过滤器
 */
public class RememberMeFilter extends FormAuthenticationFilter {
	
	@Autowired
	private UserDAO userDao;
	
	
	@Override  
    protected boolean isAccessAllowed(ServletRequest request, ServletResponse response, Object mappedValue) {
		
		Subject subject = getSubject(request, response);
		Session session = subject.getSession(true);
		if(!subject.isAuthenticated() && subject.isRemembered()) {
			if(session.getAttribute("user") == null ) {
				String username = subject.getPrincipal().toString();
				User user = userDao.findByUsername(username);
				session.setAttribute("user", user);
                session.setAttribute("staff", user.getStaff());
			}
		}
		
		return subject.isAuthenticated() || subject.isRemembered();
	}
	
}
