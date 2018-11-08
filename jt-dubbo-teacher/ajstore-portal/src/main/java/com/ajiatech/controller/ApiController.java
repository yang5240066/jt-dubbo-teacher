package com.ajiatech.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ajiatech.pojo.AjiaItem;
import com.ajiatech.pojo.AjiaResult;
import com.ajiatech.pojo.DetailVO;
import com.ajiatech.service.ApiService;

@Controller
@RequestMapping("/api")
public class ApiController {

	@Autowired
	ApiService apiService;
	//   http://172.211.100.32/api/getItemDetail
	//     .html?itemId=10000028
	@RequestMapping("/getItemDetail.html")
	@ResponseBody
	public AjiaResult getItemDetail(Long itemId) 
			throws Exception
	{
		AjiaResult ajiaResult=new AjiaResult();
		DetailVO detailVO=apiService.getItemDetail(itemId);
		ajiaResult.setData(detailVO);
		ajiaResult.setStatus(200);
		return ajiaResult;
	}
	
	@RequestMapping("/getIndexItem.html")
	@ResponseBody
	public AjiaResult getIndexItem() throws Exception
	{
		AjiaResult ajiaResult=new AjiaResult();
		List<AjiaItem> list=apiService.getIndexItem();
//		for (AjiaItem ajiaItem:list)
//		{
//			String detailUrl="detail.html?itemId="
//		+ajiaItem.getId();
//			ajiaItem.setDetailUrl(detailUrl);
//		}
		ajiaResult.setData(list);
		ajiaResult.setStatus(200);
		return ajiaResult;
		
	}
}
