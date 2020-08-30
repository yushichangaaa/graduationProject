package com.ysc.entity;

public class Order {

	private String uid;

	private String name;
	
	private String phone;

	private String jsonList;

	private String date;

	private Double price;

	private Integer isPay;

	private String address;

	private Integer dr; // 0,1 代表的是是否取消了订单 0代表正常状态 未取消 1代表取消了订单 只有 在isPay为0
						// 的时候 也就是订单未支付的状态才可以 改变dr的值
	
	public String getUid() {
		return uid;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setUid(String uid) {
		this.uid = uid;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getJsonList() {
		return jsonList;
	}

	public void setJsonList(String jsonList) {
		this.jsonList = jsonList;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public Double getPrice() {
		return price;
	}

	public void setPrice(Double price) {
		this.price = price;
	}

	public Integer getIsPay() {
		return isPay;
	}

	public void setIsPay(Integer isPay) {
		this.isPay = isPay;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public Integer getDr() {
		return dr;
	}

	public void setDr(Integer dr) {
		this.dr = dr;
	}

	@Override
	public String toString() {
		return "Order [uid=" + uid + ", phone=" + phone + ", jsonList=" + jsonList + ", date=" + date + ", price="
				+ price + ", isPay=" + isPay + ", address=" + address + ", dr=" + dr + "]";
	}

}