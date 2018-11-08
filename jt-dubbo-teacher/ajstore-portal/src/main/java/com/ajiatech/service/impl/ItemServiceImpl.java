package com.ajiatech.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ajiatech.common.utils.JsonUtils;
import com.ajiatech.mapper.AjiaItemDescMapper;
import com.ajiatech.mapper.AjiaItemMapper;
import com.ajiatech.mapper.AjiaItemParamItemMapper;
import com.ajiatech.mapper.AjiaItemParamMapper;
import com.ajiatech.pojo.AjiaItem;
import com.ajiatech.pojo.AjiaItemDesc;
import com.ajiatech.pojo.AjiaItemParam;
import com.ajiatech.pojo.AjiaItemParamItem;
import com.ajiatech.pojo.AjiaItemParamItemExample;
import com.ajiatech.pojo.paramData.AjiaItemParamData;
import com.ajiatech.pojo.paramData.Params;
import com.ajiatech.service.ItemService;

@Service
public class ItemServiceImpl implements ItemService {
	@Autowired
	AjiaItemMapper ajiaItemMapper;

	@Autowired
	AjiaItemParamItemMapper ajiaItemParamItemMapper;

	// 从spring框架中取出ajiaItemDesc代理对象
	@Autowired
	AjiaItemDescMapper ajiaItemDescMapper;

	@Autowired
	AjiaItemParamMapper ajiaItemParamMapper;

	@Override
	public AjiaItemParam selectTypeParamByTypeId(Long itemParamId) throws Exception {

		AjiaItemParam ajiaItemParam = ajiaItemParamMapper.selectByPrimaryKey(itemParamId);

		return ajiaItemParam;
	}

	@Override
	public AjiaItemDesc selectDescById(Long itemId) throws Exception {
		AjiaItemDesc ajiaItemDesc = ajiaItemDescMapper.selectByPrimaryKey(itemId);
		return ajiaItemDesc;
	}

	@Override
	public AjiaItemParamItem getParamDataById(Long itemId) throws Exception {
		// where item_id=10000028;
		AjiaItemParamItemExample example = new AjiaItemParamItemExample();

		AjiaItemParamItemExample.Criteria criteria = example.or();
		criteria.andItemIdEqualTo(itemId);
		// 如果表中有一列的数据类型是text,要用widthBlobs()
		// 不用WithBLOBs,param_date列没值
		List<AjiaItemParamItem> list = ajiaItemParamItemMapper.selectByExampleWithBLOBs(example);
		if (list != null && list.size() >= 1) {
			return list.get(0);
		}
		return null;
	}

	@Override
	public List<AjiaItem> selectAll() throws Exception {
		return ajiaItemMapper.selectByExample(null);
	}

	@Override
	public AjiaItem selectItemById(Long itemId) throws Exception {
		AjiaItem ajiaItem = ajiaItemMapper.selectByPrimaryKey(itemId);
		return ajiaItem;
	}

	@Override
	public Long selectItemByParams(String color, String model, Long itemParamId) throws Exception {
		// where item_param_id=111;
		AjiaItemParamItemExample example = new AjiaItemParamItemExample();

		AjiaItemParamItemExample.Criteria criteria = example.or();

		criteria.andItemParamIdEqualTo(itemParamId);
		List<AjiaItemParamItem> itemList = ajiaItemParamItemMapper.selectByExampleWithBLOBs(example);
		// 遍历每个商品
		for (AjiaItemParamItem ajiaItemParamItem : itemList) {
			// 取到param_data
			String paramData = ajiaItemParamItem.getParamData();
			// 把json转成对象
			List<AjiaItemParamData> paramList = JsonUtils.jsonToList(paramData, AjiaItemParamData.class);
			// 从对象中取出params
			// [{,params:[]},{}]
			List<Params> list = paramList.get(0).getParams();
			// 遍历params
			boolean colorIsEquals = false, modelIsEquals = false;
			// 判断遍历商品的color,model是否与用户选择的一致
			for (Params params : list) {
				String value = params.getValues().get(0);
				String key = params.getKey();
				if ("颜色".equals(key) && value.equals(color)) {
					colorIsEquals = true;
				}
				if ("型号".equals(key) && value.equals(model)) {
					modelIsEquals = true;
				}
			}

			if (colorIsEquals && modelIsEquals) {
				// 返回商品id
				return ajiaItemParamItem.getItemId();
			}
		}
		return 0L;
	}

}
