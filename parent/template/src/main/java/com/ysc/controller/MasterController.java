package com.ysc.controller;

import java.text.ParseException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.ysc.entity.Food;
import com.ysc.feign.Master;
import com.ysc.util.Result;


@RestController
public class MasterController {

	
	@Autowired
	Master master;
	
	public static Logger log = LoggerFactory.getLogger(MasterController.class);
	
	@PostMapping("/get7DaysFlow")
	public Map<String,Object> get7DaysFlow() throws ParseException {
		return master.get7DaysFlow();
	}
	
	@PostMapping(value = "/getFoodList")
	public Map<String,Object> getFoods(@RequestParam(value="page", required =false) Integer page,
			@RequestParam(value="rows",required = false) Integer rows) {
		return master.getFoods(page,rows);
	}
	
	@RequestMapping(value = "/editFood",method = {RequestMethod.POST},produces = { "application/json;charset=UTF-8" })
	public Map<String,Object> editFood(@RequestParam("id") Integer id,
			@RequestParam("name") String name,@RequestParam("describe") String describe,
			@RequestParam("price") Double price) {
		Map<String,Object> map = new HashMap<String, Object>();
		map.put("code", 20000);
		map.put("result", master.editFood(id, name, describe, price));
		return map;
	}
	
	@PostMapping(value= "/deleteFood")
	public Map<String,Object> deleteFood(@RequestParam("id") Integer id) {
		Map<String,Object> map = new HashMap<String, Object>();
		map.put("code", 20000);
		map.put("result", master.deleteFood(id));
		return map;
	}
}
