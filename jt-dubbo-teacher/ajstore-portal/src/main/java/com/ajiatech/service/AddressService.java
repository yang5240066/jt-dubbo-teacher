package com.ajiatech.service;

import java.util.List;

import com.ajiatech.pojo.AjiaShipping;

public interface AddressService {
	/**
	 * 获取用户默认地址
	 * @param userId
	 * @return
	 * @throws Exception
	 */
	public AjiaShipping getDefaultByUserId(Long userId)
			throws Exception;
	/**
	 * 把某个地址设置成默认的
	 * @param userId
	 * @param addId
	 * @return
	 * @throws Exception
	 */
	public int setDefault(Long userId,Long addId)
			throws Exception;
	/**
	 * 删除地址
	 * @param addId
	 * @return
	 * @throws Exception
	 */
	public int delete(Long addId) throws Exception;
	/**
	 * 添加地址
	 * @param ajiaShipping 实体类
	 * @return int 添加的行数
	 * @throws Exception
	 */
	public int insert(AjiaShipping ajiaShipping) 
			throws Exception;
	/**
	 * 查询某个用户的地址
	 * @param userId
	 * @return
	 * @throws Exception
	 */
	public List<AjiaShipping> selectByUserId(Long userId)
			throws Exception;

}
