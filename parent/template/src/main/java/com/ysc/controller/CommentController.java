package com.ysc.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ysc.feign.Comments;

@RestController
public class CommentController {

	@Autowired
	Comments comments;
	
	@RequestMapping(value = "/getComment")
	public Map<String,Object> getComments() {
		return comments.getComments();
		
	}
}
