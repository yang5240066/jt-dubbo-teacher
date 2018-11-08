package com.ajiatech.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.ajiatech.pojo.AjiaCartItem;
import com.ajiatech.pojo.AjiaResult;
import com.ajiatech.pojo.ItemVO;
import com.ajiatech.service.CartService;

@Controller
@RequestMapping("/cart")
public class CartController {
	@Autowired
	CartService cartService;
	
	@RequestMapping("/batchDelete.html")
	@ResponseBody
	//www.ajstore.com/cart/batchDelete.html
	//?itemId=10000028,10000030
	public AjiaResult batchDelete(String itemIds) throws Exception
	{
		AjiaResult ajiaResult=new AjiaResult();
		//itemId=28,30
		String[] array=itemIds.split("\\,");
		List<Long> list=new ArrayList<>();
		for (String string:array)
		{
			list.add(Long.parseLong(string));
		}
		int row=cartService.batchDelete(14L, list);
		if (row>=1)
		{
			ajiaResult.setStatus(200);
		}else {
			ajiaResult.setStatus(500);
		}
		return ajiaResult;
	}
	
	@RequestMapping("/changeNum.html")
	@ResponseBody
	public AjiaResult changeNum(Long itemId,int num)
			throws Exception
	{
		AjiaResult ajiaResult=new AjiaResult();
		AjiaCartItem ajiaCartItem=new AjiaCartItem();
		ajiaCartItem.setUserId(14L);
		ajiaCartItem.setItemId(itemId);
		ajiaCartItem.setNum(num);
		ajiaCartItem.setStatus(1);
		ajiaCartItem.setUpdated(new Date());
		int row=cartService.updateNum(ajiaCartItem);
		if (row>=1)
		{
			ajiaResult.setStatus(200);
		}else{
			ajiaResult.setStatus(500);
		}
		return ajiaResult;
	}

	@RequestMapping("/toCart.html")
	public ModelAndView toCart() throws Exception {
		ModelAndView modelAndView = new ModelAndView("cart");
		List<ItemVO> itemVOs = cartService.selectByUserId(14L);
		modelAndView.addObject("itemVOs", itemVOs);
		return modelAndView;
	}

	@RequestMapping("/insert.html")
	@ResponseBody
	public AjiaResult insert(Long itemId, int num) throws Exception {
		AjiaResult ajiaResult = new AjiaResult();
		// 创建实体类
		AjiaCartItem ajiaCartItem = new AjiaCartItem();
		ajiaCartItem.setStatus(1);
		ajiaCartItem.setUserId(14L);
		ajiaCartItem.setItemId(itemId);
		ajiaCartItem.setNum(num);
		ajiaCartItem.setCreated(new Date());
		int row = cartService.insert(ajiaCartItem);
		if (row >= 1) {
			ajiaResult.setStatus(200);
		} else {
			ajiaResult.setStatus(500);
		}
		return ajiaResult;
	}
}
