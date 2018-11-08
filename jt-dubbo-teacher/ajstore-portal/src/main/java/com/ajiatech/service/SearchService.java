package com.ajiatech.service;

import java.util.List;

import com.ajiatech.pojo.AjiaItem;
import com.ajiatech.pojo.AjiaItemSolr;

//跟solr 搜索相关
public interface SearchService {
	/**
	 * 从solr服务器查询商品
	 * @param key
	 * @return
	 * @throws Exception
	 */
	public List<AjiaItemSolr> query(String key) throws Exception;
	
/**
 * 向solr服务器添加数据
 * @param list AjiaItem对应的是mysql中的ajia_item
 * @throws Exception
 */
	public void insert(List<AjiaItem> list) throws Exception;
}
