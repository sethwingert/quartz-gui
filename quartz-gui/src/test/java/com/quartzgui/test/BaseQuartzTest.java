package com.quartzgui.test;

import org.junit.After;
import org.junit.Before;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.impl.StdSchedulerFactory;

public class BaseQuartzTest {

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
