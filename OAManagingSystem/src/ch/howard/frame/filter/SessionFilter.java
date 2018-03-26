package ch.howard.frame.filter;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;

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

import ch.howard.frame.ajax.AjaxInParam;
import ch.howard.frame.ajax.AjaxOutParam;
import ch.howard.frame.ajax.RtnCode;
import ch.howard.frame.util.json.JsonUtil;

public class SessionFilter implements Filter {

	@Override
	public void destroy() {
		// TODO Auto-generated method stub
	}

	@Override
	public void doFilter(ServletRequest arg0, ServletResponse arg1, FilterChain arg2)
			throws IOException, ServletException {
		HttpServletRequest httpReq=(HttpServletRequest)arg0;  
		httpReq = new BodyReaderHttpServletRequestWrapper(httpReq);
        HttpServletResponse httpRes=(HttpServletResponse)arg1;  
        HttpSession httpSession=httpReq.getSession();  
        String url = httpReq.getRequestURI();
        
        String header = httpReq.getHeader("X-Requested-With");  
        boolean isAjax = "XMLHttpRequest".equals(header) ? true:false;
        
        if(!isAjax) {
        	
        	//非ajax
	        if(!url.contains(httpReq.getContextPath() + "/login.do") && httpSession.getAttribute("user")==null){  
	        	httpRes.sendRedirect(httpReq.getContextPath() + "/login.do");
	        }else{  
	        	arg2.doFilter(httpReq, arg1);  
	        }
        }else {
        	if(httpSession.getAttribute("user")==null) {
        		//ajax判断， 如果是登录界面的ajax则不需要登录，其他都需要用户登录 
            	String queryUrl = getRequestPayload(httpReq);
        		if(!queryUrl.equals("")) {
        			AjaxInParam ai = JsonUtil.convertToObj(queryUrl, AjaxInParam.class);
                	if(ai.getService().equals("userLoginService") || ai.getService().equals("userRegisterService")) {
                		arg2.doFilter(httpReq, arg1);  
                	}else {
                		httpRes.setHeader("noAuthentication", "true");
    		        	AjaxOutParam ao = new AjaxOutParam();
    		        	ao.setRtnCode(RtnCode.NOLOGIN.getKey());
    		        	ao.setRtnMessage("您还没有登录，请登录！");
    		        	httpRes.setContentType("application/json;charset=UTF-8");  
    			         PrintWriter writer = arg1.getWriter();  
    			         writer.write(JsonUtil.convertToJson(ao));
    			         writer.close();  
    			         httpRes.flushBuffer(); 
                	}
        		}else {
        			httpRes.setHeader("noAuthentication", "true");
		        	AjaxOutParam ao = new AjaxOutParam();
		        	ao.setRtnCode(RtnCode.NOLOGIN.getKey());
		        	ao.setRtnMessage("您还没有登录，请登录！");
		        	httpRes.setContentType("application/json;charset=UTF-8");  
			         PrintWriter writer = arg1.getWriter();  
			         writer.write(JsonUtil.convertToJson(ao));
			         writer.close();  
			         httpRes.flushBuffer(); 
        		}
        		
        	}else {
        		arg2.doFilter(httpReq, arg1); 
        	}
        }
	}

	@Override
	public void init(FilterConfig arg0) throws ServletException {
		// TODO Auto-generated method stub
		
	}
	
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
	
	/**
	 * 是因为获取request流的方法是不容许被重复使用的，不同获取流的方法，一次请求中只能使用一次，getReader(),getInputStream(),所以，当我们需要在一次请求中使用同一种方法多次读取request流中的数据时，就需要对request进行封装，使其可以多次被我们读取使用
	 * @author Administrator
	 *
	 */
	public class BodyReaderHttpServletRequestWrapper extends HttpServletRequestWrapper {

	    private final byte[] body;

	    public BodyReaderHttpServletRequestWrapper(HttpServletRequest request) throws IOException {
	        super(request);
	        ServletInputStream stream = request.getInputStream();
	        ByteArrayOutputStream swapStream = new ByteArrayOutputStream();
	        byte[] buff = new byte[100];
	        int rc = 0;
	        while ((rc = stream.read(buff, 0, 100)) > 0) {
	          swapStream.write(buff, 0, rc);
	        }
	        this.body = swapStream.toByteArray();
	    }

	    @Override
	    public BufferedReader getReader() throws IOException {
	        return new BufferedReader(new InputStreamReader(getInputStream()));
	    }

	    @Override
	    public ServletInputStream getInputStream() throws IOException {
	        final ByteArrayInputStream bais = new ByteArrayInputStream(body);
	        return new ServletInputStream() {

	            @Override
	            public int read() throws IOException {
	                return bais.read();
	            }

				@Override
				public boolean isFinished() {
					// TODO Auto-generated method stub
					return false;
				}

				@Override
				public boolean isReady() {
					// TODO Auto-generated method stub
					return false;
				}

				@Override
				public void setReadListener(ReadListener arg0) {
					// TODO Auto-generated method stub
					
				}
	        };
	    }

	}

}
