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

public class ResourceComponent extends Component {
	
	private String resources;
	
	public ResourceComponent(ValueStack stack) {
		super(stack);
	}

	@Override
	public boolean start(Writer writer) {
		try {
			Object findValue = findValue(resources);
			List<Resource> r = (List<Resource>) findValue;
			Resource resource2 = r.get(0);
			String pattern = "<li><a rid=''{0}'' href=''{1}?rid={0}''>{2}</a></li>";
			Object[] obj = {resource2.getId(), resource2.getUrl(), "<span class=' glyphicon glyphicon-home'></span>"};
			pattern = MessageFormat.format(pattern, obj);
			writer.write(pattern);
			for(int i = 1; i < r.size(); i++){
				Resource resource = r.get(i);
				if(hasRole(resource)) {
					writeResource(resource,writer);
				}
			}
			return true;
		} catch (IOException e) {
			e.printStackTrace();
			return super.start(writer);
		}
	}
	
	
	public void writeResource(Resource resource, Writer writer) throws IOException {
		String pattern = "<li><a rid=''{0}'' href=''{1}?rid={0}''>{2}</a></li>";
		Object[] obj = {resource.getId(), resource.getUrl(), resource.getTitleName()};
		pattern = MessageFormat.format(pattern, obj);
		writer.write(pattern);
	}
	
	public boolean hasRole(Resource resource) {
		List<Menu> menus = resource.getMenus();
		if(menus.size() <= 0) {
			return true;
		}else{
			for(Menu m : menus) {
				Set<Role> roles = m.getRoles();
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
				}
			}
			return false;
		}
	}

	public String getResources() {
		return resources;
	}

	public void setResources(String resources) {
		this.resources = resources;
	}
}
