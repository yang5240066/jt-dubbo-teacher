package com.ajiatech.pojo;

import org.apache.solr.client.solrj.beans.Field;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

//����������solr�е�field��ӳ���ϵ(��Ӧ��ϵ )
//��solr��������ӵ���AjiaItemSolr�Ķ��󣬶����ж������

//������δ֪����
@JsonIgnoreProperties(ignoreUnknown=true)
public class AjiaItemSolr {

	//���ö����itemId���Ե�ֵ����solr item_id field��
	@Field("item_id")
	private String itemId;
	
	@Field("brand")
	private String brand;
	
	@Field("model")
	private String model;
	
	@Field("title")
	private String title;
	
	@Field("sellPoint")
	private String sellPoint;
	
	@Field("image")
	private String image;
	
	@Field("price")
	private double price;

	public String getItemId() {
		return itemId;
	}

	public void setItemId(String itemId) {
		this.itemId = itemId;
	}

	public String getBrand() {
		return brand;
	}

	public void setBrand(String brand) {
		this.brand = brand;
	}

	public String getModel() {
		return model;
	}

	public void setModel(String model) {
		this.model = model;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getSellPoint() {
		return sellPoint;
	}

	public void setSellPoint(String sellPoint) {
		this.sellPoint = sellPoint;
	}

	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}
	
}
