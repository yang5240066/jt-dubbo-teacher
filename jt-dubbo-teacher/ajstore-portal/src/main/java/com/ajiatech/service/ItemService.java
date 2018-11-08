package com.ajiatech.service;

import java.util.List;


import com.ajiatech.pojo.AjiaItem;
import com.ajiatech.pojo.AjiaItemDesc;
import com.ajiatech.pojo.AjiaItemParam;
import com.ajiatech.pojo.AjiaItemParamItem;

//����ajia_item��
public interface ItemService {
	/**
	 * ���ݲ�����ѯ��Ʒ
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
	 * 28��30��ͬһ�����ͣ���������(itemParamId)��111
	 * ����111��ajia_item_param��ѯ
	 * ������Ʒ�Ĳ������Ͳ�ѯ��Ʒ���͵Ĳ���
	 * @param itemParamId
	 * @return
	 * @throws Exception
	 */
	public AjiaItemParam selectTypeParamByTypeId
	(Long itemParamId) throws Exception;
	
	/**
	 * ������Ʒ��Ų�ѯ��Ʒ����
	 * @param itemId
	 * @return
	 * @throws Exception
	 */
	public AjiaItemDesc selectDescById(Long itemId) 
			throws Exception;
	/**
	 * ������Ʒ��Ų�ѯ��Ʒ
	 * @param itemId
	 * @return
	 * @throws Exception
	 */
	public AjiaItem selectItemById(Long itemId) 
			throws Exception;
	/**
	 * ������Ʒ��Ų�ѯ��Ʒ����
	 * @param itemId
	 * @return
	 * @throws Exception
	 */
	public AjiaItemParamItem 
	getParamDataById(Long itemId) throws Exception;
	/**
	 * ��ѯ������Ʒ����
	 * @return
	 * @throws Exception
	 */
public List<AjiaItem> selectAll() throws Exception;
}
