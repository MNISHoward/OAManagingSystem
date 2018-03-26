package ch.howard.frame.web;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

import org.apache.struts2.ServletActionContext;
import org.springframework.core.Constants;
import org.springframework.stereotype.Controller;

import com.opensymphony.xwork2.ActionSupport;

/**
 * 百度编译器文件下载
 * @author Administrator
 *
 */
@Controller
public class DownloadAction extends ActionSupport {
	private String fileName;
	public static final String PATH_CLASS_ROOT = Constants.class.getClassLoader().getResource("").getPath();    
	/*项目根路径*/  
	public static final String ROOT_Path = PATH_CLASS_ROOT.substring(0,PATH_CLASS_ROOT.length() - "WEB-INF\\classes\\".length());
	
	 public InputStream getInputStream() throws IOException{
        return new FileInputStream(new File( ROOT_Path + fileName));
    }
    public String getFileName() {
    	String name = fileName.substring(fileName.lastIndexOf("/") + 1);
        return name;
    }
    public void setFileName(String fileName) {
        this.fileName = fileName;
    }
}
