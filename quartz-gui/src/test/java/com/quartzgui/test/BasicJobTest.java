package com.quartzgui.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.quartz.JobBuilder.newJob;
import static org.quartz.TriggerBuilder.newTrigger;

import java.lang.management.ManagementFactory;

import javax.management.MBeanServerConnection;
import javax.management.ObjectName;

import org.junit.Before;
import org.junit.Test;
import org.quartz.JobDetail;
import org.quartz.SchedulerException;
import org.quartz.SimpleScheduleBuilder;
import org.quartz.Trigger;
import org.quartz.core.QuartzSchedulerResources;

import com.quartzgui.jmx.QuartzGuiMBeanScheduler;

public class BasicJobTest extends BaseQuartzTest {

	JobDetail helloWorldJob;
	
	@Before
	public void init() throws SchedulerException {
	    helloWorldJob = newJob(HelloWorldJob.class)
	        .withIdentity("job1", "group1")
	        .build();

	    Trigger trigger = newTrigger()
	        .withIdentity("trigger1", "group1")
	        .startNow()
	        .withSchedule(SimpleScheduleBuilder.simpleSchedule()
	                .withIntervalInSeconds(40)
	                .repeatForever())            
	        .build();

	    scheduler.scheduleJob(helloWorldJob, trigger);
	}
	
	
	@Test
	public void testHelloWorldJobExists() throws Exception {
		assertTrue("Hello world job not in scheduler", scheduler.checkExists(helloWorldJob.getKey()));
	}
	
	@Test
	public void testJmxAccess() throws Exception {
		String objectName = QuartzSchedulerResources.generateJMXObjectName(scheduler.getSchedulerName(), scheduler.getSchedulerInstanceId());
		MBeanServerConnection mBeanServer = ManagementFactory.getPlatformMBeanServer();  //local server
		QuartzGuiMBeanScheduler remoteScheduler = new QuartzGuiMBeanScheduler(mBeanServer, new ObjectName(objectName));
		JobDetail remoteJobDetail = remoteScheduler.getJobDetail(helloWorldJob.getKey());
		assertEquals("job1", remoteJobDetail.getKey().getName());
	}
}
