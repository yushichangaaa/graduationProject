package com.ysc.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ysc.entity.Food;
import com.ysc.entity.FoodExample;
import com.ysc.mapper.FoodMapper;

@Service
public class MasterService {

	@Autowired
	private FoodMapper mapper;
	
	public List<Food> getFoodByLikeName(String name) {
		return mapper.getByFoodLikeName(name);
	}
	
	public void edit(Food record) {
		mapper.updateByPrimaryKeySelective(record );
	}
	
	public void delete(Integer id) {
		mapper.deleteByPrimaryKey(id);
	}
	
//	public void insert(MultipartFile file) throws IOException {
//		
//		
//		byte[] bts = file.getBytes();
//				
//		Object record;
//		record.setPicture(bts);
//		
//		mapper.insert(record);
//	}
	
	public void insert(Food food) {

		Food record = new Food();
		
		record.setName(food.getName());
		
		record.setPicture(food.getPicture());
		
		record.setPrice(food.getPrice());
		
		if( food.getDescribe() != null) {
			
			record.setDescribe(food.getDescribe());
			
		}
		
		mapper.insertSelective(record);
		
	}
	
	public List<Food> getFood() {
		
		FoodExample example = new FoodExample();
		
		List<Food> list = mapper.selectByExample(example);
		
		return list;
	}
	
}
