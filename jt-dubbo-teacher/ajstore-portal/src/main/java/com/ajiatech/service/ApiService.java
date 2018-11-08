package com.ajiatech.service;

import java.util.List;
import com.ajiatech.pojo.*;

public interface ApiService {
	/**
	 * ��ȡ��Ʒ����
	 * @param itemId
	 * @return
	 * @throws Exception
	 */
	public DetailVO getItemDetail(Long itemId) 
			throws Exception;

	/**
	 * ��ѯ��Ʒ
	 * @return
	 * @throws Exception
	 */
	public List<AjiaItem> getIndexItem() 
			throws Exception;
}
