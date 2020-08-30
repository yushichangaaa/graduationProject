package com.ysc.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ysc.entity.User;
import com.ysc.service.UserService;
import com.ysc.util.Result;

@RestController
@RequestMapping("/user")
public class UserController {

	@Autowired
	UserService service;
	
	public static Logger log = LoggerFactory.getLogger(UserController.class);
	
	@RequestMapping(value = "/regist",method = {RequestMethod.POST},produces = { "application/json;charset=UTF-8" })
	public Map<String, Object> regist(@RequestParam("username")String username,
			@RequestParam("password")String password,
			@RequestParam("phone")String phone) {
		Map<String, Object> map = new HashMap<String, Object>();
		try {
		
			User user = new User();
			user.setActivate(0);
			user.setPassword(password);
			user.setPhone(phone);
			user.setUsername(username);
			
			service.addUser(user );
			
			log.info("注册成功");
			
			map.put("code", 20000);
			
		} catch (Exception e) {
			
			log.info("注册失败");
			
			e.printStackTrace();
			map.put("code", 0);
		}
		return map;
		
	}
	
	@RequestMapping(value = "/login",method = {RequestMethod.GET,RequestMethod.POST},produces = { "application/json;charset=UTF-8" })
	public Map<String, Object> login(@RequestParam("username")String username,@RequestParam("password")String password) {
		
		User user = new User();
		user.setUsername(username);
		user.setPassword(password);
		User u = service.selectUser(user );
		
		
		if( u != null ) {
			Map<String, Object> map = new HashMap<String, Object>();
			Map<String, Object> datamap = new HashMap<String, Object>();
			map.put("code", 20000);
			datamap.put("token", "admin-token");
			datamap.put("activate", u.getActivate());
			datamap.put("phone", u.getPhone());
			datamap.put("name", u.getUsername());
			map.put("data", datamap);
			log.info("登录成功");
			return map;
		} else {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("code", 0);
			return map;
		}
		
	}
	
	@RequestMapping(value = "/info", produces = { "application/json;charset=UTF-8" })
	public Map<String, Object> info(String user) {
		Map<String, Object> map = new HashMap<String, Object>();
		Map<String, Object> datamap = new HashMap<String, Object>();
		ArrayList<Object> rolesList = new ArrayList<>();
		rolesList.add(0, user);
		map.put("code", 20000);
		datamap.put("roles", rolesList);
		datamap.put("name", user);
		datamap.put("avatar", "https://wpimg.wallstcn.com/f778738c-e4f8-4870-b634-56703b4acafe.gif");
		map.put("data", datamap);

		return map;
	}
	
	@RequestMapping(value = "/logout", produces = { "application/json;charset=UTF-8" })
	public Map<String, Object> logout() {
		Map<String, Object> map = new HashMap<String, Object>();
		Map<String, Object> datamap = new HashMap<String, Object>();
		ArrayList<Object> rolesList = new ArrayList<>();
		rolesList.add(0, "admin");
		map.put("code", 20000);
		datamap.put("roles", rolesList);
		datamap.put("name", "admin");
		datamap.put("avatar", "https://wpimg.wallstcn.com/f778738c-e4f8-4870-b634-56703b4acafe.gif");
		map.put("data", datamap);

		return map;
	}
	
	
	@RequestMapping(value = "/sendMsg", produces = { "application/json;charset=UTF-8" })
	public Map<String, Object> sendMsg() {
		Map<String, Object> map = new HashMap<>();
		Map<String, Object> datamap  = new HashMap<>();
		map.put("code", 20000);
		datamap.put("activeCode", new Random().nextInt(9999));
		map.put("data", datamap);
		return map;
	}
	
	@RequestMapping(value = "/active", produces = { "application/json;charset=UTF-8" })
	public Map<String, Object> activeUser(@RequestParam("username") String username) {
		
		Integer count = 0;
		Map<String, Object> map = new HashMap<>();
		try {
			count = service.activeUser(username);
		} catch (Exception e) {
			log.info("激活失败");
			e.printStackTrace();
			map.put("code", 0);
		} 
		if (count == 1) {
			map.put("code", 20000);
			log.info("激活成功");
		}
		return map;
	}
	
}
