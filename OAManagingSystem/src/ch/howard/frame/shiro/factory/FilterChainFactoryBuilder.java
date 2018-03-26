package ch.howard.frame.shiro.factory;

import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import ch.howard.frame.model.Menu;
import ch.howard.frame.shiro.service.UserLoginService;
import ch.howard.rbac.dao.RoleDAO;
import ch.howard.rbac.model.Role;

@Component
public class FilterChainFactoryBuilder {
	
	private static final transient Logger log = LoggerFactory.getLogger(FilterChainFactoryBuilder.class);
	
	@Autowired
	private RoleDAO roleDao;
	
	public Map<String,String> LinkedHashMapBulider() {
		Map<String,String> map = new LinkedHashMap<>();
		
        map.put("/ajax/ajax.do", "anon");
	    map.put("/web/Theme/**", "anon");
	    map.put("/web/Common/js/**", "anon");
	    map.put("/web/Common/ueditor/**", "anon");
	    map.put("/web/js/**", "anon");
	    
	    map.put("/index.do", "rememberMe");
	    
	    map.put("/logout", "logout");
	    
	    Iterable<Role> roles = roleDao.findAll();
	    Iterator<Role> iterRoles = roles.iterator();
	    while(iterRoles.hasNext()) { //如果角色有菜单栏的权限，同时就具有该菜单栏的资源权限
	    	Role role = iterRoles.next();
	    	Iterator<Menu> iterMenus = role.getMenus().iterator();
	    	while(iterMenus.hasNext()) {
	    		Menu menu = iterMenus.next();
	    		String url = menu.getUrl();
	    		String reUrl = menu.getResource().getUrl();
	    		if(map.containsKey(url)) {  //判断linkedmap里是否有菜单栏的权限 
	    			String roleValue = map.get(url);
	    			roleValue = roleValue.substring(0, roleValue.length()-2) + "," + role.getName() + "\"]";
	    			map.put(url, roleValue);
	    			if(map.containsKey(reUrl)) { //判断linkedmap里是否有对应资源栏的权限
	    				String resRoleValue = map.get(reUrl);
	    				if(resRoleValue.indexOf(role.getName()) == -1) {
	    					resRoleValue = resRoleValue.substring(0, resRoleValue.length()-2) + "," + role.getName() + "\"]";
    						map.put(reUrl, resRoleValue);
	    				}
	    			}else {
	    				map.put(reUrl, "anyofroles[\"" + role.getName() + "\"]");
	    			}
	    		}else {
	    			map.put(url, "anyofroles[\"" + role.getName() + "\"]");
	    			if(map.containsKey(reUrl)) { //判断linkedmap里是否有对应资源栏的权限
	    				String resRoleValue = map.get(reUrl);
	    				if(resRoleValue.indexOf(role.getName()) == -1) {
		    				resRoleValue = resRoleValue.substring(0, resRoleValue.length()-2) + "," + role.getName() + "\"]";
		    				map.put(reUrl, resRoleValue);
	    				}
	    			}else {
	    				map.put(reUrl, "anyofroles[\"" + role.getName() + "\"]");
	    			}
	    		}
	    	}
	    }
	    map.put("/**", "authc");
	    log.info("系统权限要求:" + map);
		return map;
	}
	
}
