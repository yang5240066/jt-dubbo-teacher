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

	//return true:继续处理
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		//判断有没有cookie,得不到cookie显示登录界面
		//重启服务器，浏览器
		//1,访问我的订单，应该显示登录页面
		//2,登录后再访问我的订单，应该显示我的订单
		String from=request.getHeader("referer");
		String loginUrl="http://sso.ajstore.com:90"
				+ "/user/toLogin.html?callback="+from;
		String ticket=CookieUtils.getCookieValue(request, GlobalConst.COOKIE_NAME);
		if (StringUtils.isEmpty(ticket))
		{
			response.sendRedirect(loginUrl);
			return false;//不继续处理
		}else{	
		//去sso取用户信息
			List<String> cookies=new ArrayList<>();
			cookies.add(GlobalConst.COOKIE_NAME+"="+ticket);
			String url="http://sso.ajstore.com:90"
					+ "/user/checkLoginForHttpClient.html";
			String json=HttpClientUtils.doGet
					(url, null, cookies);
			if (StringUtils.isEmpty(json))
			{
				response.sendRedirect(loginUrl);
				return false;//不继续处理
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
