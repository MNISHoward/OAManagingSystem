package ch.howard.frame.filter;

import java.io.File;
import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

public class StartFilter implements Filter {

	@Override
	public void destroy() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void doFilter(ServletRequest arg0, ServletResponse arg1, FilterChain arg2)
			throws IOException, ServletException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void init(FilterConfig arg0) throws ServletException {
		File directory = new File("");//参数为空 
		 String cmd = "\\Chrome-bin\\chrome.exe -k --user-data-dir=\"UserData\" http://localhost:8080/OAManagingSystem";
	    	Runtime run = Runtime.getRuntime();
	    	try {
	    	String courseFile = directory.getCanonicalPath();
		   run.exec(courseFile+cmd);	
		} catch (IOException e) {	
		   e.printStackTrace();
		}
	}

}
