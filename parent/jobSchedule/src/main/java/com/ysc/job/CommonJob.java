package com.ysc.job;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;

import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.ysc.entity.User2;
import com.ysc.respository.User2Respository;

@Component
public class CommonJob implements BaseJob{
	
	public static CommonJob cj;
	
	@Autowired
	User2Respository ur;
	@PostConstruct
	public void init() {
		cj = this;
		cj.ur = this.ur;
	}
	
	
	public static void main(String[] args) {
		System.out.println(Math.round(-1.5) ); 
	}
	
	private static Logger log = LoggerFactory.getLogger(CommonJob.class);

	
	@Override
	public void execute(JobExecutionContext context) throws JobExecutionException {

		List<User2> list = cj.ur.findAll();
		
		cj.ur.deleteAll();
		
		for(int i=0;i<list.size();i++) {
			User2 u = list.get(i);
			List<List<Integer>> couponList = u.getCouponList();
			List<Integer> list2 = new ArrayList<Integer>();
			list2.add(50);
			list2.add(10);
			couponList.add(list2);
			u.setCouponList(couponList);
		}
		cj.ur.insert(list);
		
		log.warn("CommonJob执行时间 :"+ new Date());
	}

}
