package com.ysc.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.ysc.entity.Comment;
import com.ysc.service.CommentService;
import com.ysc.util.Result;

@RestController
public class CommentController {

	@Autowired
	CommentService service;
	
	@RequestMapping(value = "/comment",method = {RequestMethod.POST ,RequestMethod.GET})
	public Result comment(Comment comment) {
		
		int i = service.add(comment);
		
		if(i == 1) {
			return new Result(true, "成功");
		} 
		
		return new Result(false, "失败");
	}
	
	@RequestMapping(value = "/comment2",method = {RequestMethod.POST ,RequestMethod.GET})
	public Map<String,Object> getComments() {
		Map<String,Object> map  = new HashMap<String, Object>();
		Map<String,Object> dataMap = new HashMap<>();
		List<Comment> list = service.getComments();
		map.put("code", 20000);
		dataMap.put("list", list);
		map.put("data", dataMap);
		return map;
	}
	
}
