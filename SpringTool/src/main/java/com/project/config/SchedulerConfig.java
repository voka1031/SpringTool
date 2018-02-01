package com.project.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.quartz.CronTriggerFactoryBean;
import org.springframework.scheduling.quartz.MethodInvokingJobDetailFactoryBean;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;

/**
 * @author WeiNung
 * 排程器
 */
@Configuration
public class SchedulerConfig {
	
	@Bean
	public MethodInvokingJobDetailFactoryBean getJobDetail(){
		MethodInvokingJobDetailFactoryBean bean = new MethodInvokingJobDetailFactoryBean();
		bean.setTargetBeanName("routineJob");
		bean.setTargetMethod("doJob");
		bean.setConcurrent(false);
		return bean;
	}
	
	@Bean
	public CronTriggerFactoryBean getTriggers(){
		CronTriggerFactoryBean bean = new CronTriggerFactoryBean();
		bean.setJobDetail(getJobDetail().getObject());
		bean.setCronExpression("0 1/50 * * * ?");
		return bean;
	}
	
	@Bean
	public MethodInvokingJobDetailFactoryBean getJobDetail_2(){
		MethodInvokingJobDetailFactoryBean bean = new MethodInvokingJobDetailFactoryBean();
		bean.setTargetBeanName("routineJob");
		bean.setTargetMethod("doJob_2");
		bean.setConcurrent(false);
		return bean;
	}
	
	@Bean
	public CronTriggerFactoryBean getTriggers_2(){
		CronTriggerFactoryBean bean = new CronTriggerFactoryBean();
		bean.setJobDetail(getJobDetail_2().getObject());
		bean.setCronExpression("0 0/50 * * * ?");
		return bean;
	}
	
	@Bean
	public SchedulerFactoryBean schedulerFactory(){
		SchedulerFactoryBean bean = new SchedulerFactoryBean();
		bean.setTriggers(getTriggers().getObject(),
					     getTriggers_2().getObject());
		bean.setAutoStartup(true);
		bean.setSchedulerName("PracticeScheduler");
		return bean;
	}
}
