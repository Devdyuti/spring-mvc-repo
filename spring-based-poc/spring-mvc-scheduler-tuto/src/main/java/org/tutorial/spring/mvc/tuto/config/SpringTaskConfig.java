package org.tutorial.spring.mvc.tuto.config;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.SchedulingConfigurer;
import org.springframework.scheduling.config.ScheduledTaskRegistrar;

/*
 * Spring Scheduler Configuration class
 * Scheduled the task based on cron job expression.
 * */
@Configuration
@EnableScheduling
@PropertySource(value = {"file:C:/application.properties"})
public class SpringTaskConfig implements SchedulingConfigurer{

	private static final String OFF_VALUE = "OFF";
	
	@Value("${cron.expression.identifierprocessor}")
	private String cron_expression_IdentifierProcessor;
	
	@Bean
	public Executor scheduledExecutor() {
		return Executors.newScheduledThreadPool(5);
	}
	
	@Override
	public void configureTasks(ScheduledTaskRegistrar taskRegistrar) {
		taskRegistrar.setScheduler(scheduledExecutor());
		
		// if active, register the task - work_submitWorkToCISNET_cron
		addCronTask(taskRegistrar, cron_expression_IdentifierProcessor, new Runnable() {
			public void run() {
				try {
					System.out.println("TASK STARTED ::::devdyuti");
				} catch (Exception e) {
	
				}
			}
		});
	}
	private boolean addCronTask(ScheduledTaskRegistrar taskRegistrar, String cronExpression, Runnable task) {
		boolean scheduled = false;
		// if the cron value is present or the value is "OFF" the task will be
				// skeduled
		if (StringUtils.isNotEmpty(cronExpression) && !StringUtils.equalsIgnoreCase(OFF_VALUE, cronExpression)) {	
			taskRegistrar.addCronTask(task, cronExpression);
			scheduled = true;
		}
		return scheduled;
	}

}
