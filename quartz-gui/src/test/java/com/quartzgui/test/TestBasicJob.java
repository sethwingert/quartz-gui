package com.quartzgui.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.quartz.JobBuilder.newJob;
import static org.quartz.TriggerBuilder.newTrigger;

import java.lang.management.ManagementFactory;

import javax.management.AttributeList;
import javax.management.MBeanServerConnection;
import javax.management.MalformedObjectNameException;
import javax.management.ObjectName;

import org.junit.Before;
import org.junit.Test;
import org.quartz.JobDetail;
import org.quartz.SchedulerException;
import org.quartz.SimpleScheduleBuilder;
import org.quartz.Trigger;
import org.quartz.core.QuartzSchedulerResources;
import org.quartz.impl.RemoteMBeanScheduler;

public class TestBasicJob extends BaseQuartzTest {

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
		TestRemoteScheduler remoteScheduler = new TestRemoteScheduler(objectName);
		JobDetail remoteJobDetail = remoteScheduler.getJobDetail(helloWorldJob.getKey());
		assertEquals("job1", remoteJobDetail.getKey().getName());
	}
	
	/**
	 * Copied from official Quartz JUnit tests
	 * 
	 * @author sw8840
	 *
	 */
	public static class TestRemoteScheduler extends RemoteMBeanScheduler {

        private MBeanServerConnection mBeanServer;
        private ObjectName objectName;

        public TestRemoteScheduler(String objectName) throws SchedulerException, MalformedObjectNameException {
            this.objectName = new ObjectName(objectName);
            initialize();
        }

        @Override
        public void initialize() throws SchedulerException {
            mBeanServer = ManagementFactory.getPlatformMBeanServer();
        }

        @Override
        protected Object getAttribute(String attribute) throws SchedulerException {
            try {
                return mBeanServer.getAttribute(objectName, attribute);
            } catch (Exception e) {
                throw new SchedulerException(e);
            }
        }

        @Override
        protected AttributeList getAttributes(String[] attributes) throws SchedulerException {
            try {
                return mBeanServer.getAttributes(objectName, attributes);
            }catch (Exception e) {
                throw new SchedulerException(e);
            }

        }

        @Override
        protected Object invoke(String operationName, Object[] params, String[] signature) throws SchedulerException {
            try {
                return mBeanServer.invoke(objectName, operationName, params, signature);
            } catch (Exception e) {
                throw new SchedulerException(e);
            }
        }
    }
}
