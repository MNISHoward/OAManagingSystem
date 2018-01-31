package ch.howard.frame.web;

import javax.servlet.http.HttpSession;

import org.apache.shiro.SecurityUtils;
import org.apache.struts2.ServletActionContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;

import com.opensymphony.xwork2.ActionSupport;


@Controller
public class IndexAction extends ActionSupport {
	
	private static final transient Logger log = LoggerFactory.getLogger(IndexAction.class);
	
	@Override
	public String execute() throws Exception {
		log.info("执行IndexAction.execute");
		HttpSession session = ServletActionContext.getRequest().getSession();
		if(session.getAttribute("user") == null) {
			SecurityUtils.getSubject().logout();
			return "login";
		}
		return super.execute();
	}
	
}
