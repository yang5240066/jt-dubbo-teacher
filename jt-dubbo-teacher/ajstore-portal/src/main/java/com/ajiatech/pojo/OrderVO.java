package com.ajiatech.pojo;

import java.util.List;

//��ҳ����ʾ��һ������
public class OrderVO {
	//������Ϣ
	AjiaOrder ajiaOrder;
	
	//�����еĶ����Ʒ��Ϣ
	List<AjiaOrderItem> ajiaOrderItems;

	@Override
	public String toString() {
		return "OrderVO [ajiaOrder=" + ajiaOrder + ", ajiaOrderItems=" + ajiaOrderItems + "]";
	}

	public AjiaOrder getAjiaOrder() {
		return ajiaOrder;
	}

	public void setAjiaOrder(AjiaOrder ajiaOrder) {
		this.ajiaOrder = ajiaOrder;
	}

	public List<AjiaOrderItem> getAjiaOrderItems() {
		return ajiaOrderItems;
	}

	public void setAjiaOrderItems(List<AjiaOrderItem> ajiaOrderItems) {
		this.ajiaOrderItems = ajiaOrderItems;
	}
	

}
