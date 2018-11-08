package com.ajiatech.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ajiatech.common.utils.JsonUtils;
import com.ajiatech.mapper.AjiaCartItemMapper;
import com.ajiatech.mapper.AjiaItemMapper;
import com.ajiatech.mapper.AjiaItemParamItemMapper;
import com.ajiatech.mapper.AjiaOrderItemMapper;
import com.ajiatech.mapper.AjiaOrderMapper;
import com.ajiatech.mapper.AjiaShippingMapper;
import com.ajiatech.pojo.AjiaCartItem;
import com.ajiatech.pojo.AjiaCartItemExample;
import com.ajiatech.pojo.AjiaItem;
import com.ajiatech.pojo.AjiaItemParamItem;
import com.ajiatech.pojo.AjiaItemParamItemExample;
import com.ajiatech.pojo.AjiaOrder;
import com.ajiatech.pojo.AjiaOrderExample;
import com.ajiatech.pojo.AjiaOrderItem;
import com.ajiatech.pojo.AjiaOrderItemExample;
import com.ajiatech.pojo.AjiaShipping;
import com.ajiatech.pojo.ItemVO;
import com.ajiatech.pojo.OrderVO;
import com.ajiatech.pojo.paramData.AjiaItemParamData;
import com.ajiatech.pojo.paramData.Params;
import com.ajiatech.service.OrderService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

//����spring��ܹ���
//applicationContext.xml���������ɨ�裬@service����Ч
@Service
public class OrderServiceImpl implements OrderService {
	// ��spring��ܵ�AjiaOrdermapper�Ĵ������
	@Autowired
	AjiaOrderMapper ajiaOrderMapper;

	@Autowired
	AjiaCartItemMapper ajiaCartItemMapper;

	@Autowired
	AjiaItemMapper ajiaItemMapper;

	@Autowired
	AjiaItemParamItemMapper ajiaItemParamItemMapper;

	@Autowired
	AjiaShippingMapper ajiaShippingMapper;

	@Autowired
	AjiaOrderItemMapper ajiaOrderItemMapper;

	// ����id
	public synchronized String generateId() {
		Random random = new Random();
		// 7λ�����
		int number = random.nextInt(9000000) + 1000000;
		return "" + System.currentTimeMillis() + number;
	}

	@Override
	public AjiaOrder saveOrder(Long addId, Long userId, List<Long> itemId) throws Exception {
		// �ѵ�ַ��Ϣд��������
		AjiaShipping ajiaShipping = ajiaShippingMapper.selectByPrimaryKey(addId);
		String orderId = generateId();
		AjiaOrder ajiaOrder = new AjiaOrder();
		ajiaOrder.setOrderId(orderId);
		ajiaOrder.setUserId(userId);
		ajiaOrder.setAddId(addId);
		ajiaOrder.setShippingName(ajiaShipping.getReceiverName());
		ajiaOrder.setShippingCode(ajiaShipping.getReceiverAddress());
		ajiaOrder.setStatus(1);// δ����
		ajiaOrder.setPaymentType(1);
		ajiaOrder.setPostFee(10D);
		ajiaOrder.setCreateTime(new Date());

		double payment = 0;
		// ����Ʒ��Ϣд�����������
		List<ItemVO> itemVOs = selectCartItemByUseridAndItemIds(userId, itemId);
		for (ItemVO itemVO : itemVOs) {
			AjiaOrderItem ajiaOrderItem = new AjiaOrderItem();
			String id = generateId();
			//id�ظ�,��һ����Ʒ��ӳɹ����ڶ�����Ʒ���ʧ��
			//String id="2";
			ajiaOrderItem.setId(id);
			ajiaOrderItem.setOrderId(orderId);
			ajiaOrderItem.setItemId("" + itemVO.getAjiaItem().getId());
			ajiaOrderItem.setTitle(itemVO.getAjiaItem().getTitle());
			ajiaOrderItem.setPrice(itemVO.getAjiaItem().getPrice());
			ajiaOrderItem.setNum(itemVO.getAjiaCartItem().getNum());

			payment = payment + itemVO.getAjiaCartItem().getNum() * itemVO.getAjiaItem().getPrice();
			ajiaOrderItemMapper.insert(ajiaOrderItem);
		}
		ajiaOrder.setPayment(payment);
		// ���涩�������ݿ�
		ajiaOrderMapper.insert(ajiaOrder);
		return ajiaOrder;
	}

	@Override
	public List<ItemVO> selectCartItemByUseridAndItemIds(Long userId, List<Long> itemIds) throws Exception {
		// 1,�����û���źͶ����Ʒ��Ŵӹ��ﳵ���в�ѯ����Ʒ
		AjiaCartItemExample cartItemExample = new AjiaCartItemExample();
		AjiaCartItemExample.Criteria criteria = cartItemExample.or();
		criteria.andUserIdEqualTo(userId);
		criteria.andItemIdIn(itemIds);
		criteria.andStatusEqualTo(1);

		List<AjiaCartItem> cartItems = ajiaCartItemMapper.selectByExample(cartItemExample);
		// 2,������vo��List
		List<ItemVO> itemVOs = new ArrayList<>();
		// 3,����ÿ����Ʒ
		for (AjiaCartItem ajiaCartItem : cartItems) {
			// 3.1 ��ajia_item��ȡtitle
			Long itemId = ajiaCartItem.getItemId();
			AjiaItem ajiaItem = ajiaItemMapper.selectByPrimaryKey(itemId);
			// 3.2 ��ajia_item_param_item����ȡ����
			// 3.2.1 ����example
			AjiaItemParamItemExample paramExample = new AjiaItemParamItemExample();
			AjiaItemParamItemExample.Criteria paramCriteria = paramExample.or();
			paramCriteria.andItemIdEqualTo(itemId);
			List<AjiaItemParamItem> items = ajiaItemParamItemMapper.selectByExampleWithBLOBs(paramExample);
			// 3.2.2 ȡ����һ������
			List<Params> paramsList = new ArrayList<>();
			if (items != null && items.size() >= 1) {
				AjiaItemParamItem item = items.get(0);
				// 3.2.3 ȡparam-data
				String paramData = item.getParamData();
				// 3.2.4 ��jsonת��list
				List<AjiaItemParamData> list = JsonUtils.jsonToList(paramData, AjiaItemParamData.class);
				// 3.2.4,��list��ȡ����һ�������params����
				paramsList = list.get(0).getParams();
			}
			// 3.3 ����ItemVO
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
	public AjiaOrder selectById(String orderId) throws Exception {
		AjiaOrder ajiaOrder = ajiaOrderMapper.selectByPrimaryKey(orderId);
		return ajiaOrder;
	}

	@Override
	public List<OrderVO> selectByUserIdAndStatus
	(Long userId, int status) throws Exception {
//1����ѯ���ж���
		//where user_id=14 and status!=9 order by create_time desc
		AjiaOrderExample orderExample=new AjiaOrderExample();
		AjiaOrderExample.Criteria criteria=orderExample.or();
		criteria.andUserIdEqualTo(userId);
		criteria.andStatusNotEqualTo(9);//9��ʾ�����Ѿ�ɾ��
//0 ��ʾ���ж���
		if (status!=0)
		{
			criteria.andStatusEqualTo(status);
		}
		orderExample.setOrderByClause("create_time desc");
		List<AjiaOrder> orders=ajiaOrderMapper
				.selectByExample(orderExample);
		
		//2,���� VOList
		List<OrderVO> orderVOs=new ArrayList<>();
		//3���������еĶ���
		for (AjiaOrder ajiaOrder:orders)
		{
		//3.1��ѯ������������Ʒ
			//from ajia_order_item where order_id=30;
			AjiaOrderItemExample itemExample=new AjiaOrderItemExample();
			AjiaOrderItemExample.Criteria itemCriteria=itemExample.or();
			itemCriteria.andOrderIdEqualTo(ajiaOrder.getOrderId());
			List<AjiaOrderItem> orderItems=
					ajiaOrderItemMapper
					.selectByExample(itemExample);
		//3.2����������Ʒ
			for (AjiaOrderItem ajiaOrderItem:orderItems)
			{
		//3.3ȡ��Ʒ����
				
			//3.3.1 example,criteria
				AjiaItemParamItemExample example=
						new AjiaItemParamItemExample();
				AjiaItemParamItemExample.Criteria paramCriteria=
						example.or();
				long itemId=Long.parseLong(ajiaOrderItem
						.getItemId());
				paramCriteria.andItemIdEqualTo(itemId);
				
				List<AjiaItemParamItem> paramItemList=ajiaItemParamItemMapper
						.selectByExampleWithBLOBs(example);
			//3.3.2 ��list��ȡ������ 
				List<Params> paramsList=new ArrayList<>();
				if (paramItemList.size()>=1)
				{
			//3.3.3 ȡparam_data
					AjiaItemParamItem ajiaItemParamItem=
							paramItemList.get(0);
					String paramData=ajiaItemParamItem
							.getParamData();					
				//3.3.4 ��jsonת��list
					List<AjiaItemParamData> paramDataList=JsonUtils
							.jsonToList(paramData, 
									AjiaItemParamData.class);
				//3.3.5 ��listȡ����ɫ�Ȳ���
					paramsList=paramDataList.get(0).getParams();
				}
			ajiaOrderItem.setParamsList(paramsList);	
			}
		//3.4 ����orderVo,��vo��ֵ������Ϣ����Ʒ��Ϣ
			OrderVO orderVO=new OrderVO();
			orderVO.setAjiaOrder(ajiaOrder);
			orderVO.setAjiaOrderItems(orderItems);
			
			orderVOs.add(orderVO);
		}
		//4,����voList
		return orderVOs;
	}

	@Override
	public PageInfo<OrderVO> selectByUserIdAndStatusForPage(Long userId, int status, int currentPage, int pageSize)
			throws Exception {
//��ҳ��һ��:���õ�ǰҳ����ÿҳ������
		PageHelper.startPage(currentPage, pageSize);
		//PageHelper.orderBy("create_time desc");
		
		//1����ѯ���ж���
				//where user_id=14 and status!=9 order by create_time desc
				AjiaOrderExample orderExample=new AjiaOrderExample();
				AjiaOrderExample.Criteria criteria=orderExample.or();
				criteria.andUserIdEqualTo(userId);
				criteria.andStatusNotEqualTo(9);//9��ʾ�����Ѿ�ɾ��
		//0 ��ʾ���ж���
				if (status!=0)
				{
					criteria.andStatusEqualTo(status);
				}
				orderExample.setOrderByClause("create_time desc");
				List<AjiaOrder> orders=ajiaOrderMapper
						.selectByExample(orderExample);
				
				//2,���� VOList
				List<OrderVO> orderVOs=new ArrayList<>();
				//��ҳ�ڶ���������pageInfo
				PageInfo<OrderVO> pageInfo=new PageInfo(orders);
				//3���������еĶ���
				for (AjiaOrder ajiaOrder:orders)
				{
				//3.1��ѯ������������Ʒ
					//from ajia_order_item where order_id=30;
					AjiaOrderItemExample itemExample=new AjiaOrderItemExample();
					AjiaOrderItemExample.Criteria itemCriteria=itemExample.or();
					itemCriteria.andOrderIdEqualTo(ajiaOrder.getOrderId());
					List<AjiaOrderItem> orderItems=
							ajiaOrderItemMapper
							.selectByExample(itemExample);
				//3.2����������Ʒ
					for (AjiaOrderItem ajiaOrderItem:orderItems)
					{
				//3.3ȡ��Ʒ����
						
					//3.3.1 example,criteria
						AjiaItemParamItemExample example=
								new AjiaItemParamItemExample();
						AjiaItemParamItemExample.Criteria paramCriteria=
								example.or();
						long itemId=Long.parseLong(ajiaOrderItem
								.getItemId());
						paramCriteria.andItemIdEqualTo(itemId);
						
						List<AjiaItemParamItem> paramItemList=ajiaItemParamItemMapper
								.selectByExampleWithBLOBs(example);
					//3.3.2 ��list��ȡ������ 
						List<Params> paramsList=new ArrayList<>();
						if (paramItemList.size()>=1)
						{
					//3.3.3 ȡparam_data
							AjiaItemParamItem ajiaItemParamItem=
									paramItemList.get(0);
							String paramData=ajiaItemParamItem
									.getParamData();					
						//3.3.4 ��jsonת��list
							List<AjiaItemParamData> paramDataList=JsonUtils
									.jsonToList(paramData, 
											AjiaItemParamData.class);
						//3.3.5 ��listȡ����ɫ�Ȳ���
							paramsList=paramDataList.get(0).getParams();
						}
					ajiaOrderItem.setParamsList(paramsList);	
					}
				//3.4 ����orderVo,��vo��ֵ������Ϣ����Ʒ��Ϣ
					OrderVO orderVO=new OrderVO();
					orderVO.setAjiaOrder(ajiaOrder);
					orderVO.setAjiaOrderItems(orderItems);
					
					orderVOs.add(orderVO);
				}
				//��ҳ�����������õ�ǰҳ��ʾ������
				//pageInfo.setList(orderVOs);
				//4,����pageInfo
				return pageInfo;
			
	}

	@Override
	public AjiaOrder findOrderByOrderid(String orderId) throws Exception {
AjiaOrder ajiaOrder=ajiaOrderMapper
.selectByPrimaryKey(orderId);
		return ajiaOrder;
	}

	@Override
	public int updateOrderStatus(String orderId, int status) throws Exception {
//update ajia_order set status=8 where orderId=100
		AjiaOrder ajiaOrder=new AjiaOrder();
		ajiaOrder.setStatus(status);
		
		AjiaOrderExample example=new AjiaOrderExample();
		AjiaOrderExample.Criteria criteria=example.or();
		criteria.andOrderIdEqualTo(orderId);
		
		int row=ajiaOrderMapper
				.updateByExampleSelective
				(ajiaOrder, example); 
		return row;
	}

}
