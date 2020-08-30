package com.ysc.entity;

import com.ysc.util.BaseEntity;

public class Food extends BaseEntity{
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column food.id
     *
     * @mbg.generated Mon Apr 01 14:44:21 CST 2019
     */
    private Integer id;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column food.picture
     *
     * @mbg.generated Mon Apr 01 14:44:21 CST 2019
     */
    private String picture;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column food.name
     *
     * @mbg.generated Mon Apr 01 14:44:21 CST 2019
     */
    private String name;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column food.describe
     *
     * @mbg.generated Mon Apr 01 14:44:21 CST 2019
     */
    private String describe;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column food.price
     *
     * @mbg.generated Mon Apr 01 14:44:21 CST 2019
     */
    private Double price;

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column food.id
     *
     * @return the value of food.id
     *
     * @mbg.generated Mon Apr 01 14:44:21 CST 2019
     */
    public Integer getId() {
        return id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column food.id
     *
     * @param id the value for food.id
     *
     * @mbg.generated Mon Apr 01 14:44:21 CST 2019
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column food.picture
     *
     * @return the value of food.picture
     *
     * @mbg.generated Mon Apr 01 14:44:21 CST 2019
     */
    public String getPicture() {
        return picture;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column food.picture
     *
     * @param picture the value for food.picture
     *
     * @mbg.generated Mon Apr 01 14:44:21 CST 2019
     */
    public void setPicture(String picture) {
        this.picture = picture == null ? null : picture.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column food.name
     *
     * @return the value of food.name
     *
     * @mbg.generated Mon Apr 01 14:44:21 CST 2019
     */
    public String getName() {
        return name;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column food.name
     *
     * @param name the value for food.name
     *
     * @mbg.generated Mon Apr 01 14:44:21 CST 2019
     */
    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column food.describe
     *
     * @return the value of food.describe
     *
     * @mbg.generated Mon Apr 01 14:44:21 CST 2019
     */
    public String getDescribe() {
        return describe;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column food.describe
     *
     * @param describe the value for food.describe
     *
     * @mbg.generated Mon Apr 01 14:44:21 CST 2019
     */
    public void setDescribe(String describe) {
        this.describe = describe == null ? null : describe.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column food.price
     *
     * @return the value of food.price
     *
     * @mbg.generated Mon Apr 01 14:44:21 CST 2019
     */
    public Double getPrice() {
        return price;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column food.price
     *
     * @param price the value for food.price
     *
     * @mbg.generated Mon Apr 01 14:44:21 CST 2019
     */
    public void setPrice(Double price) {
        this.price = price;
    }

	@Override
	public String toString() {
		return "Food [id=" + id + ", picture=" + picture + ", name=" + name + ", describe=" + describe + ", price="
				+ price + "]";
	}

	public Food(String picture, String name, String describe, Double price) {
		super();
		this.picture = picture;
		this.name = name;
		this.describe = describe;
		this.price = price;
	}

	public Food() {
		super();
	}
    
    
}