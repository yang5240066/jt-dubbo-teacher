package com.ajiatech.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ajiatech.mapper.AjiaShippingMapper;
import com.ajiatech.pojo.AjiaShipping;
import com.ajiatech.pojo.AjiaShippingExample;
import com.ajiatech.service.AddressService;

//交给spring框架管理
@Service
public class AddressServiceImpl implements AddressService{

	//从spring框架中得到AjiaShippingMapper的代理对象
	@Autowired
	AjiaShippingMapper ajiaShippingMapper;
	@Override
	public int insert(AjiaShipping ajiaShipping) throws Exception {
		//成功添加了几行
		int row=ajiaShippingMapper.insert(ajiaShipping);
		return row;
	}
	@Override
	public List<AjiaShipping> selectByUserId(Long userId) throws Exception {
// from ajia_shipping where user_id=14 and status=1
		AjiaShippingExample example=new 
				AjiaShippingExample();
		
		AjiaShippingExample.Criteria criteria=
				example.or();
		criteria.andUserIdEqualTo(userId);
		criteria.andStatusEqualTo((byte)1);
		
		List<AjiaShipping> list=ajiaShippingMapper
				.selectByExample(example);		
		
		return list;
	}
	@Override
	public int setDefault(Long userId, Long addId) throws Exception {
//把所有地址设置成非默认的
		// update ajia_shipping set is_default=0
		//where user_=14;
		AjiaShippingExample example=
				new AjiaShippingExample();
		
		AjiaShippingExample.Criteria criteria=example.or();
		criteria.andUserIdEqualTo(userId);
		
		AjiaShipping ajiaShipping=new AjiaShipping();
		ajiaShipping.setIsDefault(false);
		//set receiverName=null,phone=null
		//一般情况下不用 updateByExample
		//updateByExampleSelective,选中ajiaShipping属性值不为空的属性，放在set 属性名=值，
		int row=ajiaShippingMapper
				.updateByExampleSelective(ajiaShipping, example);
		
		//把某个地址设置成默认的
		//where user_id=14 and add_id=1
		criteria.andAddIdEqualTo(addId);
		ajiaShipping.setIsDefault(true);
		ajiaShippingMapper
		.updateByExampleSelective(ajiaShipping, example);
		return row;
	}
	@Override
	public int delete(Long addId) throws Exception {
		int row = ajiaShippingMapper.deleteByPrimaryKey(addId);
		return row;
	}
	@Override
	public AjiaShipping getDefaultByUserId(Long userId) throws Exception {
AjiaShippingExample example=new AjiaShippingExample();
AjiaShippingExample.Criteria criteria=example.or();
criteria.andUserIdEqualTo(userId);
criteria.andIsDefaultEqualTo(true);

List<AjiaShipping> ajiaShippings=ajiaShippingMapper
.selectByExample(example);

	if (ajiaShippings!=null 
			&& ajiaShippings.size()>=1)
	{
		return ajiaShippings.get(0);
	}
		return null;
	}
}
