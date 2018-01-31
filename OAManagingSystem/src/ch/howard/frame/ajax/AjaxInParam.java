package ch.howard.frame.ajax;

import java.util.Map;

public class AjaxInParam {
	
	private String service;
	private String method;
	private Map<String,Object> param;
	public String getService() {
		return service;
	}
	public void setService(String service) {
		this.service = service;
	}
	public String getMethod() {
		return method;
	}
	public void setMethod(String method) {
		this.method = method;
	}
	public Map<String, Object> getParam() {
		return param;
	}
	public void setParam(Map<String, Object> param) {
		this.param = param;
	}
	@Override
	public String toString() {
		return "AjaxInParam [service=" + service + ", method=" + method
				+ ", param=" + param + "]";
	}
}
