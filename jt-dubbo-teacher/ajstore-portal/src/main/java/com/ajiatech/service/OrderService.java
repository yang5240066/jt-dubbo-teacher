package com.ajiatech.service;

import java.util.List;

import com.ajiatech.pojo.AjiaOrder;
import com.ajiatech.pojo.ItemVO;
import com.ajiatech.pojo.OrderVO;
import com.github.pagehelper.PageInfo;

public interface OrderService {
	/**
	 * ���ݶ�����Ų��Ҷ���
	 * @param orderId
	 * @return
	 * @throws Exception
	 */
	public AjiaOrder findOrderByOrderid
	(String orderId) throws Exception;
	
	/**
	 * ���¶���״̬
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
	 * @param currentPage ȡ�ڼ�ҳ������
	 * @param pageSize ÿҳ��ʾ����
	 * @return
	 * @throws Exception
	 */
	public PageInfo<OrderVO> selectByUserIdAndStatusForPage
	(Long userId,int status,int currentPage,int pageSize) 
			throws Exception;
	/**
	 * ��ѯ�û� �����ж���
	 * @param userId
	 * @param status
	 * @return
	 * @throws Exception
	 */
	public List<OrderVO> selectByUserIdAndStatus
	(Long userId,int status) 
			throws Exception;
	/**
	 * ���涩��
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
	 * �����û���ź���Ʒ��Ų�ѯ���ﳵ����Ʒ
	 * @param userId
	 * @param itemIds
	 * @return
	 * @throws Exception
	 */
	public List<ItemVO> 
	selectCartItemByUseridAndItemIds
	(Long userId,List<Long> itemIds) 
			throws Exception;
//������󷽷�
	public AjiaOrder selectById(String orderId)
			throws Exception;
}
