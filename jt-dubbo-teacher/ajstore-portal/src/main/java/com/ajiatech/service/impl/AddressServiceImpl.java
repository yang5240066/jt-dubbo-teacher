package com.ajiatech.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ajiatech.mapper.AjiaShippingMapper;
import com.ajiatech.pojo.AjiaShipping;
import com.ajiatech.pojo.AjiaShippingExample;
import com.ajiatech.service.AddressService;

//����spring��ܹ���
@Service
public class AddressServiceImpl implements AddressService{

	//��spring����еõ�AjiaShippingMapper�Ĵ������
	@Autowired
	AjiaShippingMapper ajiaShippingMapper;
	@Override
	public int insert(AjiaShipping ajiaShipping) throws Exception {
		//�ɹ�����˼���
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
//�����е�ַ���óɷ�Ĭ�ϵ�
		// update ajia_shipping set is_default=0
		//where user_=14;
		AjiaShippingExample example=
				new AjiaShippingExample();
		
		AjiaShippingExample.Criteria criteria=example.or();
		criteria.andUserIdEqualTo(userId);
		
		AjiaShipping ajiaShipping=new AjiaShipping();
		ajiaShipping.setIsDefault(false);
		//set receiverName=null,phone=null
		//һ������²��� updateByExample
		//updateByExampleSelective,ѡ��ajiaShipping����ֵ��Ϊ�յ����ԣ�����set ������=ֵ��
		int row=ajiaShippingMapper
				.updateByExampleSelective(ajiaShipping, example);
		
		//��ĳ����ַ���ó�Ĭ�ϵ�
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
