package com.ajiatech.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.ajiatech.common.utils.JsonUtils;
import com.ajiatech.pojo.AjiaItem;
import com.ajiatech.pojo.AjiaItemDesc;
import com.ajiatech.pojo.AjiaItemParam;
import com.ajiatech.pojo.AjiaItemParamItem;
import com.ajiatech.pojo.AjiaResult;
import com.ajiatech.pojo.paramData.AjiaItemParamData;
import com.ajiatech.pojo.paramData.Params;
import com.ajiatech.service.ItemService;

@Controller
@RequestMapping("/item")
public class ItemController {
	//从spring框架中取ItemService
	@Autowired
	ItemService itemService;
	//www.ajstore.com/item/getItemId.html
	@RequestMapping("/getItemId.html")
	@ResponseBody
	public AjiaResult getItemId(String color,
			String model,
			String itemParamId) throws Exception
	{
		AjiaResult ajiaResult=new AjiaResult();
		Long itemId=10000033L;
		
		//调service从数据库中取出商品id
		itemId=itemService.selectItemByParams(color, model,
				Long.parseLong(itemParamId));
		ajiaResult.setData(itemId);
		return ajiaResult;
	}
	@RequestMapping("/toItemInfo.html")
	public ModelAndView toItemInfo(Long itemId) throws Exception
	{
		ModelAndView modelAndView=new ModelAndView();
		//把商品编号放到request中
		modelAndView.addObject("itemId", itemId);
		
		
		AjiaItemParamItem ajiaItemParamItem=itemService
				.getParamDataById(itemId);
		String paramData=ajiaItemParamItem.getParamData();
		List<AjiaItemParamData> list=JsonUtils
				.jsonToList(paramData, AjiaItemParamData.class);
		//把商品类型id保存到request中 
		Long itemParamId=ajiaItemParamItem.getItemParamId();
		modelAndView.addObject("itemParamId", itemParamId);
		//从list中取出当前商品的参数
		//[{params:[{}]},{}]
		String itemColor="",itemModel="";
		for (Params params:list.get(0).getParams())
		{
			if (params.getKey().equals("颜色") 
					&& params.getValues()!=null)
			{
				itemColor=params.getValues().get(0);
			}
			if (params.getKey().equals("型号") 
					&& params.getValues()!=null)
			{
				itemModel=params.getValues().get(0);
			}
		}
		modelAndView.addObject("itemColor", itemColor);
		modelAndView.addObject("itemModel", itemModel);
		
		//取商品信息
		AjiaItem ajiaItem=itemService
				.selectItemById(itemId);
		modelAndView.addObject("ajiaItem", ajiaItem);
		
		//取描述图片
		AjiaItemDesc ajiaItemDesc=itemService
				.selectDescById(itemId);
		String desc=ajiaItemDesc.getItemDesc();
		modelAndView.addObject("desc", desc);
		
		//取商品类型的参数
		itemParamId=ajiaItemParamItem
				.getItemParamId();
		AjiaItemParam ajiaItemParam=itemService
				.selectTypeParamByTypeId(itemParamId);
		String typeParamData=ajiaItemParam
				.getParamData();
		List<AjiaItemParamData> typeList=JsonUtils
				.jsonToList(typeParamData, 
						AjiaItemParamData.class);
		modelAndView.addObject("typeParamData",typeList);
		
		modelAndView.addObject("paramData", list);
		modelAndView.setViewName("product_details");
		return modelAndView;
	}

}
