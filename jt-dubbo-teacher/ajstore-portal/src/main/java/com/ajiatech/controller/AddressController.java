package com.ajiatech.controller;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.ajiatech.pojo.AjiaResult;
import com.ajiatech.pojo.AjiaShipping;
import com.ajiatech.service.AddressService;

@Controller
public class AddressController {

	// 从spring 框架取service对象
	@Autowired
	AddressService addressService;
	
	@RequestMapping("/address/getDefault.html")
	@ResponseBody
	public AjiaResult getDefault() throws Exception
	{
		AjiaResult ajiaResult=new AjiaResult();
		AjiaShipping ajiaShipping=addressService
				.getDefaultByUserId(14L);
		if (ajiaShipping!=null){
			ajiaResult.setStatus(200);
			ajiaResult.setData(ajiaShipping);
		}else{
			ajiaResult.setStatus(500);
		}		
		return ajiaResult;
	}

	@RequestMapping("/address/setDefault.html")
	@ResponseBody
	// http://www.ajstore.com
	// /address/setDefault.html?addId=1
	public AjiaResult setDefault(Long addId) throws Exception {
		AjiaResult ajiaResult = new AjiaResult();
		int row = addressService.setDefault(14L, addId);
		if (row >= 1) {
			ajiaResult.setStatus(200);
		} else {
			ajiaResult.setStatus(500);
		}
		return ajiaResult;

	}

	@RequestMapping("/address/delete.html")
	@ResponseBody
	// http://www.ajstore.com/address/delete.html?addId=3
	public AjiaResult delete(Long addId) throws Exception {
		AjiaResult ajiaResult = new AjiaResult();
		int row = addressService.delete(addId);
		if (row >= 1) {
			ajiaResult.setStatus(200);
		} else {
			ajiaResult.setStatus(500);
		}
		return ajiaResult;
	}

	@RequestMapping("/address/insert.html")
	// http://www.ajstore.com/address/insert.html
	public ModelAndView Insert(AjiaShipping ajiaShipping) throws Exception {
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName("addressAdmin");
		// 接收的参数中没有userId,status,isDefault
		ajiaShipping.setUserId(14L);
		ajiaShipping.setStatus((byte) 1);
		ajiaShipping.setIsDefault(true);

		addressService.insert(ajiaShipping);
		return modelAndView;
	}

	// http://www.ajstore.com/address/list.html
	@RequestMapping("/address/list.html")
	public ModelAndView list() throws Exception {
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName("addressAdmin");
		List<AjiaShipping> list = addressService.selectByUserId(14L);
		System.out.println(list);
		modelAndView.addObject("list", list);
		return modelAndView;
	}

}
