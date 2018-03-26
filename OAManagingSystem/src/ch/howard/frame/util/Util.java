package ch.howard.frame.util;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
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
    
    /**
     * 该日期和现在相差多少日
     * @param end
     * @return
     */
    public static int differentedDay(Date end) {
    	Calendar cal1 = Calendar.getInstance();
        cal1.setTime(new Date());
        
        Calendar cal2 = Calendar.getInstance();
        cal2.setTime(end);
        int day1= cal1.get(Calendar.DAY_OF_YEAR);
        int day2 = cal2.get(Calendar.DAY_OF_YEAR);
        
        int year1 = cal1.get(Calendar.YEAR);
        int year2 = cal2.get(Calendar.YEAR);
        if(year1 != year2)   //同一年
        {
            int timeDistance = 0 ;
            for(int i = year1 ; i < year2 ; i ++)
            {
                if(i%4==0 && i%100!=0 || i%400==0)    //闰年            
                {
                    timeDistance += 366;
                }
                else    //不是闰年
                {
                    timeDistance += 365;
                }
            }
            
            return timeDistance + (day2-day1) ;
	        }
	        else    //不同年
	        {
	            return day2-day1;
	        }
    }
    
    /**
     * 对时间格式化
     * @param date
     * @return
     */
    public static String dateFormat(Date date) {
    	SimpleDateFormat time=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    	String format = time.format(date);
    	return format;
    }
}
