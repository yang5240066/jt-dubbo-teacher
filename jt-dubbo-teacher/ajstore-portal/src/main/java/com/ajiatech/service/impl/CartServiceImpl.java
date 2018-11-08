package com.ajiatech.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ajiatech.common.utils.JsonUtils;
import com.ajiatech.mapper.AjiaCartItemMapper;
import com.ajiatech.mapper.AjiaItemMapper;
import com.ajiatech.mapper.AjiaItemParamItemMapper;
import com.ajiatech.pojo.AjiaCartItem;
import com.ajiatech.pojo.AjiaCartItemExample;
import com.ajiatech.pojo.AjiaItem;
import com.ajiatech.pojo.AjiaItemParamItem;
import com.ajiatech.pojo.AjiaItemParamItemExample;
import com.ajiatech.pojo.ItemVO;
import com.ajiatech.pojo.paramData.AjiaItemParamData;
import com.ajiatech.pojo.paramData.Params;
import com.ajiatech.service.CartService;

@Service
public class CartServiceImpl implements CartService {

	@Autowired
	AjiaCartItemMapper ajiaCartItemMapper;

	@Autowired
	AjiaItemMapper ajiaItemMapper;

	@Autowired
	AjiaItemParamItemMapper ajiaItemParamItemMapper;

	@Override
	public List<ItemVO> selectByUserId(Long userId) throws Exception {
		// 1,��ѯ���ﳵ��
		AjiaCartItemExample cartItemExample = new AjiaCartItemExample();
		AjiaCartItemExample.Criteria criteria = cartItemExample.or();
		criteria.andUserIdEqualTo(userId);
		criteria.andStatusEqualTo(1);
		List<AjiaCartItem> cartItems = ajiaCartItemMapper.selectByExample(cartItemExample);
		// 2,����list���itemVo
		List<ItemVO> itemVOs = new ArrayList<>();
		// 3,������Ʒ
		for (AjiaCartItem ajiaCartItem : cartItems) {
			// 3.1 ȡ��Ʒtitle
			Long itemId = ajiaCartItem.getItemId();
			AjiaItem ajiaItem = ajiaItemMapper.selectByPrimaryKey(itemId);
			// 3.2 ȡ��Ʒ�Ĳ���
			// 3.2.1 ����example��ѯajia_item_param_item
			AjiaItemParamItemExample paramExample = new AjiaItemParamItemExample();
			AjiaItemParamItemExample.Criteria paramCriteria = paramExample.or();
			paramCriteria.andItemIdEqualTo(itemId);
			List<AjiaItemParamItem> paramItems = ajiaItemParamItemMapper.selectByExampleWithBLOBs(paramExample);
			List<Params> paramsList = new ArrayList<>();
			if (paramItems != null && paramItems.size() >= 1) {
				AjiaItemParamItem ajiaItemParamItem = paramItems.get(0);
				// 3.2.2 ȡparam_data
				String param_data = ajiaItemParamItem.getParamData();
				// 3.2.3 ��jsonת��list
				List<AjiaItemParamData> paramDataList = JsonUtils.jsonToList(param_data, AjiaItemParamData.class);
				// 3.2.4��list�ĵ�һ������ȡ����
				paramsList = paramDataList.get(0).getParams();
			}
			// 3.3 ��������title,�����ŵ�ItemVo
			ItemVO itemVO = new ItemVO();
			itemVO.setAjiaCartItem(ajiaCartItem);
			itemVO.setAjiaItem(ajiaItem);
			itemVO.setParamsList(paramsList);
			// 3.4 ��vo�ŵ�list
			itemVOs.add(itemVO);
		}
		// 4,����list
		return itemVOs;
	}

	@Override
	public int insert(AjiaCartItem ajiaCartItem) throws Exception {
		// �Ȳ�ѯ�û��Ƿ������Ʒ
		// where userid=14 and item_id=28 and status=1
		AjiaCartItemExample example = new AjiaCartItemExample();
		AjiaCartItemExample.Criteria criteria = example.or();
		criteria.andUserIdEqualTo(ajiaCartItem.getUserId());
		criteria.andItemIdEqualTo(ajiaCartItem.getItemId());
		criteria.andStatusEqualTo(ajiaCartItem.getStatus());
		List<AjiaCartItem> list = ajiaCartItemMapper.selectByExample(example);
		int row = 0;
		if (list != null && list.size() >= 1) {
			// ��ӹ���,��������
			AjiaCartItem dbItem = list.get(0);
			ajiaCartItem.setNum(dbItem.getNum() + ajiaCartItem.getNum());
			row = ajiaCartItemMapper.updateByExampleSelective(ajiaCartItem, example);
		} else {
			// û��ӹ����������
			row = ajiaCartItemMapper.insert(ajiaCartItem);
		}
		return row;
	}

	@Override
	public int updateNum(AjiaCartItem ajiaCartItem) throws Exception {
		// update ajia_cart_item set num=5
		// where userId=14 and item_id=28 and status=1
		AjiaCartItemExample example = new AjiaCartItemExample();
		AjiaCartItemExample.Criteria criteria = example.or();
		criteria.andUserIdEqualTo(ajiaCartItem.getUserId());
		criteria.andItemIdEqualTo(ajiaCartItem.getItemId());
		criteria.andStatusEqualTo(ajiaCartItem.getStatus());
		int row = ajiaCartItemMapper.updateByExampleSelective(ajiaCartItem, example);

		return row;
	}

	@Override
	public int batchDelete(Long userId, List<Long> itemIds) throws Exception {
		AjiaCartItemExample example = new AjiaCartItemExample();
		AjiaCartItemExample.Criteria criteria = example.or();
		criteria.andUserIdEqualTo(userId);
		criteria.andItemIdIn(itemIds);

		AjiaCartItem ajiaCartItem = new AjiaCartItem();
		ajiaCartItem.setStatus(2);

		int row = ajiaCartItemMapper.updateByExampleSelective(ajiaCartItem, example);

		return row;
	}

}
