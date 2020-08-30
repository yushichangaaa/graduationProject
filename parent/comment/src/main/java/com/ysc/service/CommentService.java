package com.ysc.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ysc.entity.Comment;
import com.ysc.mapper.CommentMapper;

@Service
public class CommentService {

	@Autowired
	CommentMapper mapper;
	
	public int add(Comment comment) {
		
		Comment record = new Comment();
		
		if(comment.getParent() != null) {
			record.setParent(comment.getParent());
		}
		
		record.setName(comment.getName());
		
		record.setText(comment.getText());
		
		int i = mapper.insertSelective(record);
		
		return i;
	}
	
	public List<Comment> getComments() {
		return mapper.selectByExample(null);
	}
}
