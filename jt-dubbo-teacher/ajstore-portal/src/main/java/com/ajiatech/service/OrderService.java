package com.ajiatech.service;

import java.util.List;

import com.ajiatech.pojo.AjiaOrder;
import com.ajiatech.pojo.ItemVO;
import com.ajiatech.pojo.OrderVO;
import com.github.pagehelper.PageInfo;

public interface OrderService {
	/**
	 * 根据订单编号查找订单
	 * @param orderId
	 * @return
	 * @throws Exception
	 */
	public AjiaOrder findOrderByOrderid
	(String orderId) throws Exception;
	
	/**
	 * 更新订单状态
	 * @param orderId
	 * @param status
	 * @return
	 * @throws Exception
	 */
	public int updateOrderStatus
	(String orderId,int status) throws Exception;

	/**
	 * 
	 * @param userId
	 * @param status
	 * @param currentPage 取第几页的数据
	 * @param pageSize 每页显示几行
	 * @return
	 * @throws Exception
	 */
	public PageInfo<OrderVO> selectByUserIdAndStatusForPage
	(Long userId,int status,int currentPage,int pageSize) 
			throws Exception;
	/**
	 * 查询用户 的所有订单
	 * @param userId
	 * @param status
	 * @return
	 * @throws Exception
	 */
	public List<OrderVO> selectByUserIdAndStatus
	(Long userId,int status) 
			throws Exception;
	/**
	 * 保存订单
	 * @param addid
	 * @param userId
	 * @param itemId
	 * @return
	 * @throws Exception
	 */
	public AjiaOrder saveOrder
	(Long addId,Long userId,List<Long> itemId)
			throws Exception;
	/**
	 * 根据用户编号和商品编号查询购物车中商品
	 * @param userId
	 * @param itemIds
	 * @return
	 * @throws Exception
	 */
	public List<ItemVO> 
	selectCartItemByUseridAndItemIds
	(Long userId,List<Long> itemIds) 
			throws Exception;
//定义抽象方法
	public AjiaOrder selectById(String orderId)
			throws Exception;
}
