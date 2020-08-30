package com.ysc.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ysc.entity.User;
import com.ysc.entity.UserExample;
import com.ysc.entity.UserExample.Criteria;
import com.ysc.mapper.UserMapper;

@Service
public class UserService {

	
	@Autowired
	UserMapper mapper;

	public void addUser(User user) {
		
		User record = new User();
		
		record.setActivate(user.getActivate());
		
		record.setUsername(user.getUsername());
		
		record.setPassword(user.getPassword());
		
		record.setPhone(user.getPhone());
		
		mapper.insertSelective(record );
	}
	
	public User selectUser(User user) {
		
		UserExample example = new UserExample();
		
		Criteria criteria = example.createCriteria();
		
		criteria.andUsernameEqualTo(user.getUsername());
		
		List<User> list = mapper.selectByExample(example);
		
		User u;
		
		try {
			u = list.get(0);
		} catch (Exception e) {
			return null;
		}
		
		if(u != null) {
			
			if(user.getPassword().equals(u.getPassword())) {
				
				return u;
			}
			
			return null;
			
		}
		
		return null;
		
	}
	
	public Integer activeUser(String username) {
		
		User record = new User();
		UserExample example = new UserExample();
		record.setActivate(1);
		Criteria criteria = example.createCriteria();
		criteria.andUsernameEqualTo(username);
		Integer count = mapper.updateByExampleSelective(record, example);
		return count;
	}
	
	
}
