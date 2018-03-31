package ch.howard.frame.tag;

import java.io.IOException;
import java.io.Writer;
import java.text.MessageFormat;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.apache.struts2.components.Component;

import com.opensymphony.xwork2.util.ValueStack;

import ch.howard.frame.model.Menu;
import ch.howard.frame.model.Resource;
import ch.howard.rbac.model.Role;

public class MenuComponent extends Component {

	private String resource;
	
	public MenuComponent(ValueStack stack) {
		super(stack);
	}

	@Override
	public boolean start(Writer writer) {
		try {
			Object findValue = findValue(resource);
			Resource r = (Resource) findValue;
			List<Menu> menus = r.getMenus();
			for(Menu m : menus) {
				writeMenu(m, writer);
			}
			return true;
		} catch (IOException e) {
			e.printStackTrace();
			return super.start(writer);
		}
	}
	
	
	public void writeMenu(Menu menu, Writer writer) throws IOException {
		if(this.hasRole(menu)) {
			String pattern = "<a mid=''{0}'' href=''{1}'' class=''list-group-item leftnav-menu-a''><span class=''icon glyphicon {2} />'' ></span><span class=''icon-text''>{3}</span></a>";
			Object[] obj = {menu.getId(), menu.getUrl(), menu.getIconClass(), menu.getTitleName()};
			pattern = MessageFormat.format(pattern, obj);
			writer.write(pattern);
		}
	}
	
	public boolean hasRole(Menu menu) {
		Set<Role> roles = menu.getRoles();
		if(roles.size() <= 0) {
			return true;
		}else{
			Iterator<Role> iterator = roles.iterator();
			Subject subject = SecurityUtils.getSubject();
			while (iterator.hasNext()) {
				Role role = (Role) iterator.next();
				if(subject.hasRole(role.getName())){
					return true;
				}
			}
			return false;
		}
	}

	public String getResource() {
		return resource;
	}

	public void setResource(String resource) {
		this.resource = resource;
	}
	
	
}
