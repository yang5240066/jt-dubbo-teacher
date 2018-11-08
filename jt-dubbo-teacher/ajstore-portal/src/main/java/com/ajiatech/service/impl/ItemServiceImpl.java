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

	// ��spring�����ȡ��ajiaItemDesc�������
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
		// ���������һ�е�����������text,Ҫ��widthBlobs()
		// ����WithBLOBs,param_date��ûֵ
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
		// ����ÿ����Ʒ
		for (AjiaItemParamItem ajiaItemParamItem : itemList) {
			// ȡ��param_data
			String paramData = ajiaItemParamItem.getParamData();
			// ��jsonת�ɶ���
			List<AjiaItemParamData> paramList = JsonUtils.jsonToList(paramData, AjiaItemParamData.class);
			// �Ӷ�����ȡ��params
			// [{,params:[]},{}]
			List<Params> list = paramList.get(0).getParams();
			// ����params
			boolean colorIsEquals = false, modelIsEquals = false;
			// �жϱ�����Ʒ��color,model�Ƿ����û�ѡ���һ��
			for (Params params : list) {
				String value = params.getValues().get(0);
				String key = params.getKey();
				if ("��ɫ".equals(key) && value.equals(color)) {
					colorIsEquals = true;
				}
				if ("�ͺ�".equals(key) && value.equals(model)) {
					modelIsEquals = true;
				}
			}

			if (colorIsEquals && modelIsEquals) {
				// ������Ʒid
				return ajiaItemParamItem.getItemId();
			}
		}
		return 0L;
	}

}
