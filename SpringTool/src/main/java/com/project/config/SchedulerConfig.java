package com.project.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.quartz.CronTriggerFactoryBean;
import org.springframework.scheduling.quartz.MethodInvokingJobDetailFactoryBean;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;

/**
 * @author WeiNung 排程器
 */
@Configuration
public class SchedulerConfig {

	@Bean
	public MethodInvokingJobDetailFactoryBean getTimer() {
		MethodInvokingJobDetailFactoryBean bean = new MethodInvokingJobDetailFactoryBean();
		bean.setTargetBeanName("routineJob");
		bean.setTargetMethod("doJob");
		bean.setConcurrent(false);
		return bean;
	}

	@Bean
	public CronTriggerFactoryBean getTriggers() {
		CronTriggerFactoryBean bean = new CronTriggerFactoryBean();
		bean.setJobDetail(getTimer().getObject());
		bean.setCronExpression("0 0/10 * ? * * *");
		return bean;
	}

	@Bean
	public MethodInvokingJobDetailFactoryBean getDBUpdate() {
		MethodInvokingJobDetailFactoryBean bean = new MethodInvokingJobDetailFactoryBean();
		bean.setTargetBeanName("routineJob");
		bean.setTargetMethod("dbUpdate");
		bean.setConcurrent(false);
		return bean;
	}

	@Bean
	public CronTriggerFactoryBean getTriggers_2() {
		CronTriggerFactoryBean bean = new CronTriggerFactoryBean();
		bean.setJobDetail(getDBUpdate().getObject());
		bean.setCronExpression("0 0 15-23 ? * * *");// 每天下午三點到晚上11點內每小時0分0秒
		return bean;
	}
	
	@Bean
	public MethodInvokingJobDetailFactoryBean doCheck() {
		MethodInvokingJobDetailFactoryBean bean = new MethodInvokingJobDetailFactoryBean();
		bean.setTargetBeanName("routineJob");
		bean.setTargetMethod("doCheck");
		bean.setConcurrent(false);
		return bean;
	}
	
	@Bean
	public CronTriggerFactoryBean getTriggers_3() {
		CronTriggerFactoryBean bean = new CronTriggerFactoryBean();
		bean.setJobDetail(doCheck().getObject());
		bean.setCronExpression("0 0/2 * ? * * *");
		return bean;
	}

	@Bean
	public SchedulerFactoryBean schedulerFactory() {
		SchedulerFactoryBean bean = new SchedulerFactoryBean();
		bean.setTriggers(getTriggers().getObject(), getTriggers_2().getObject());
		bean.setAutoStartup(true);
		bean.setSchedulerName("ProjectScheduler");
		return bean;
	}
}
