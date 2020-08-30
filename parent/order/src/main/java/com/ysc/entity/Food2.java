package com.ysc.entity;

public class Food2 {
	
	private Integer id;
	
	private String picture;
	
	private String name;
	
	private String describe;
	
	private Double price;
	
	private Integer count;

	
	
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getPicture() {
		return picture;
	}

	public void setPicture(String picture) {
		this.picture = picture;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescribe() {
		return describe;
	}

	public void setDescribe(String describe) {
		this.describe = describe;
	}

	public Double getPrice() {
		return price;
	}

	public void setPrice(Double price) {
		this.price = price;
	}

	public Integer getCount() {
		return count;
	}

	public void setCount(Integer count) {
		this.count = count;
	}

	@Override
	public String toString() {
		return "Food2 [id=" + id + ", picture=" + picture + ", name=" + name + ", describe=" + describe + ", price="
				+ price + ", count=" + count + "]";
	}

	
	
}
