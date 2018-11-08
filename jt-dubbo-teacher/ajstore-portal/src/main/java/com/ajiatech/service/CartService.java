package com.ajiatech.service;

import java.util.List;

import com.ajiatech.pojo.AjiaCartItem;
import com.ajiatech.pojo.ItemVO;

public interface CartService {
	/**
	 * 批量删除
	 * @param userId
	 * @param itemIds
	 * @return
	 * @throws Exception
	 */
	public int batchDelete(Long userId,
			List<Long> itemIds)
			throws Exception;
	/**
	 * 更新购物车中商品的购买数量
	 * @param ajiaCartItem
	 * @return
	 * @throws Exception
	 */
	public int updateNum(AjiaCartItem ajiaCartItem) 
			throws Exception;
	/**
	 * 根据用户编号查询购物车
	 * @param userId
	 * @return
	 * @throws Exception
	 */
	public List<ItemVO> selectByUserId(Long userId) 
			throws Exception;
	/**
	 * 向购物车中添加一个商品
	 * @param ajiaCartItem
	 * @return
	 * @throws Exception
	 */
	public int insert(AjiaCartItem ajiaCartItem) 
			throws Exception;

}
