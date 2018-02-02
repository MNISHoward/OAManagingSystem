package ch.howard.frame.service;

import java.util.List;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.session.Session;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ch.howard.frame.dao.ResourceDAO;
import ch.howard.frame.model.Resource;
import ch.howard.frame.util.EhcacheUtil;
import ch.howard.frame.web.IndexAction;
import net.sf.ehcache.Cache;
import net.sf.ehcache.CacheManager;
import net.sf.ehcache.Element;

@Service
public class IndexService {
	
	private static final transient Logger log = LoggerFactory.getLogger(IndexService.class);
	
	
	//判断session中的user
	public boolean verifyUserInSession() {
		Session session = SecurityUtils.getSubject().getSession();
		if(session.getAttribute("user") == null) {
			SecurityUtils.getSubject().logout();
			return false;
		}
		return true;
	}
	
	
	
}
