package ch.howard.frame.tag;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.DynamicAttributes;
import javax.servlet.jsp.tagext.SimpleTagSupport;

public class InputTag extends SimpleTagSupport implements DynamicAttributes{

	private ArrayList<String> keys = new ArrayList<String>();
	private ArrayList<Object> values = new ArrayList<Object>();
	
	@Override
	public void doTag() throws JspException, IOException {
		JspWriter out = getJspContext().getOut();
		String attr = "class=' form-control ' ";
		for(int i = 0; i < keys.size(); i++) {
			String key = keys.get(i);
			Object value = values.get(i);
			if(key.equals("id")) {
				keys.add("name");
				values.add(value);
			}
			if(key.equals("type") && (value.equals("radio") || value.equals("checkbox"))) {
				int index = attr.indexOf(" form-control ");
				attr = attr.substring(0, index) + value + attr.substring(index + " form-control ".length()-1);
			}
			if(key.equals("class")) {
				int index = attr.indexOf(" form-control ");
				attr = attr.substring(0, index) + value + attr.substring(index);
			}else {
				attr += key + "='" + value + "' ";
			}
		}
		out.print("<input "+ attr +" />");
		
	}
	
	@Override
	public void setDynamicAttribute( String uri, String localName,
			Object value )throws JspException
	{
		keys.add( localName );
		values.add( value );
	}

	
}	
