package ch.howard.frame.shiro.factory;

import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import ch.howard.frame.model.Menu;
import ch.howard.frame.shiro.service.UserLoginService;
import ch.howard.rbac.dao.RoleDAO;
import ch.howard.rbac.model.Role;

public class FilterChainFactoryBuilder {
	
	private static final transient Logger log = LoggerFactory.getLogger(FilterChainFactoryBuilder.class);
	
	@Autowired
	private RoleDAO roleDao;
	
	public Map<String,String> LinkedHashMapBulider() {
		Map<String,String> map = new LinkedHashMap<>();
		
        map.put("/ajax/ajax.do", "anon");
	    map.put("/web/Theme/**", "anon");
	    map.put("/web/Common/js/**", "anon");
	    map.put("/web/js/**", "anon");
	    
	    map.put("/index.do", "rememberMe, user");
	    
	    map.put("/logout", "logout");
	    
	    Iterable<Role> roles = roleDao.findAll();
	    Iterator<Role> iterRoles = roles.iterator();
	    while(iterRoles.hasNext()) {
	    	Role role = iterRoles.next();
	    	Iterator<Menu> iterMenus = role.getMenus().iterator();
	    	if(iterMenus.hasNext()) {
	    		Menu menu = iterMenus.next();
	    		String url = menu.getUrl();
	    		if(map.containsKey(url)) {
	    			String roleValue = map.get(url);
	    			roleValue = roleValue.substring(0, roleValue.length()-1) + "," + role.getName() + "]";
	    			map.put(url, roleValue);
	    		}else {
	    			map.put(url, "roles[\"" + role.getName() + "\"]");
	    		}
	    	}
	    }
	    map.put("/**", "authc");
	    log.info("系统权限要求:" + map);
		return map;
	}
	
}
