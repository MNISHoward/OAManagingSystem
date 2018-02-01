package ch.howard.rbac.web;


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
import ch.howard.rbac.model.Resource;

@Controller
public class RbacAction extends ActionSupport{
	
	private static final transient Logger log = LoggerFactory.getLogger(RbacAction.class);
	
	private Iterable<Resource> resources;
	
	@Autowired
	private IndexService indexService;

	public Iterable<Resource> getResources() {
		return resources;
	}

	@Override
	public String execute() throws Exception {
		log.info("执行RabcAction.execute");
		HttpServletRequest request = ServletActionContext.getRequest();
		HttpSession session = request.getSession();
		
		//获取资源iterable
		resources = indexService.queryResource();
		if(session.getAttribute("user") == null) {
			SecurityUtils.getSubject().logout();
			return "login";
		}
		
		return super.execute();
	}
	
	
	
}
