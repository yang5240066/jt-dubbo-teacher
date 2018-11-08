package com.ajiatech;

import java.io.DataOutputStream;
import java.io.File;
import java.io.FileOutputStream;

import com.ajiatech.common.utils.HttpClientUtils;

public class TestHttpClient {
public static void main(String[] args) throws Exception{
	//test1();
	//test2();
	test3();
}

private static void test3() throws Exception{
//每次访问一个商品显示页面，要读取数据库，要运行service
	//网页静态化，提交把商品页面生成出来，
	//保存在一个静态页面中，用户访问，直接返回静态页面，
	//不用读数据库，不用运行service,controller
	
	String[] idList={"10000028","10000029","10000030"};
	for (String itemId:idList)
	{
		String url="http://www.ajstore.com/item"
				+ "/toItemInfo.html?itemId="+itemId;
		String html=HttpClientUtils.doGet(url);
		String path="E:\\apache-tomcat-7.0.82-windows-x64"
				+ "\\apache-tomcat-7.0.82\\webapps"
				+ "\\ajstore-portal\\static\\";
		path=path+itemId+".html";
		File file=new File(path);
		FileOutputStream fileOutputStream=
				new FileOutputStream(file);
		DataOutputStream dataOutputStream=
				new DataOutputStream(fileOutputStream);
		dataOutputStream.writeUTF(html);
		dataOutputStream.close();
		}
}

private static void test2() {
String url="http://sso.ajstore.com:90/user/checkLogin.html";
String json=HttpClientUtils.doGet(url);
//用httpclient访问web服务器不存在跨域问题
//假定在浏览器中登录成功，得不到用户信息
//浏览器运行在一个进程中，运行在另一个进程，进程之间不能互相访问
System.out.println(json);
}

private static void test1() {
	System.out.println(
			HttpClientUtils
			.doGet("http://www.baidu.com"));
	
}
}
