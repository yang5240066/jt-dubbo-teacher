package com.ajiatech.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ajiatech.mapper.AjiaItemDescMapper;
import com.ajiatech.mapper.AjiaItemMapper;
import com.ajiatech.pojo.AjiaItem;
import com.ajiatech.pojo.AjiaItemDesc;
import com.ajiatech.pojo.AjiaItemExample;
import com.ajiatech.pojo.DetailVO;
import com.ajiatech.service.ApiService;

@Service
public class ApiServiceImpl implements ApiService {

	@Autowired
	AjiaItemMapper ajiaItemMapper;

	@Autowired
	AjiaItemDescMapper ajiaItemDescMapper;

	@Override
	public List<AjiaItem> getIndexItem() throws Exception {
		// select * from ajia_item where item_id in(10000028,1000029)
		ArrayList<Long> itemIds = new ArrayList<>();
		itemIds.add(10000028L);
		itemIds.add(10000029L);
		itemIds.add(10000030L);
		itemIds.add(10000031L);
		itemIds.add(10000032L);
		itemIds.add(10000033L);

		AjiaItemExample example = new AjiaItemExample();
		AjiaItemExample.Criteria criteria = example.or();
		criteria.andIdIn(itemIds);

		List<AjiaItem> list = ajiaItemMapper.selectByExample(example);

		return list;
	}

	@Override
	public DetailVO getItemDetail(Long itemId) throws Exception {
		DetailVO detailVO = new DetailVO();

		AjiaItem ajiaItem = ajiaItemMapper
				.selectByPrimaryKey(itemId);
		AjiaItemDesc ajiaItemDesc = ajiaItemDescMapper
				.selectByPrimaryKey(itemId);

		detailVO.setAjiaItem(ajiaItem);
		detailVO.setAjiaItemDesc(ajiaItemDesc);

		return detailVO;
	}

}
