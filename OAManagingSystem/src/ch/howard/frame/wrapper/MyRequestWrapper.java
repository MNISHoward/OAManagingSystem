package ch.howard.frame.wrapper;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import javax.servlet.ReadListener;
import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;

/**
	 * 是因为获取request流的方法是不容许被重复使用的，不同获取流的方法，一次请求中只能使用一次，getReader(),getInputStream(),所以，当我们需要在一次请求中使用同一种方法多次读取request流中的数据时，就需要对request进行封装，使其可以多次被我们读取使用
	 * @author Administrator
	 *
	 */
	public class MyRequestWrapper extends HttpServletRequestWrapper {
		 private final String body;
		 public MyRequestWrapper(HttpServletRequest request) throws IOException {
		   super(request);
		   StringBuilder stringBuilder = new StringBuilder();
		   BufferedReader bufferedReader = null;
		   try {
		     InputStream inputStream = request.getInputStream();
		     if (inputStream != null) {
		       bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
		       char[] charBuffer = new char[128];
		       int bytesRead = -1;
		       while ((bytesRead = bufferedReader.read(charBuffer)) > 0) {
		         stringBuilder.append(charBuffer, 0, bytesRead);
		       }
		     } else {
		       stringBuilder.append("");
		     }
		   } catch (IOException ex) {
		       throw ex;
		   } finally {
		     if (bufferedReader != null) {
		       try {
		         bufferedReader.close();
		       } catch (IOException ex) {
		         throw ex;
		       }
		     }
		   }
		   body = stringBuilder.toString();
		 }

		 @Override
		 public ServletInputStream getInputStream() throws IOException {
		   final ByteArrayInputStream byteArrayInputStream = new     ByteArrayInputStream(body.getBytes());
		   ServletInputStream servletInputStream = new ServletInputStream() {
		     public int read() throws IOException {
		       return byteArrayInputStream.read();
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
		   return servletInputStream;
		 }

		 @Override
		 public BufferedReader getReader() throws IOException {
		   return new BufferedReader(new InputStreamReader(this.getInputStream()));
		 }

		 public String getBody() {
		   return this.body;
		 }
		}