package com.ajiatech.controller;

import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import javax.imageio.spi.RegisterableService;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.ajiatech.common.GlobalConst;
import com.ajiatech.common.utils.CookieUtils;
import com.ajiatech.common.utils.DesUtil;
import com.ajiatech.common.utils.HttpClientUtils;
import com.ajiatech.common.utils.JsonUtils;
import com.ajiatech.common.utils.MD5Encrypt;
import com.ajiatech.pojo.AjiaResult;
import com.ajiatech.pojo.AjiaUser;
import com.ajiatech.service.UserService;

@Controller
public class UserController {
	@RequestMapping("/user/checkLogin.html")
	public void checkLogin
	(HttpServletRequest request
			,HttpServletResponse response) 
					throws Exception
	{
		//��������н���cookie
		String ticket=CookieUtils.getCookieValue
				(request, GlobalConst.COOKIE_NAME);
		List<String> cookies=new ArrayList<>();
		String json="";
		if (!StringUtils.isEmpty(ticket)){
			cookies.add(GlobalConst.COOKIE_NAME+"="+ticket);
			//��httpClient�����ݸ�sso
			String url="http://sso.ajstore.com:90"
					+ "/user/checkLoginForHttpClient.html";
			json=HttpClientUtils.doGet(url, null, cookies);
		}else{
			AjiaResult ajiaResult=new AjiaResult();
			ajiaResult.setStatus(500);
			json=JsonUtils.objectToJson(ajiaResult);
		}
		
		//��sso���ص����ݷ��ظ������
		PrintWriter out=response.getWriter();
		out.println(json);
		out.close();
	}
}
