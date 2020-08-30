package com.ysc.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.ysc.feign.ToUser;

@RestController
public class UserController {

	@Autowired
	ToUser toUser;
	
	@Autowired
	RestTemplate restTemplate;
	
	@RequestMapping(value = "/user/login",method = {RequestMethod.POST},produces = { "application/json;charset=UTF-8" })
	public Map<String, Object> login(@RequestParam("username")String username,@RequestParam("password")String password) {
		return toUser.login(username,password);
	}
	
	@RequestMapping(value = "/user/info", produces = { "application/json;charset=UTF-8" })
	public Map<String, Object> info(@RequestParam("user")String user) {
		return toUser.info(user);
	}
	
	@RequestMapping(value = "/user/logout", produces = { "application/json;charset=UTF-8" })
	public Map<String, Object> logout() {
		return toUser.logout();
	}
	
	@RequestMapping(value = "/user/regist",method = {RequestMethod.POST},produces = { "application/json;charset=UTF-8" })
	public Map<String, Object> regist(@RequestParam("username")String username,
			@RequestParam("password")String password,
			@RequestParam("phone")String phone) {
		return toUser.regist(username, password, phone);
	}
	
	@RequestMapping("/user/sendMsg")
	public Map<String, Object> sendMsg() {
		return toUser.sendMsg();
	}
	
	@RequestMapping(value = "/user/active", produces = { "application/json;charset=UTF-8" })
	public Map<String, Object> activeUser(@RequestParam("username") String username) {
		return toUser.activeUser(username);
	}
	

}
