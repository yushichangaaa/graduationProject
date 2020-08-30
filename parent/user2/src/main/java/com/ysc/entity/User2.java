package com.ysc.entity;

import java.util.List;



public class User2 {

	private String phone;
	
	private List<Order> orderList;
	
	private List<String> addressList;
	
	private List<List<Integer>> couponList;
	
	private String name;
	
	

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public List<Order> getOrderList() {
		return orderList;
	}

	public void setOrderList(List<Order> orderList) {
		this.orderList = orderList;
	}

	public List<String> getAddressList() {
		return addressList;
	}

	public void setAddressList(List<String> addressList) {
		this.addressList = addressList;
	}

	public List<List<Integer>> getCouponList() {
		return couponList;
	}

	public void setCouponList(List<List<Integer>> couponList) {
		this.couponList = couponList;
	}

	@Override
	public String toString() {
		return "User2 [phone=" + phone + ", orderList=" + orderList + ", addressList=" + addressList + ", couponList="
				+ couponList + "]";
	}


}
