package com.quartzgui.test;

import static org.junit.Assert.assertTrue;
import static org.quartz.JobBuilder.newJob;
import static org.quartz.TriggerBuilder.newTrigger;

import org.junit.Before;
import org.junit.Test;
import org.quartz.JobDetail;
import org.quartz.SchedulerException;
import org.quartz.SimpleScheduleBuilder;
import org.quartz.Trigger;

public class TestBasicJob extends BaseQuartzTest {

	JobDetail helloWorldJob;
	
	@Before
	public void init() throws SchedulerException {
		// define the job and tie it to our HelloJob class
	    helloWorldJob = newJob(HelloWorldJob.class)
	        .withIdentity("job1", "group1")
	        .build();

	    // Trigger the job to run now, and then repeat every 40 seconds
	    Trigger trigger = newTrigger()
	        .withIdentity("trigger1", "group1")
	        .startNow()
	        .withSchedule(SimpleScheduleBuilder.simpleSchedule()
	                .withIntervalInSeconds(40)
	                .repeatForever())            
	        .build();

	    // Tell quartz to schedule the job using our trigger
	    scheduler.scheduleJob(helloWorldJob, trigger);
	}
	
	
	@Test
	public void testHelloWorldJobExists() throws Exception {
		assertTrue("Hello world job not in scheduler", scheduler.checkExists(helloWorldJob.getKey()));
	}
}
