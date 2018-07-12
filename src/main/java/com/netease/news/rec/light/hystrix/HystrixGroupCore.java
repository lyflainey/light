package com.netease.news.rec.light.hystrix;

public enum HystrixGroupCore {
	TEST("test","test");
	
	/**消息类型*/
	private String type;
	/**消息说明*/
	private String desc;
	
	private HystrixGroupCore(String type,String desc){
		this.type=type;
		this.desc=desc;
	}
	
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getDesc() {
		return desc;
	}
	public void setDesc(String desc) {
		this.desc = desc;
	}
}
