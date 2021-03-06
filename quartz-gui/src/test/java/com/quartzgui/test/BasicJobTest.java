package com.quartzgui.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.quartz.JobBuilder.newJob;
import static org.quartz.TriggerBuilder.newTrigger;

import org.junit.Before;
import org.junit.Test;
import org.quartz.JobDetail;
import org.quartz.SchedulerException;
import org.quartz.SimpleScheduleBuilder;
import org.quartz.Trigger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;

import com.quartzgui.dao.ServerConfigDao;
import com.quartzgui.dao.ServerConfigMemoryDao;
import com.quartzgui.jmx.JmxClient;
import com.quartzgui.jmx.JmxServerConfig;

@ContextConfiguration(classes=ServerConfigMemoryDao.class)
public class BasicJobTest extends BaseQuartzTest {

	JobDetail helloWorldJob;
	
	@Autowired
	ServerConfigDao dao;
	
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
	public void testLocalJmxAccess() throws Exception {
		JmxServerConfig config = new JmxServerConfig();
		config.setLocal(true);
		JmxClient client = new JmxClient(config);
		assertEquals(1, client.getSchedulers().size());
		JobDetail remoteJobDetail = client.getSchedulers().values().iterator().next().getJobDetail(helloWorldJob.getKey());
		assertEquals("job1",remoteJobDetail.getKey().getName());
		client.close();
	}
	
	@Test
	public void testServerConfigSave() throws Exception {
		JmxServerConfig config = new JmxServerConfig();
		config.setLocal(true);
		dao.saveServerConfig(config);
		JmxServerConfig foundConfig = dao.findServerConfigById(config.getId());
		assertNotNull(foundConfig);
		assertTrue(foundConfig.getLocal());
	}
}
