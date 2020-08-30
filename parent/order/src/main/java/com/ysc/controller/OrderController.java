package com.ysc.controller;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.ysc.entity.MyId;
import com.ysc.entity.Order;
import com.ysc.resposity.OrderRespository;

@RestController
@RequestMapping("/order")
public class OrderController {

	public static Logger log = LoggerFactory.getLogger(OrderController.class);
	
	@Autowired
	OrderRespository or;
	
	@RequestMapping(value = "/deleteOrder",method = {RequestMethod.POST ,RequestMethod.GET},produces = { "application/json;charset=UTF-8" })
	public Map<String,Object> deleteOrder(String uid) throws Exception {
		Map<String,Object> map = new HashMap<String, Object>();
		Order order = or.findByUid(uid);
		order.setDr(1);
		try {
			or.deleteByUid(uid);
			or.insert(order);
			log.info("删除成功");
			map.put("code", 20000);
			map.put("msg", "成功");
		} catch (Exception e) {
			e.printStackTrace();
			log.info("添加失败");
			map.put("code", 0);
			map.put("msg", "失败");
		}
		return map;
	}
	
	@SuppressWarnings("deprecation")
	@RequestMapping(value = "/createOrder",method = {RequestMethod.POST ,RequestMethod.GET},produces = { "application/json;charset=UTF-8" })
	public Map<String,Object> addOrder(Order order) throws Exception {
		Map<String,Object> map = new HashMap<String, Object>();
		try {
			order.setDate(new Date().toLocaleString());
			System.out.println(order);
			String uid = UUID.randomUUID().toString();
			order.setIsPay(0);
			order.setUid(uid);
			order.setDr(0);
			or.insert(order);
//			JSONArray arrayList= JSONArray.parseArray(order.getJsonList());
//			List<Food2> list = JSONObject.parseArray(arrayList.toJSONString(), Food2.class);
//			for(int i=0;i<list.size();i++) {
//				Food2 food = list.get(i);
//				System.out.println(food);
//			}
			log.info("添加成功");
			map.put("code", 20000);
			map.put("msg", "成功");
			map.put("uid", uid);
		} catch (Exception e) {
			
			log.info("添加失败");
			map.put("code", 0);
			map.put("msg", "失败");
			e.printStackTrace();
		}
		return map;
	}
	
	@RequestMapping(value = "/getOrderByUids",method = {RequestMethod.POST ,RequestMethod.GET},produces = { "application/json;charset=UTF-8" })
	public Map<String,Object> getOrderByUids(String uids) {
		JSONArray arrayList= JSONArray.parseArray(uids);
		List<MyId> list = JSONObject.parseArray(arrayList.toJSONString(), MyId.class);
		Map<String,Object> map = new HashMap<String, Object>();
		try {
			List<Order> orderList = new ArrayList<Order>();
			for(int i=0;i<list.size();i++) {
				Order order = or.findByUid(list.get(i).getUid());
				if ( order.getDr() == 0 ) {
					orderList.add(order);
				}
			}
			map.put("code", 20000);
			map.put("list", orderList);
			map.put("msg", "成功");
		} catch (Exception e) {
			e.printStackTrace();
			map.put("code", 0);
			map.put("msg", "失败");
		}
		return map;
	}
}
