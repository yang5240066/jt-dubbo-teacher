package com.ajiatech.pojo;

public class AjiaResult {
	// 200成功 500失败
	private int status;
	// 对状态的描述
	private String msg;
	// 返回给浏览器的数据
	private Object data;
	
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	public String getMsg() {
		return msg;
	}
	public void setMsg(String msg) {
		this.msg = msg;
	}
	public Object getData() {
		return data;
	}
	public void setData(Object data) {
		this.data = data;
	}
	
	
}
