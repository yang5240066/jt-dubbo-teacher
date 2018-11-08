package com.ajiatech.pojo;

public class AjiaUserResult {
	int status;
	String msg;
	AjiaUser data;
	
	
	@Override
	public String toString() {
		return "AjiaUserResult [status=" + status + ", msg=" + msg + ", data=" + data + "]";
	}
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
	public AjiaUser getData() {
		return data;
	}
	public void setData(AjiaUser data) {
		this.data = data;
	}
	
	

}
