package ch.howard.frame.tag;

import java.io.IOException;
import java.io.Writer;
import java.util.Iterator;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.SimpleTagSupport;

import org.apache.struts2.components.Component;

import com.opensymphony.xwork2.util.ValueStack;

import ch.howard.frame.model.Department;

public class DeptComponent extends Component {
	
	private String value;

	public DeptComponent(ValueStack stack) {
		super(stack);
	}
	
	
	
	@Override
	public boolean start(Writer writer) {
		try {
			Object findValue = findValue(value);
			Iterable<Department> depts = (Iterable<Department>) findValue;
			deptIterator(depts, writer);
			return true;
		} catch (IOException e) {
			e.printStackTrace();
			return super.start(writer);
		}
		
	}

	private void deptIterator(Iterable<Department> departments, Writer out) throws IOException {
		Iterator<Department> iterator = departments.iterator();
		out.write("<ul class=\"list-group\" >");
		while(iterator.hasNext()) {
			Department dept = iterator.next();
			System.out.println(dept.getName() + "   ," + dept.getDepartments().size());
			if(dept.getDepartments() == null || dept.getDepartments().size() <= 0) {
				out.write("<li class=\"list-group-item\"><a>" + dept.getName() + "</a>");
			}else{
				out.write("<ul class=\"list-group\" >");
				out.write("<li class=\"list-group-item\"><a class=\"has-child\"><span class=\"glyphicon glyphicon-chevron-right\" ></span>" + dept.getName() + "</a>");
				deptIterator(dept.getDepartments(), out);
				out.write("</li>");
				out.write("</ul>");
			}
		}
		out.write("</ul>");
	}



	public String getValue() {
		return value;
	}



	public void setValue(String value) {
		this.value = value;
	}
	
	
	
}
