package ch.howard.frame.util;

import net.sf.ehcache.Cache;
import net.sf.ehcache.CacheManager;

public class EhcacheUtil {
	private static CacheManager cacheManager = null;
	
	static {
		cacheManager = CacheManager.create();
	}

	public static CacheManager getCacheManager() {
		return cacheManager;
	}
	
	public static Cache getCache(String name) {
		return cacheManager.getCache(name);
	}
	
}
