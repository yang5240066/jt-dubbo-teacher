package com.ajiatech.pojo;

import java.util.List;

//网页上显示的一个订单
public class OrderVO {
	//订单信息
	AjiaOrder ajiaOrder;
	
	//订单中的多个商品信息
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
