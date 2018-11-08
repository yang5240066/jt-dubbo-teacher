package com.ajiatech.exception;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.tags.EscapeBodyTag;

import com.ajiatech.common.ExceptionHandler;

//全局异常处理类
public class AjStoreException implements HandlerExceptionResolver {

	@Override
	public ModelAndView resolveException(HttpServletRequest request, HttpServletResponse response, Object handler,
			Exception ex) {
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName("exception");
		String message;
		// 判断异常是不是自定义的
		if (ex instanceof StoreException) {
			// 自定义异常得详细信息
			message = ex.getMessage();
		} else {
			// 系统异常显示未知错误
			message = "未知错误";
		}
		// 保存异常详细信息
		ExceptionHandler.handle(ex);
		modelAndView.addObject("message", message);
		return modelAndView;
	}

}
