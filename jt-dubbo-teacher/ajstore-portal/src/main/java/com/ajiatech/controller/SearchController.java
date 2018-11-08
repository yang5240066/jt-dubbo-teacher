package com.ajiatech.controller;

import java.util.List;

import org.apache.solr.client.solrj.impl.HttpSolrClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.ajiatech.pojo.AjiaItem;
import com.ajiatech.pojo.AjiaItemSolr;
import com.ajiatech.pojo.AjiaResult;
import com.ajiatech.service.ItemService;
import com.ajiatech.service.SearchService;

@Controller
public class SearchController {

	//tomcat-->listener-->spring/*.xml
	//从spring框架中取
//	@Autowired
//	HttpSolrClient httpSolrClient;
	
	@Autowired
	ItemService itemService;
	
	@Autowired
	SearchService searchService;
	
	@RequestMapping("/search/toSearch.html")
	public ModelAndView toSearch(String key) throws Exception
	{
		ModelAndView modelAndView=new ModelAndView();
		System.out.println(key);
		//浏览器 get请求发的字符串的编码是iso-8859-1
		byte[] data=key.getBytes("ISO-8859-1");
		//把字节转成utf-8
		key=new String(data, "utf-8");
		System.out.println(key);

		List<AjiaItemSolr> list=searchService.query(key);
		modelAndView.setViewName("search");
		modelAndView.addObject("list", list);
		return modelAndView;
	}
	
	@RequestMapping("/search/insert.html")
	@ResponseBody
	public AjiaResult insert() throws Exception
	{
		AjiaResult ajiaResult=new AjiaResult();
		//System.out.println(httpSolrClient);
		List<AjiaItem> list=itemService.selectAll();
		searchService.insert(list);
		return ajiaResult;
	}
}
