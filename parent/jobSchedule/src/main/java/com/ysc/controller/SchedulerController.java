package com.ysc.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.quartz.CronScheduleBuilder;
import org.quartz.CronTrigger;
import org.quartz.JobBuilder;
import org.quartz.JobDetail;
import org.quartz.JobKey;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.TriggerBuilder;
import org.quartz.TriggerKey;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ysc.entity.Schedulejob;
import com.ysc.job.BaseJob;
import com.ysc.service.ScheduleJobService;


@RestController
@RequestMapping(value="/job")
public class SchedulerController {

	@Autowired
	Scheduler scheduler;
	
	@Autowired
	ScheduleJobService service;
	
	private static Logger log = LoggerFactory.getLogger(SchedulerController.class);
	
	@PostMapping("/getJobByCondition")
	public Map<String,Object> getJobByCondition(Schedulejob job) {
		Map<String,Object> map = new HashMap<String, Object>();
		try {
			List<Schedulejob> list = service.getJobByCondition(job);
			map.put("list", list);
			map.put("code", 20000);
		} catch (Exception e) {
			e.printStackTrace();
			map.put("code", 0);
		}
		return map;
	}
	@PostMapping("/runJobOnce")
	public Map<String,Object> runJobOnce(@RequestParam(value = "jobClassName") String jobClassName,
			@RequestParam(value = "jobGroupName") String jobGroupName) throws SchedulerException {
		Map<String,Object> map = new HashMap<String, Object>();
		try {
			JobKey jobKey = JobKey.jobKey(jobClassName, jobGroupName);
			scheduler.triggerJob(jobKey);
			map.put("code", 20000);
			log.info("runJobOnce成功");
		} catch (Exception e) {
			e.printStackTrace();
			log.info("runJobOnce失败");
			map.put("code", 0);
		}
		return map;
	}
	
	@PostMapping("/add")
	public Map<String,Object> addjob(Schedulejob job) throws Exception {
		Map<String,Object> map = new HashMap<String, Object>();
		try {
//			scheduler.getListenerManager().addJobListener(new SchedulerConfig());
			job.setJobstatus("BLOCKED");
			
			service.add(job);
			
			addJob(job.getJobclassname(), job.getJobgroup(), job.getCronexpression());
			map.put("code", 20000);
//			jobPause(job);

		} catch (Exception e) {

			log.error("addjob出现错误");
			map.put("code", 0);
			throw new Exception(e + "addjob出现错误");

		}
		return map;
	}

	@PostMapping(value = "/pauseJob")
	public Map<String,Object> pausejob(@RequestParam(value = "jobClassName") String jobClassName,
			@RequestParam(value = "jobGroupName") String jobGroupName) throws SchedulerException {
		Map<String,Object> map = new HashMap<String, Object>();
		try {
			jobPause(jobClassName, jobGroupName);
			
			Schedulejob job = new Schedulejob();
			job.setJobclassname(jobClassName);
			job.setJobgroup(jobGroupName);
			job.setJobstatus("PAUSED");
			service.changeJob(job);
			
			map.put("code", 20000);
//			jobPause(job);
			log.warn("pausejob成功");

		} catch (Exception e) {
			e.printStackTrace();
			log.error("addjob出现错误");
			map.put("code", 0);
		}
		return map;
		
	}
	
	@PostMapping(value = "/editJob")
	public Map<String,Object> editJob(Schedulejob job) throws SchedulerException {
		Map<String,Object> map = new HashMap<String, Object>();
		try {
			
			System.out.println(job);
			scheduler.pauseTrigger(TriggerKey.triggerKey(job.getJobclassname(), job.getJobgroup()));
			scheduler.deleteJob(JobKey.jobKey(job.getJobclassname(), job.getJobgroup()));
			addJob(job.getJobclassname(), job.getJobgroup(), job.getCronexpression());
			service.changeJob(job);
			jobPause(job.getJobclassname(), job.getJobgroup());
			
			map.put("code", 20000);
//			jobPause(job);
			log.warn("editJob成功");
		} catch (Exception e) {
			e.printStackTrace();
			log.error("editJob出现错误");
			map.put("code", 0);
		}
		return map;
		
	}
	
	
	@PostMapping(value = "/resumeJob")
	public Map<String,Object> resumeJob(@RequestParam(value = "jobClassName") String jobClassName,
			@RequestParam(value = "jobGroupName") String jobGroupName) throws SchedulerException {
		Map<String,Object> map = new HashMap<String, Object>();
		try {
			
			Schedulejob job = new Schedulejob();
			job.setJobclassname(jobClassName);
			job.setJobgroup(jobGroupName);
			resume(job);
			job.setJobstatus("BLOCKED");
			service.changeJob(job);
			
			map.put("code", 20000);
//			jobPause(job);
			log.warn("resumeJob成功");
		} catch (Exception e) {
			e.printStackTrace();
			log.error("addjob出现错误");
			map.put("code", 0);
		}
		return map;
		
	}
	
	
	public void resume(Schedulejob job) throws SchedulerException {
		scheduler.resumeJob(JobKey.jobKey(job.getJobclassname(), job.getJobgroup()));
	}
	@PostMapping(value = "/deleteJob")
	public Map<String,Object> deleteJob(@RequestParam(value = "jobClassName") String jobClassName,
			@RequestParam(value = "jobGroupName") String jobGroupName) throws SchedulerException {
		Map<String,Object> map = new HashMap<String, Object>();
		try {
			map.put("code", 20000);
			scheduler.pauseTrigger(TriggerKey.triggerKey(jobClassName, jobGroupName));
			scheduler.deleteJob(JobKey.jobKey(jobClassName, jobGroupName));
			service.deleteJob(jobClassName, jobGroupName);
		} catch (Exception e) {
			e.printStackTrace();
			log.error("deleteJob出现错误");
			map.put("code", 0);
		
		}
		return map;
	}
	
	private void jobPause(String jobClassName, String jobGroupName) throws SchedulerException {
		scheduler.pauseJob(JobKey.jobKey(jobClassName, jobGroupName));
	}


	private void addJob(String jobclassname, String jobgroup, String cronexpression) throws Exception {
		
		scheduler.start(); 
		
		JobDetail jobDetail = JobBuilder.newJob(getClass(jobclassname).getClass())
				.withIdentity(jobclassname, jobgroup).build();

		CronScheduleBuilder scheduleBuilder = CronScheduleBuilder.cronSchedule(cronexpression);

		CronTrigger trigger = TriggerBuilder.newTrigger().withIdentity(jobclassname, jobgroup)
				.withSchedule(scheduleBuilder).build();
		
		try {

			scheduler.scheduleJob(jobDetail, trigger);

		} catch (SchedulerException e) {

			throw new Exception("创建定时任务失败   " + e);

		}
		
	}

	@PostMapping(value="/reschedulejob")
    public void rescheduleJob(@RequestParam(value="jobClassName")String jobClassName, 
            @RequestParam(value="jobGroupName")String jobGroupName,
            @RequestParam(value="cronExpression")String cronExpression) throws Exception
    {           
        jobreschedule(jobClassName, jobGroupName, cronExpression);
    }
	
	private void jobreschedule(String jobClassName, String jobGroupName, String cronExpression) throws Exception {
		try {
            TriggerKey triggerKey = TriggerKey.triggerKey(jobClassName, jobGroupName);
            // 表达式调度构建器
            CronScheduleBuilder scheduleBuilder = CronScheduleBuilder.cronSchedule(cronExpression);

            CronTrigger trigger = (CronTrigger) scheduler.getTrigger(triggerKey);

            // 按新的cronExpression表达式重新构建trigger
            trigger = trigger.getTriggerBuilder().withIdentity(triggerKey).withSchedule(scheduleBuilder).build();

            // 按新的trigger重新设置job执行
            scheduler.rescheduleJob(triggerKey, trigger);
        } catch (SchedulerException e) {
            System.out.println("更新定时任务失败"+e);
            throw new Exception("更新定时任务失败");
        }
	}

	private BaseJob getClass(String jobclassname) throws ClassNotFoundException, InstantiationException, IllegalAccessException {
		
		Class<?> class1 = Class.forName(jobclassname);

		return (BaseJob) class1.newInstance();
	}
	
}
