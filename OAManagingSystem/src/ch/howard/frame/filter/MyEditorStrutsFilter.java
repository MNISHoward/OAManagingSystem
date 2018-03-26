package ch.howard.frame.filter;
import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.dispatcher.filter.StrutsPrepareAndExecuteFilter;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;

import ch.howard.frame.ajax.AjaxOutParam;
import ch.howard.frame.ajax.RtnCode;
import ch.howard.frame.util.json.JsonUtil;
 
/**
 * 为了防止百度编译上传文件的文件被struts2拦截
 * @author Administrator
 *
 */
public class MyEditorStrutsFilter extends StrutsPrepareAndExecuteFilter{
    @Override
    public void doFilter(ServletRequest req, ServletResponse res,
            FilterChain chain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) req;
        String url = request.getRequestURI();
	        if (url.contains(request.getContextPath()+"/web/Common/ueditor/jsp/")) {
	            chain.doFilter(req, res);         
	        }else{             
	            super.doFilter(req, res, chain);         
	        }

    }
}