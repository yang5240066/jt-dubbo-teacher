package com.ajiatech.service;

import java.util.List;

import com.ajiatech.pojo.AjiaShipping;

public interface AddressService {
	/**
	 * ��ȡ�û�Ĭ�ϵ�ַ
	 * @param userId
	 * @return
	 * @throws Exception
	 */
	public AjiaShipping getDefaultByUserId(Long userId)
			throws Exception;
	/**
	 * ��ĳ����ַ���ó�Ĭ�ϵ�
	 * @param userId
	 * @param addId
	 * @return
	 * @throws Exception
	 */
	public int setDefault(Long userId,Long addId)
			throws Exception;
	/**
	 * ɾ����ַ
	 * @param addId
	 * @return
	 * @throws Exception
	 */
	public int delete(Long addId) throws Exception;
	/**
	 * ��ӵ�ַ
	 * @param ajiaShipping ʵ����
	 * @return int ��ӵ�����
	 * @throws Exception
	 */
	public int insert(AjiaShipping ajiaShipping) 
			throws Exception;
	/**
	 * ��ѯĳ���û��ĵ�ַ
	 * @param userId
	 * @return
	 * @throws Exception
	 */
	public List<AjiaShipping> selectByUserId(Long userId)
			throws Exception;

}
