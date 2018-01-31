package ch.howard.frame.shiro.factory;

import java.util.LinkedHashMap;
import java.util.Map;

public class FilterChainFactoryBuilder {
	
	public Map<String,String> LinkedHashMapBulider() {
		Map<String,String> map = new LinkedHashMap<>();
        map.put("/login/admin", "roles[admin]");
	    map.put("/login/user", "roles[user]");
	    map.put("/index.jsp", "anon");
	    map.put("/login/index", "anon");
	    map.put("/login/in", "anon");
	    map.put("/login/logout", "logout");
	    map.put("/**", "authc");
		return map;
	}
	
}
