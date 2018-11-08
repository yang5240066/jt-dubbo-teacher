package com.ajiatech.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.impl.HttpSolrClient;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.object.SqlQuery;
import org.springframework.stereotype.Service;

import com.ajiatech.pojo.AjiaItem;
import com.ajiatech.pojo.AjiaItemSolr;
import com.ajiatech.service.SearchService;

//交给spring框架
@Service
public class SearchServiceImpl implements SearchService {

	// 从spring框架中取出httpSolrClient
	@Autowired
	HttpSolrClient httpSolrClient;

	@Override
	public List<AjiaItemSolr> query(String key) throws Exception {
		List<AjiaItemSolr> list = null;
		SolrQuery solrQuery = new SolrQuery();
		//查询默认只返回10条数据
		//设置返回8000条数据
		solrQuery.set("rows", 8000);
		solrQuery.setQuery(key);
		QueryResponse queryResponse=
				httpSolrClient.query(solrQuery);
		list=queryResponse.getBeans(AjiaItemSolr.class);
		return list;
	}

	@Override
	public void insert(List<AjiaItem> list) throws Exception {
		// 把mysql中的数据AjiaItem添加到solr中AjiaItemSolr
		// ajiaItem中没有指定属性和solr中field的对应关系
		ArrayList<AjiaItemSolr> solrList = new ArrayList<>();
		for (AjiaItem ajiaItem : list) {
			AjiaItemSolr ajiaItemSolr = new AjiaItemSolr();
			ajiaItemSolr.setItemId(ajiaItem.getId() + "");
			ajiaItemSolr.setBrand(ajiaItem.getBrand());
			ajiaItemSolr.setModel(ajiaItem.getModel());
			ajiaItemSolr.setTitle(ajiaItem.getTitle());
			ajiaItemSolr.setSellPoint(ajiaItem.getSellPoint());

			ajiaItemSolr.setImage(ajiaItem.getImage());
			ajiaItemSolr.setPrice(ajiaItem.getPrice());

			solrList.add(ajiaItemSolr);
		}
		httpSolrClient.addBeans(solrList);
		httpSolrClient.commit();
	}

}
