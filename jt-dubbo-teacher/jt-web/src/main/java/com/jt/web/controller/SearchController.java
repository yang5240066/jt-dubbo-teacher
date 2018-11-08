package com.jt.web.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.jt.search.pojo.Item;
import com.jt.search.service.SearchService;

@Controller
public class SearchController {
	
	//由dubbo来提供searchService,
	
	@Autowired
	SearchService searchService;
	
	@RequestMapping("/search")
	public String search(String q,Model model) throws Exception{
		System.out.println("q    "+q);
		//chrome get 发信息的默认编码是iso-8859-1
		byte[] data = q.getBytes("ISO8859-1");
		String key = new String(data, "UTF-8");
		//dubbo消费者发信息给提供者,字符的编码是utf-8
		//调用微服务的提供者
		List<Item> itemList = searchService.findItemByKey(key);
		model.addAttribute("itemList", itemList);
		return "search";
	}
	
}
