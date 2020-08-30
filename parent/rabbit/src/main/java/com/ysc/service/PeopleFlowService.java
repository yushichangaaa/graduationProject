package com.ysc.service;

import java.util.Calendar;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ysc.entity.PeopleFlow;
import com.ysc.mapper.PeopleFlowMapper;

@Service
public class PeopleFlowService {
	

	@Autowired
	PeopleFlowMapper mapper;
	
	
	
	public Integer getFlow() {
		return mapper.selectFlow();
	}
	
	public boolean addFlow() {
		//判断是否是今天，是今天就addOne  非今天直接insertOne
		PeopleFlow pf = mapper.getLastOne();
		if(pf == null) {
			try {
				mapper.insertOne();
				return true;
			} catch (Exception e) {
				return false;
			}
		}
		if(isSameDay(pf.getDate(),new Date())) {
			try {
				mapper.addOne();
			} catch (Exception e) {
				return false;
			}
		}else {
			try {
				mapper.insertOne();
			} catch (Exception e) {
				return false;
			}
		}
		return true;
		
	}
	public static boolean isSameDay(Date date1, Date date2) {
        if(date1 != null && date2 != null) {
            Calendar cal1 = Calendar.getInstance();
            cal1.setTime(date1);
            Calendar cal2 = Calendar.getInstance();
            cal2.setTime(date2);
            return isSameDay(cal1, cal2);
        } else {
            throw new IllegalArgumentException("The date must not be null");
        }
    }

    public static boolean isSameDay(Calendar cal1, Calendar cal2) {
        if(cal1 != null && cal2 != null) {
            return cal1.get(0) == cal2.get(0) && cal1.get(1) == cal2.get(1) && cal1.get(6) == cal2.get(6);
        } else {
            throw new IllegalArgumentException("The date must not be null");
        }
    }
}
