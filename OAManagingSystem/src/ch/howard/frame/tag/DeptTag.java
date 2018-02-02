package ch.howard.frame.tag;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.components.Component;
import org.apache.struts2.views.jsp.ComponentTagSupport;

import com.opensymphony.xwork2.util.ValueStack;

public class DeptTag extends ComponentTagSupport{
	private String value;

	@Override
	public Component getBean(ValueStack arg0, HttpServletRequest arg1, HttpServletResponse arg2) {
		return new DeptComponent(arg0);
	}
	
	@Override
	protected void populateParams() {
		DeptComponent component = (DeptComponent) getComponent();
        component.setValue(value);

    }

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}
	
	

}
