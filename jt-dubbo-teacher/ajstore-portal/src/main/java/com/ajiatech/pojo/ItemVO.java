package com.ajiatech.pojo;
import java.util.*;
//���ﳵ�е�һ����Ʒ

import com.ajiatech.pojo.paramData.Params;
public class ItemVO {
	//������Ϣ
	private AjiaCartItem ajiaCartItem;
	//title,price
	private AjiaItem ajiaItem;
	
	//����
	//ajiaItemParamitem-->paramData
	//[{params:[{},{},{}]},{}]
	List<Params> paramsList;

	public AjiaCartItem getAjiaCartItem() {
		return ajiaCartItem;
	}

	public void setAjiaCartItem(AjiaCartItem ajiaCartItem) {
		this.ajiaCartItem = ajiaCartItem;
	}

	public AjiaItem getAjiaItem() {
		return ajiaItem;
	}

	public void setAjiaItem(AjiaItem ajiaItem) {
		this.ajiaItem = ajiaItem;
	}

	public List<Params> getParamsList() {
		return paramsList;
	}

	public void setParamsList(List<Params> paramsList) {
		this.paramsList = paramsList;
	}

}
