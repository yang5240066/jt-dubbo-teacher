package com.ajiatech.common;

import java.io.PrintWriter;
import java.io.StringWriter;

//���쳣��ϸ��Ϣ
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
		//���쳣��ϸ��Ϣ
		//String detail=e.getMessage();
		
		StringWriter stringWriter=new StringWriter();
		PrintWriter printWriter=new 
				PrintWriter(stringWriter);
		e.printStackTrace(printWriter);
		String detail=stringWriter.toString();
		System.out.println(detail);
		//���浽mysql
		//���ʼ�
		//������
	}
}
