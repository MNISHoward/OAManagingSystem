package ch.howard.frame.web;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.shiro.SecurityUtils;
import org.apache.struts2.ServletActionContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import com.opensymphony.xwork2.ActionSupport;

import ch.howard.frame.service.IndexService;
import ch.howard.frame.util.EhcacheUtil;
import ch.howard.rbac.model.Resource;
import net.sf.ehcache.Cache;
import net.sf.ehcache.CacheManager;
import net.sf.ehcache.Element;


@Controller
public class IndexAction extends ActionSupport {
	
	private static final transient Logger log = LoggerFactory.getLogger(IndexAction.class);
	
	@Autowired
	private IndexService indexService;
	private Iterable<Resource> resources;
	
	public Iterable<Resource> getResources() {
		return resources;
	}

	@Override
	public String execute() throws Exception {
		log.info("执行IndexAction.execute");
		HttpServletRequest request = ServletActionContext.getRequest();
		HttpSession session = request.getSession();
		resources = indexService.queryResource();
		if(session.getAttribute("user") == null) {
			SecurityUtils.getSubject().logout();
			return "login";
		}
		return super.execute();
	}
	
}
