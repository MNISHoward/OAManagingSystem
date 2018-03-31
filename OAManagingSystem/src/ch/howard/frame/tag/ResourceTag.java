package ch.howard.frame.tag;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.components.Component;
import org.apache.struts2.views.jsp.ComponentTagSupport;

import com.opensymphony.xwork2.util.ValueStack;

public class ResourceTag extends ComponentTagSupport{
	
	private String resources;

	@Override
	public Component getBean(ValueStack arg0, HttpServletRequest arg1, HttpServletResponse arg2) {
		return new ResourceComponent(arg0);
	}
	
	@Override
	protected void populateParams() {
		ResourceComponent component = (ResourceComponent) getComponent();
        component.setResources(resources);
    }

	public String getResources() {
		return resources;
	}

	public void setResources(String resources) {
		this.resources = resources;
	}
	
	
	
	
	
	
}
