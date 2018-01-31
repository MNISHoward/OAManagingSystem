package ch.howard.frame.util.json;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

import ch.howard.frame.exception.AppException;

public class JsonUtil {
	
	private static Log log = LogFactory.getLog(JsonUtil.class);
	
	private static ObjectMapper objMapper = new ObjectMapper();
	
	static {
		objMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES,false);
	}
	
	/**
	 * convert bean to json
	 * @param obj
	 * @return
	 */
	public static String convertToJson(Object obj) {
		try {
			if(obj != null){
				return objMapper.writeValueAsString(obj);
			}else{
				return null;
			}
		} catch (JsonProcessingException e) {
			String message = e.getMessage();
			log.error(message,e);
			throw new AppException(message,e);
		}
	}
	
	/**
	 * convert json to bean
	 * @param json
	 * @param cls
	 * @return
	 */
	public static <T> T convertToObj(String json,Class<T> cls){
		try {
			if(json != null){
				return objMapper.readValue(json, cls);
			}else{
				return null;
			}
		} catch (Exception e) {
			String message = e.getMessage();
			log.error(message,e);
			throw new AppException(message,e);
		}
	}

}