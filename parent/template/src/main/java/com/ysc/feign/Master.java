package com.ysc.feign;

import java.text.ParseException;
import java.util.List;
import java.util.Map;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.ysc.entity.Food;
import com.ysc.util.Result;

@FeignClient(value = "master")
public interface Master {

	@PostMapping("/getFlow")
	public Map<String,Object> get7DaysFlow() throws ParseException;
	
	@PostMapping(value = "/getFoods")
	public Map<String,Object> getFoods(@RequestParam(value="page",required = false) Integer page,
			@RequestParam(value="rows",required = false) Integer rows);
	
	@RequestMapping(value = "/edit",method = {RequestMethod.POST},produces = { "application/json;charset=UTF-8" })
	public Result editFood(@RequestParam("id") Integer id,
			@RequestParam("name") String name,@RequestParam("describe") String describe,
			@RequestParam("price") Double price);
	
	@PostMapping(value= "/delete")
	public Result deleteFood(@RequestParam("id") Integer id);
}
