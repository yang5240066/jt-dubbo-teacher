package com.ajiatech.service;

import java.util.List;

import com.ajiatech.pojo.AjiaCartItem;
import com.ajiatech.pojo.ItemVO;

public interface CartService {
	/**
	 * ����ɾ��
	 * @param userId
	 * @param itemIds
	 * @return
	 * @throws Exception
	 */
	public int batchDelete(Long userId,
			List<Long> itemIds)
			throws Exception;
	/**
	 * ���¹��ﳵ����Ʒ�Ĺ�������
	 * @param ajiaCartItem
	 * @return
	 * @throws Exception
	 */
	public int updateNum(AjiaCartItem ajiaCartItem) 
			throws Exception;
	/**
	 * �����û���Ų�ѯ���ﳵ
	 * @param userId
	 * @return
	 * @throws Exception
	 */
	public List<ItemVO> selectByUserId(Long userId) 
			throws Exception;
	/**
	 * ���ﳵ�����һ����Ʒ
	 * @param ajiaCartItem
	 * @return
	 * @throws Exception
	 */
	public int insert(AjiaCartItem ajiaCartItem) 
			throws Exception;

}
