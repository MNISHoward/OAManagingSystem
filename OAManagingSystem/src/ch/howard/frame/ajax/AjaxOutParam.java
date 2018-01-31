package ch.howard.frame.ajax;

import java.util.Map;

public class AjaxOutParam {

	private String rtnCode;
	private String rtnMessage;
	private Map<String,Object> param;
	public String getRtnCode() {
		return rtnCode;
	}
	public void setRtnCode(String rtnCode) {
		this.rtnCode = rtnCode;
	}
	public String getRtnMessage() {
		return rtnMessage;
	}
	public void setRtnMessage(String rtnMessage) {
		this.rtnMessage = rtnMessage;
	}
	public Map<String, Object> getParam() {
		return param;
	}
	public void setParam(Map<String, Object> param) {
		this.param = param;
	}
	@Override
	public String toString() {
		return "AjaxOutParam [rtnCode=" + rtnCode + ", rtnMessage="
				+ rtnMessage + ", param=" + param + "]";
	}
	
}
