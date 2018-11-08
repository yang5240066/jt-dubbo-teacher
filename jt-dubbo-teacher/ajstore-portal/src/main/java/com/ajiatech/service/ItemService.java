package com.ajiatech.service;

import java.util.List;


import com.ajiatech.pojo.AjiaItem;
import com.ajiatech.pojo.AjiaItemDesc;
import com.ajiatech.pojo.AjiaItemParam;
import com.ajiatech.pojo.AjiaItemParamItem;

//操作ajia_item表
public interface ItemService {
	/**
	 * 根据参数查询商品
	 * @param color
	 * @param model
	 * @param itemParamId
	 * @return
	 * @throws Exception
	 */
	public Long selectItemByParams(String color
			,String model,Long itemParamId)
			throws Exception;
	/**
	 * 28，30是同一个类型，参数类型(itemParamId)是111
	 * 根据111到ajia_item_param查询
	 * 根据商品的参数类型查询商品类型的参数
	 * @param itemParamId
	 * @return
	 * @throws Exception
	 */
	public AjiaItemParam selectTypeParamByTypeId
	(Long itemParamId) throws Exception;
	
	/**
	 * 根据商品编号查询商品描述
	 * @param itemId
	 * @return
	 * @throws Exception
	 */
	public AjiaItemDesc selectDescById(Long itemId) 
			throws Exception;
	/**
	 * 根据商品编号查询商品
	 * @param itemId
	 * @return
	 * @throws Exception
	 */
	public AjiaItem selectItemById(Long itemId) 
			throws Exception;
	/**
	 * 根据商品编号查询商品参数
	 * @param itemId
	 * @return
	 * @throws Exception
	 */
	public AjiaItemParamItem 
	getParamDataById(Long itemId) throws Exception;
	/**
	 * 查询所有商品数据
	 * @return
	 * @throws Exception
	 */
public List<AjiaItem> selectAll() throws Exception;
}
