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
//ÿ�η���һ����Ʒ��ʾҳ�棬Ҫ��ȡ���ݿ⣬Ҫ����service
	//��ҳ��̬�����ύ����Ʒҳ�����ɳ�����
	//������һ����̬ҳ���У��û����ʣ�ֱ�ӷ��ؾ�̬ҳ�棬
	//���ö����ݿ⣬��������service,controller
	
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
//��httpclient����web�����������ڿ�������
//�ٶ���������е�¼�ɹ����ò����û���Ϣ
//�����������һ�������У���������һ�����̣�����֮�䲻�ܻ������
System.out.println(json);
}

private static void test1() {
	System.out.println(
			HttpClientUtils
			.doGet("http://www.baidu.com"));
	
}
}
