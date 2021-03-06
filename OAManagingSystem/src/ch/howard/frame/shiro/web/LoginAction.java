package ch.howard.frame.shiro.web;

import javax.servlet.http.HttpSession;

import org.apache.struts2.ServletActionContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;

import com.opensymphony.xwork2.ActionSupport;


@Controller
public class LoginAction extends ActionSupport {
	private static final transient Logger log = LoggerFactory.getLogger(LoginAction.class);

	public Integer getRid() {
		return 1;
	}

	@Override
	public String execute() throws Exception {
		HttpSession session = ServletActionContext.getRequest().getSession();
		if(session.getAttribute("user") != null)
			return "index";
		return super.execute();
	}
	
	
	
}
