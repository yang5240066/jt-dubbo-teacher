package com.ajiatech.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.ajiatech.pojo.AjiaOrder;

@Controller
public class ExceptionController {

	//�����쳣
	@RequestMapping("/e/test1.html")
	public ModelAndView test1() throws Exception
	{
		AjiaOrder ajiaOrder=new AjiaOrder();
		ajiaOrder.setUserId(14L);
		ModelAndView modelAndView=new ModelAndView("index");
		return modelAndView;		
	}
	//���쳣
	@RequestMapping("/e/test2.html")
	public ModelAndView test2() throws Exception
	{
		AjiaOrder ajiaOrder=new AjiaOrder();
		ajiaOrder.setUserId(null);
		ModelAndView modelAndView=new ModelAndView("index");
		return modelAndView;		
	}
	//���쳣
	@RequestMapping("/e/test3.html")
	public ModelAndView test3() throws Exception
	{
		AjiaOrder ajiaOrder=null;
		ajiaOrder.toString();
		ModelAndView modelAndView=new ModelAndView("index");
		return modelAndView;		
	}
}
