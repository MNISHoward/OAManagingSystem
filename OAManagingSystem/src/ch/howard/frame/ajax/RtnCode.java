package ch.howard.frame.ajax;

public enum RtnCode {

	SUCCESS("1","成功"),ERROR("-1","失败"),WARNNING("2","提示"),NOLOGIN("3","未登录");
	
	private String key;
	private String value;
	
	private RtnCode(String key,String value){
		this.key = key;
		this.value = value;
	}

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}
}
