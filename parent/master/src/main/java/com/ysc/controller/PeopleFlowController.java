package com.ysc.controller;

import java.text.ParseException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ysc.entity.PeopleFlow;
import com.ysc.service.PeopleFlowService;

import io.swagger.annotations.ApiOperation;

@RestController
public class PeopleFlowController {
	
	@Autowired
	PeopleFlowService service;
	@ApiOperation("getFlow")
	@PostMapping("/getFlow")
	public Map<String,Object> get7DaysFlow() throws ParseException {
		Map<String,Object> map = new HashMap<String, Object>();
		Map<String,Object> dataMap = new HashMap<String, Object>();
		map.put("code", 20000);
		List<PeopleFlow> list = service.get7DaysFlow();
		dataMap.put("list", list);
		map.put("data", dataMap);
		return map;
	}
}
