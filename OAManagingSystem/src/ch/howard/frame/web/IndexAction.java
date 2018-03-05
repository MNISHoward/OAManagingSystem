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

import ch.howard.frame.model.Resource;
import ch.howard.frame.service.IndexService;
import ch.howard.frame.service.ResourceService;
import ch.howard.frame.util.EhcacheUtil;
import net.sf.ehcache.Cache;
import net.sf.ehcache.CacheManager;
import net.sf.ehcache.Element;


@Controller
public class IndexAction extends ActionSupport {
	
	private static final transient Logger log = LoggerFactory.getLogger(IndexAction.class);
	
	@Autowired
	private ResourceService resourceService;
	@Autowired
	private IndexService indexService;
	
	private Iterable<Resource> resources;
	
	private Integer rid;
	private Resource resource;
	
	public Iterable<Resource> getResources() {
		return resources;
	}
	
	public void setRid(Integer rid) {
		this.rid = rid;
	}
	
	public Resource getResource() {
		return resource;
	}

	@Override
	public String execute() throws Exception {
		log.info("执行IndexAction的execute开始");
		//判断session中user项是否有值
		if(!indexService.verifyUserInSession()) {
			return "login";
		}
		
		//获取资源项
		resources = resourceService.queryResource();
		
		//获取资源中的菜单项
		if(rid != null) {
			resource = resourceService.queryResourceById(rid, resources);
		}
		
		return "success";
	}
	
	
	
	public String calendarExecute() {
		
		return "calendar";
	}
}
