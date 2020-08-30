package com.ysc.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ysc.entity.Schedulejob;
import com.ysc.mapper.SchedulejobMapper;

@Service
public class ScheduleJobService {

	@Autowired
	SchedulejobMapper mapper;
	
	public void add(Schedulejob job) {
		
		mapper.insertSelective(job);
	}
	
	public void changeJob(Schedulejob job) {
		mapper.updateByNameGroup(job);
	}
	
	
	public List<Schedulejob> getJobByCondition(Schedulejob job) {
		if(job == null) {
			return mapper.selectByExample(null);
		}
		return mapper.getJobByCondition(job);
	}
	
	public void deleteJob(String jobClassName, String jobGroupName) {
		Schedulejob job = new Schedulejob();
		job.setJobclassname(jobClassName);
		job.setJobgroup(jobGroupName);
		mapper.deleteByNameGroup(job);
	}
	
}
