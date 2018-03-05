package ch.howard.frame.util;

import java.util.Collection;
import java.util.Map;

public class Util {
	public static boolean isEmpty(Object obj){
		if(obj == null){
			return true;
		}else{
			if(obj instanceof String){
				String str = (String) obj;
				return isEmpty(str);
			}else if(obj instanceof Collection){
				Collection collection = (Collection) obj;
				return isEmpty(collection);
			}
			
			return false;
		}
	}
	
	private static boolean _isEmpty(String str){
		if(str == null){
			return true;
		}else{
			return str.equals("");
		}
	}
	
	public static boolean isEmpty(String str){
		return isEmpty(str,true);
	}
	
	public static boolean isEmpty(String str,boolean ingoreSpace){
		if(str == null){
			return true;
		}else if(ingoreSpace){
			str = str.trim();
			return _isEmpty(str);
		}else{
			return _isEmpty(str);
		}
	}
	
	public static boolean isEmpty(Collection collection){
		if(collection == null){
			return true;
		}else{
			return collection.isEmpty();
		}
	}
	
	public static String getType(Object o){  
        return o.getClass().toString();  
    }  
    public static String getType(int o){  
    	return "int";  
    }  
    public static String getType(byte o){  
    	return "byte";  
    }  
    public static String getType(char o){  
    	return "char";  
    }  
    public static String getType(double o){  
    	return "double";  
    }  
    public static String getType(float o){  
    	return "float";  
    }  
    public static String getType(long o){  
    	return "long";  
    }  
    public static String getType(boolean o){  
    	return "boolean";  
    }  
    public static String getType(short o){  
    	return "short";  
    }  
    public static String getType(String o){  
        return "String";  
    }
    
    public static Object mapToObject(Map<String, Object> map, Class<?> beanClass) throws Exception {    
        if (map == null)  
            return null;  
  
        Object obj = beanClass.newInstance();  
  
        org.apache.commons.beanutils.BeanUtils.populate(obj, map);  
  
        return obj;  
    }    
      
    public static Map<?, ?> objectToMap(Object obj) {  
        if(obj == null)  
            return null;   
  
        return new org.apache.commons.beanutils.BeanMap(obj);  
    }    
}
