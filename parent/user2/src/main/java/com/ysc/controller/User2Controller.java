package com.ysc.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ysc.entity.User2;
import com.ysc.respository.User2Respository;

@RestController
@RequestMapping("/user2")
public class User2Controller {

	@Autowired
	User2Respository ur;
	
	@RequestMapping("/findUserByPhone")
	public Map<String,Object> create(String phone) {
		
		Map<String, Object> returnMap = new HashMap<String, Object>();
		try {
			User2 user2 = ur.findByPhone(phone);
			if(user2 == null) {
				returnMap.put("user", null);
				returnMap.put("exist", false);
			} else {
				returnMap.put("exist", true);
			}
			returnMap.put("user", user2);
			returnMap.put("code", 20000);
			returnMap.put("msg", "成功");
			
		} catch (Exception e) {
			e.printStackTrace();
			returnMap.put("code", 0);
			returnMap.put("msg", "失败");
		}
		return returnMap;
	}
	
	
	@RequestMapping("/create")
	public Map<String,Object> createUser(@RequestParam("phone")String phone, 
			@RequestParam("name")String name) {
		System.out.println(phone);
		// List<Map<Integer,Integer>> couponList
		User2 user = new User2();
		// 新登录用户 送30 - 5 的优惠券
		// map 前端我取不出来数据 所以把map换成list 嵌套一个list   里面list 的0号为满30  1号为减5
		List<List<Integer>> list = new ArrayList<List<Integer>>();
		List<Integer> list2 = new ArrayList<Integer>();
		list2.add(30);
		
		list2.add(5);
		list.add(list2);
		user.setPhone(phone);
		user.setName(name);
		user.setCouponList(list);
//		Map<String, Integer> map = new HashMap<String, Integer>();
		Map<String, Object> returnMap = new HashMap<String, Object>();
		try {
			
			ur.insert(user);
			returnMap.put("code", 20000);
			returnMap.put("msg", "成功");
			
		} catch (Exception e) {
			e.printStackTrace();
			returnMap.put("code", 0);
			returnMap.put("msg", "失败");
		}
		return returnMap;
	}
	
	@RequestMapping("/addOrder")
	public Map<String,Object> create() {
		
		Map<String, Object> returnMap = new HashMap<String, Object>();
		
		try {
			
			returnMap.put("code", 20000);
			returnMap.put("msg", "成功");
			
		} catch (Exception e) {
			e.printStackTrace();
			returnMap.put("code", 0);
			returnMap.put("msg", "失败");
		}
		return returnMap;
	}
	
	@RequestMapping("/getCoupon")
	public Map<String,Object> getCoupon(String phone) {
		
		Map<String, Object> returnMap = new HashMap<String, Object>();
		
		try {
			User2 u = ur.findByPhone(phone);
			returnMap.put("code", 20000);
			returnMap.put("msg", "成功");
			returnMap.put("coupons", u.getCouponList());
			
		} catch (Exception e) {
			e.printStackTrace();
			returnMap.put("code", 0);
			returnMap.put("msg", "失败");
		}
		return returnMap;
	}
	@RequestMapping("/findAddressList")
	public Map<String,Object> findAddressList(String phone) {
		
		Map<String, Object> returnMap = new HashMap<String, Object>();
		try {
			
			User2 u = ur.findByPhone(phone);
			List<String> list = u.getAddressList();
			returnMap.put("list", list);
			returnMap.put("code", 20000);
			returnMap.put("msg", "成功");
			
		} catch (Exception e) {
			e.printStackTrace();
			returnMap.put("code", 0);
			returnMap.put("msg", "失败");
		}
		return returnMap;
	}
	@RequestMapping("/addAddress")
	public Map<String,Object> addAddress(String address,String phone) {
		Map<String, Object> returnMap = new HashMap<String, Object>();
		try {
			User2 u = ur.findByPhone(phone);
			List<String> list = u.getAddressList();
			if (list == null) {
				List<String> addressList = new ArrayList<String>();
				addressList.add(address);
				u.setAddressList(addressList );
			} else {
				list.add(address);
				u.setAddressList(list );
			}
			ur.deleteByPhone(phone);
			ur.insert(u);
			returnMap.put("code", 20000);
			returnMap.put("msg", "成功");

		} catch (Exception e) {
			e.printStackTrace();
			returnMap.put("code", 0);
			returnMap.put("msg", "失败");
		}
		return returnMap;
	}
//	@RequestMapping("/create")
//	public Map<String,Object> create() {
//		
//		
//		
//		Map<String, Integer> map = new HashMap<String, Integer>();
//		Map<String, Object> returnMap = new HashMap<String, Object>();
//		map.put("abc", 11);
//		try {
//			
//			
//			returnMap.put("code", 20000);
//			returnMap.put("msg", "成功");
//			
//		} catch (Exception e) {
//			e.printStackTrace();
//			returnMap.put("code", 0);
//			returnMap.put("msg", "失败");
//		}
//		return returnMap;
//	}
	
	@RequestMapping("/delete")
	public Map<String,Object> delete(@RequestParam("phone")String phone) {
		Map<String, Object> returnMap = new HashMap<String, Object>();
		try {
			ur.deleteByPhone(phone);
			returnMap.put("code", 20000);
			returnMap.put("msg", "成功");
		} catch (Exception e) {
			e.printStackTrace();
			returnMap.put("code", 0);
			returnMap.put("msg", "失败");
		}
		return returnMap;
	}
	
	@RequestMapping("/update")
	public Map<String,Object> update() {
		Map<String, Object> returnMap = new HashMap<String, Object>();
		
		return null;
	}
	
	
	
	// name => 菜品名字   这个 接口是为了得到  每个菜在购物车中的数量
	@RequestMapping("/getCount")
	public Map<String,Object> getCount(@RequestParam("name")String name) {
		return null;
	}
	
}
