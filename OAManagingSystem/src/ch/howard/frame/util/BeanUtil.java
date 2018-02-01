package ch.howard.frame.util;

import ch.howard.frame.util.json.JsonUtil;

public class BeanUtil {

	public static <T> T convert(Object obj,Class<T> cls){
		String json = JsonUtil.convertToJson(obj);
		T t = JsonUtil.convertToObj(json, cls);
		return t;
	}
	
}