package com.quartzgui.test;

import org.junit.After;
import org.junit.Before;
import org.junit.runner.RunWith;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.impl.StdSchedulerFactory;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.AnnotationConfigContextLoader;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(loader=AnnotationConfigContextLoader.class)
public abstract class BaseQuartzTest {

	protected Scheduler scheduler;
	
	@Before
	public void baseInit() throws SchedulerException {
		scheduler = StdSchedulerFactory.getDefaultScheduler();
		scheduler.start();
	}
	
	@After
	public void baseShutdown() throws SchedulerException {
		scheduler.shutdown();
	}
}
