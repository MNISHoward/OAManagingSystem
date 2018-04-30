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
import ch.howard.frame.wrapper.MyRequestWrapper;
 
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
        String header = request.getHeader("X-Requested-With");  
        boolean isAjax = "XMLHttpRequest".equals(header) ? true:false;
        if(isAjax) {//由于request流已经被读取过一次，所以要进行封装，不然会has called错误
        	super.doFilter(new MyRequestWrapper(request), res, chain);  
        }else {
        	if (url.contains(request.getContextPath()+"/web/Common/ueditor/jsp/")) {
	            chain.doFilter(req, res);      //不能用装饰器装饰，不然modeldriven不能获取数据   
	        }else{             
	            super.doFilter(req, res, chain);         
	        }
        }
    }
}