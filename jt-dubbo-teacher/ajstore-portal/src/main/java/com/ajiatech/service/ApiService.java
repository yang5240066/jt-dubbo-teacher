package com.ajiatech.service;

import java.util.List;
import com.ajiatech.pojo.*;

public interface ApiService {
	/**
	 * 获取商品详情
	 * @param itemId
	 * @return
	 * @throws Exception
	 */
	public DetailVO getItemDetail(Long itemId) 
			throws Exception;

	/**
	 * 查询商品
	 * @return
	 * @throws Exception
	 */
	public List<AjiaItem> getIndexItem() 
			throws Exception;
}
