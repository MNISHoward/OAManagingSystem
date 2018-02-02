package ch.howard.frame.tag;

import java.io.IOException;
import java.io.Writer;
import java.util.Iterator;


import org.apache.struts2.components.Component;

import com.opensymphony.xwork2.util.ValueStack;

import ch.howard.frame.model.Department;

/*
 * 
 * 用于显示树状部门（递归）
 * 
 */

public class DeptComponent extends Component {
	
	private String value;

	public DeptComponent(ValueStack stack) {
		super(stack);
	}
	
	
	
	@Override
	public boolean start(Writer writer) {
		try {
			Object findValue = findValue(value);
			writer.write("<ul class=\"list-group\" >");
			Iterable<Department> depts = (Iterable<Department>) findValue;
			deptIterator(depts, writer);
			writer.write("</ul>");
			return true;
		} catch (IOException e) {
			e.printStackTrace();
			return super.start(writer);
		}
		
	}

	private void deptIterator(Iterable<Department> departments, Writer out) throws IOException {
		Iterator<Department> iterator = departments.iterator();
		
		while(iterator.hasNext()) {
			Department dept = iterator.next();
			if(dept.getDepartments() == null || dept.getDepartments().size() <= 0) {
				out.write("<li class=\"list-group-item\"><a>" + dept.getName() + "</a>");
			}else{
				out.write("<li class=\"list-group-item\"><a class=\"has-child\"><span class=\"glyphicon glyphicon-chevron-right\" ></span>" + dept.getName() + "</a>");
				out.write("<ul class=\"list-group\" >");
				deptIterator(dept.getDepartments(), out);
				out.write("</ul>");
				out.write("</li>");
			}
		}
	}



	public String getValue() {
		return value;
	}



	public void setValue(String value) {
		this.value = value;
	}
	
	
	
}
