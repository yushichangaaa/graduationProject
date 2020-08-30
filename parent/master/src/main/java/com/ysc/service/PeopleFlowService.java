package com.ysc.service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ysc.entity.PeopleFlow;
import com.ysc.entity.PeopleFlowExample;
import com.ysc.entity.PeopleFlowExample.Criteria;
import com.ysc.mapper.PeopleFlowMapper;

@Service
public class PeopleFlowService {

	
	@Autowired
	PeopleFlowMapper mapper;
	
	
	@SuppressWarnings("deprecation")
	public List<PeopleFlow> get7DaysFlow() throws ParseException{
		
		PeopleFlowExample example = new PeopleFlowExample();
		Criteria criteria = example.createCriteria();
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date d = new  Date();
		d.setHours(0);
		d.setSeconds(0);
		d.setMinutes(0);
		String s = d.toLocaleString();
		Date d1 = df.parse(s);
		Calendar cal=Calendar.getInstance();
		cal.setTime(d1);
		cal.add(Calendar.DATE, -6); 
		Date date2 = cal.getTime();
		criteria.andDateBetween(date2, d);
		List<PeopleFlow> list = mapper.selectByExample(example );
		return list;
	}
	
	public static void main(String[] args) throws ParseException {
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date d = new  Date();
		d.setHours(0);
		d.setSeconds(0);
		d.setMinutes(0);
		String s = d.toLocaleString();
		Date d1 = df.parse(s);
		Calendar cal=Calendar.getInstance();
		cal.setTime(d1);
		cal.add(Calendar.DATE, -6); 
		System.out.println(cal.getTime());
	}
	
}
