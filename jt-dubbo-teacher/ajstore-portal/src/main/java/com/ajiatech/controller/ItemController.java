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
	//��spring�����ȡItemService
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
		
		//��service�����ݿ���ȡ����Ʒid
		itemId=itemService.selectItemByParams(color, model,
				Long.parseLong(itemParamId));
		ajiaResult.setData(itemId);
		return ajiaResult;
	}
	@RequestMapping("/toItemInfo.html")
	public ModelAndView toItemInfo(Long itemId) throws Exception
	{
		ModelAndView modelAndView=new ModelAndView();
		//����Ʒ��ŷŵ�request��
		modelAndView.addObject("itemId", itemId);
		
		
		AjiaItemParamItem ajiaItemParamItem=itemService
				.getParamDataById(itemId);
		String paramData=ajiaItemParamItem.getParamData();
		List<AjiaItemParamData> list=JsonUtils
				.jsonToList(paramData, AjiaItemParamData.class);
		//����Ʒ����id���浽request�� 
		Long itemParamId=ajiaItemParamItem.getItemParamId();
		modelAndView.addObject("itemParamId", itemParamId);
		//��list��ȡ����ǰ��Ʒ�Ĳ���
		//[{params:[{}]},{}]
		String itemColor="",itemModel="";
		for (Params params:list.get(0).getParams())
		{
			if (params.getKey().equals("��ɫ") 
					&& params.getValues()!=null)
			{
				itemColor=params.getValues().get(0);
			}
			if (params.getKey().equals("�ͺ�") 
					&& params.getValues()!=null)
			{
				itemModel=params.getValues().get(0);
			}
		}
		modelAndView.addObject("itemColor", itemColor);
		modelAndView.addObject("itemModel", itemModel);
		
		//ȡ��Ʒ��Ϣ
		AjiaItem ajiaItem=itemService
				.selectItemById(itemId);
		modelAndView.addObject("ajiaItem", ajiaItem);
		
		//ȡ����ͼƬ
		AjiaItemDesc ajiaItemDesc=itemService
				.selectDescById(itemId);
		String desc=ajiaItemDesc.getItemDesc();
		modelAndView.addObject("desc", desc);
		
		//ȡ��Ʒ���͵Ĳ���
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
