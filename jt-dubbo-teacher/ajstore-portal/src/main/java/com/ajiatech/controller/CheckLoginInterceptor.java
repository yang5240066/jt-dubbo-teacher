package com.ajiatech.controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.util.StringUtils;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import com.ajiatech.common.GlobalConst;
import com.ajiatech.common.utils.CookieUtils;
import com.ajiatech.common.utils.HttpClientUtils;
import com.ajiatech.common.utils.JsonUtils;
import com.ajiatech.pojo.AjiaUserResult;

public class CheckLoginInterceptor 
implements HandlerInterceptor{

	//return true:��������
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		//�ж���û��cookie,�ò���cookie��ʾ��¼����
		//�����������������
		//1,�����ҵĶ�����Ӧ����ʾ��¼ҳ��
		//2,��¼���ٷ����ҵĶ�����Ӧ����ʾ�ҵĶ���
		String from=request.getHeader("referer");
		String loginUrl="http://sso.ajstore.com:90"
				+ "/user/toLogin.html?callback="+from;
		String ticket=CookieUtils.getCookieValue(request, GlobalConst.COOKIE_NAME);
		if (StringUtils.isEmpty(ticket))
		{
			response.sendRedirect(loginUrl);
			return false;//����������
		}else{	
		//ȥssoȡ�û���Ϣ
			List<String> cookies=new ArrayList<>();
			cookies.add(GlobalConst.COOKIE_NAME+"="+ticket);
			String url="http://sso.ajstore.com:90"
					+ "/user/checkLoginForHttpClient.html";
			String json=HttpClientUtils.doGet
					(url, null, cookies);
			if (StringUtils.isEmpty(json))
			{
				response.sendRedirect(loginUrl);
				return false;//����������
			}else
			{
				AjiaUserResult ajiaUserResult=
						JsonUtils.jsonToPojo
						(json, AjiaUserResult.class);
				request.setAttribute("ajiaUserResult",
						ajiaUserResult);
			}
			
		}
		return true;
	}

	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)
			throws Exception {
		// TODO Auto-generated method stub
		
	}

}
