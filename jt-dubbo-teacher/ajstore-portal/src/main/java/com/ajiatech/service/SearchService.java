package com.ajiatech.service;

import java.util.List;

import com.ajiatech.pojo.AjiaItem;
import com.ajiatech.pojo.AjiaItemSolr;

//��solr �������
public interface SearchService {
	/**
	 * ��solr��������ѯ��Ʒ
	 * @param key
	 * @return
	 * @throws Exception
	 */
	public List<AjiaItemSolr> query(String key) throws Exception;
	
/**
 * ��solr�������������
 * @param list AjiaItem��Ӧ����mysql�е�ajia_item
 * @throws Exception
 */
	public void insert(List<AjiaItem> list) throws Exception;
}
