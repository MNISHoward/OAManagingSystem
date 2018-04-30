package ch.howard.frame.interceptor;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.Map;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ReadListener;
import javax.servlet.ServletException;
import javax.servlet.ServletInputStream;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.shiro.SecurityUtils;
import org.apache.struts2.ServletActionContext;
import org.apache.struts2.dispatcher.filter.StrutsPrepareAndExecuteFilter;
import org.apache.struts2.json.JSONUtil;

import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.interceptor.AbstractInterceptor;

import ch.howard.frame.ajax.AjaxInParam;
import ch.howard.frame.ajax.AjaxOutParam;
import ch.howard.frame.ajax.RtnCode;
import ch.howard.frame.util.json.JsonUtil;
import ch.howard.frame.wrapper.MyRequestWrapper;

public class SessionInterceptor extends AbstractInterceptor{
	
	private String getRequestPayload(HttpServletRequest req) {  
        StringBuilder sb = new StringBuilder();  
        try{  
            BufferedReader reader = req.getReader();
             String line;  
             while((line = reader.readLine()) != null) {  
                      sb.append(line);
             }  
             reader.close();
        }catch (IOException e) {  
                 e.printStackTrace();  
        }  
        return sb.toString();  
    }
	
	@Override
	public String intercept(ActionInvocation arg0) throws Exception {
		HttpServletRequest httpReq=ServletActionContext.getRequest();  
		httpReq = new MyRequestWrapper(httpReq);
        HttpServletResponse httpRes=ServletActionContext.getResponse();  
        HttpSession httpSession=httpReq.getSession();  
        String url = httpReq.getRequestURI();
        
        String header = httpReq.getHeader("X-Requested-With");  
        boolean isAjax = "XMLHttpRequest".equals(header) ? true:false;
        
        if(!isAjax) {
        	
        	//非ajax
	        if(!url.contains(httpReq.getContextPath() + "/login.do") && httpSession.getAttribute("user")==null){
	        	SecurityUtils.getSubject().logout();
	        	return "login";
	        }else{  
	        	return arg0.invoke(); 
	        }
        }else {
        	if(httpSession.getAttribute("user")==null) {
        		//ajax判断， 如果是登录界面的ajax则不需要登录，其他都需要用户登录 
            	String queryUrl = getRequestPayload(httpReq);
        		if(!queryUrl.equals("")) {
        			AjaxInParam ai = (AjaxInParam) JsonUtil.convertToObj(queryUrl, AjaxInParam.class);
                	if(ai.getService().equals("userLoginService") || ai.getService().equals("userRegisterService")) {
                		return arg0.invoke();    
                	}else {
                		SecurityUtils.getSubject().logout();
                		httpRes.setHeader("noAuthentication", "true");
    		        	AjaxOutParam ao = new AjaxOutParam();
    		        	ao.setRtnCode(RtnCode.NOLOGIN.getKey());
    		        	ao.setRtnMessage("您还没有登录，请登录！");
    		        	httpRes.setContentType("application/json;charset=UTF-8");  
    			        PrintWriter writer = httpRes.getWriter();  
    			        writer.write(JsonUtil.convertToJson(ao));
    			        writer.close();  
    			        httpRes.flushBuffer();
    			        return null;
                	}
        		}else {
        			SecurityUtils.getSubject().logout();
        			httpRes.setHeader("noAuthentication", "true");
		        	AjaxOutParam ao = new AjaxOutParam();
		        	ao.setRtnCode(RtnCode.NOLOGIN.getKey());
		        	ao.setRtnMessage("您还没有登录，请登录！");
		        	httpRes.setContentType("application/json;charset=UTF-8");  
			         PrintWriter writer = httpRes.getWriter();  
			         writer.write(JsonUtil.convertToJson(ao));
			         writer.close();  
			         httpRes.flushBuffer();
			         return "login";
        		}
        		
        	}else {
        		return arg0.invoke(); 
        	}
        }
	}

}
