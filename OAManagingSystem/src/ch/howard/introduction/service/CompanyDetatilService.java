package ch.howard.introduction.service;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.Constants;
import org.springframework.stereotype.Service;
import org.springframework.web.util.HtmlUtils;

import ch.howard.introduction.model.CompanyDetail;

@Service
public class CompanyDetatilService {
	private static final transient Logger log = LoggerFactory.getLogger(CompanyDetatilService.class);
	private transient Map<String,Object> outMap = new HashMap<String, Object>();
	
	public static final String PATH_CLASS_ROOT = Constants.class.getClassLoader().getResource("").getPath();    
	/*项目根路径*/  
	public static final String ROOT_Path = PATH_CLASS_ROOT.substring(0,PATH_CLASS_ROOT.length() - "WEB-INF\\classes\\".length());
	
	
	public File getFile(String fileName) throws IOException {
		File f = null;
		f = new File(ROOT_Path+"/file");
		if(!f.exists()) {
			f.mkdir();
		}
		f = new File(f, fileName);
		if(!f.exists()) {
			f.createNewFile();
		}
		
		return f;
		
	}
	
	
	public void saveCompanyDetail(CompanyDetail companyDetail) {
		File f = null;
		try {
			f = getFile("companyDetail.data");
		} catch (IOException e) {
			log.error("文件不存在，并创建失败");
			e.printStackTrace();
		}
		 try {
			ObjectOutputStream oos=new ObjectOutputStream(new FileOutputStream(f));
			oos.writeObject(companyDetail);
			oos.close();
		} catch (FileNotFoundException e) {
			log.error("文件不存在");
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} 
	}
	
	public CompanyDetail readCompanyDetail() {
		CompanyDetail companyDetail = null;
		File f = null;
		try {
			f = getFile("companyDetail.data");
		} catch (IOException e) {
			log.error("文件不存在，并创建失败");
			e.printStackTrace();
		}
		
		try {
			ObjectInputStream ois=new ObjectInputStream(new FileInputStream(f));
			companyDetail = (CompanyDetail) ois.readObject();
		} catch (FileNotFoundException e) {
			log.error("文件不存在");
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		
		return companyDetail;
	}
	
}
