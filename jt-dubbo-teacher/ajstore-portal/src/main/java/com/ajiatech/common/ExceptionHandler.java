package com.ajiatech.common;

import java.io.PrintWriter;
import java.io.StringWriter;

//得异常详细信息
public class ExceptionHandler {
	public static void main(String[] args) {
		try {
			String string=null;
			string.toCharArray();
		} catch (Exception e) {
			handle(e);
		}		
	}

	public  static void handle(Throwable e){
		//得异常详细信息
		//String detail=e.getMessage();
		
		StringWriter stringWriter=new StringWriter();
		PrintWriter printWriter=new 
				PrintWriter(stringWriter);
		e.printStackTrace(printWriter);
		String detail=stringWriter.toString();
		System.out.println(detail);
		//保存到mysql
		//发邮件
		//发短信
	}
}
