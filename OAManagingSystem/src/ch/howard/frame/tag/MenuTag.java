package ch.howard.frame.tag;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.components.Component;
import org.apache.struts2.views.jsp.ComponentTagSupport;

import com.opensymphony.xwork2.util.ValueStack;

public class MenuTag extends ComponentTagSupport{

	private String resource;
	
	@Override
	public Component getBean(ValueStack arg0, HttpServletRequest arg1, HttpServletResponse arg2) {
		return new MenuComponent(arg0);
	}
	
	@Override
	protected void populateParams() {
		MenuComponent component = (MenuComponent) getComponent();
        component.setResource(resource);
    }

	public String getResource() {
		return resource;
	}

	public void setResource(String resource) {
		this.resource = resource;
	}
	

}
