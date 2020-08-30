package com.ysc.feign;

import java.util.Map;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.ysc.util.Result;

@FeignClient(value = "user")
public interface ToUser {

	@RequestMapping(value = "/user/login",method = {RequestMethod.POST},produces = { "application/json;charset=UTF-8" })
	public Map<String, Object> login(@RequestParam("username")String username,@RequestParam("password")String password);
	
	@PostMapping(value = "/user/info", produces = { "application/json;charset=UTF-8" })
	public Map<String, Object> info(@RequestParam("user")String user);
	
	@PostMapping(value = "/user/logout", produces = { "application/json;charset=UTF-8" })
	public Map<String, Object> logout();
	
	@RequestMapping(value = "/user/regist",method = {RequestMethod.POST},produces = { "application/json;charset=UTF-8" })
	public Map<String, Object> regist(@RequestParam("username")String username,
			@RequestParam("password")String password,
			@RequestParam("phone")String phone);
	
	@PostMapping("/user/sendMsg")
	public Map<String, Object> sendMsg();
	
	@PostMapping(value = "/user/active", produces = { "application/json;charset=UTF-8" })
	public Map<String, Object> activeUser(@RequestParam("username") String username);
}
