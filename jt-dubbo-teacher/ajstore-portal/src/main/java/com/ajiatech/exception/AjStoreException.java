package com.ajiatech.exception;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.tags.EscapeBodyTag;

import com.ajiatech.common.ExceptionHandler;

//ȫ���쳣������
public class AjStoreException implements HandlerExceptionResolver {

	@Override
	public ModelAndView resolveException(HttpServletRequest request, HttpServletResponse response, Object handler,
			Exception ex) {
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName("exception");
		String message;
		// �ж��쳣�ǲ����Զ����
		if (ex instanceof StoreException) {
			// �Զ����쳣����ϸ��Ϣ
			message = ex.getMessage();
		} else {
			// ϵͳ�쳣��ʾδ֪����
			message = "δ֪����";
		}
		// �����쳣��ϸ��Ϣ
		ExceptionHandler.handle(ex);
		modelAndView.addObject("message", message);
		return modelAndView;
	}

}
