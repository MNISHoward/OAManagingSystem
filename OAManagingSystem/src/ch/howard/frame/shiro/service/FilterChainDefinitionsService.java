package ch.howard.frame.shiro.service;

import java.util.Map;

import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.util.CollectionUtils;
import org.apache.shiro.web.filter.mgt.DefaultFilterChainManager;
import org.apache.shiro.web.filter.mgt.PathMatchingFilterChainResolver;
import org.apache.shiro.web.servlet.AbstractShiroFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ch.howard.frame.shiro.factory.FilterChainFactoryBuilder;

@Service
public class FilterChainDefinitionsService {
	@Autowired
    private ShiroFilterFactoryBean shiroFilterFactoryBean;
	@Autowired
	private FilterChainFactoryBuilder filterChainFactoryBuilder;
	
	private String definitions = "";  
	
	public void reloadFilterChains() {
        synchronized (shiroFilterFactoryBean) {   //强制同步，控制线程安全
            AbstractShiroFilter shiroFilter = null;

            try {
                shiroFilter = (AbstractShiroFilter) shiroFilterFactoryBean.getObject();

                PathMatchingFilterChainResolver resolver = (PathMatchingFilterChainResolver) shiroFilter
                        .getFilterChainResolver();
                // 过滤管理器
                DefaultFilterChainManager manager = (DefaultFilterChainManager) resolver.getFilterChainManager();
                // 清除权限配置
                manager.getFilterChains().clear();
                shiroFilterFactoryBean.getFilterChainDefinitionMap().clear();
                // 重新设置权限
                shiroFilterFactoryBean.setFilterChainDefinitionMap(filterChainFactoryBuilder.LinkedHashMapBulider());//传入配置中的filterchains

                Map<String, String> chains = shiroFilterFactoryBean.getFilterChainDefinitionMap();
                //重新生成过滤链
                if (!CollectionUtils.isEmpty(chains)) {
                    chains.forEach((url, definitionChains) -> {
                        manager.createChain(url, definitionChains.trim().replace(" ", ""));
                    });
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
