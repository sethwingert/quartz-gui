package com.quartzgui.jmx;

import javax.management.AttributeList;

import org.quartz.SchedulerException;
import org.quartz.impl.RemoteMBeanScheduler;

public class JmxClient extends RemoteMBeanScheduler {

	@Override
	public void initialize() throws SchedulerException {
	}

	@Override
	protected Object getAttribute(String attribute) throws SchedulerException {
		return null;
	}

	@Override
	protected AttributeList getAttributes(String[] attributes) throws SchedulerException {
		return null;
	}

	@Override
	protected Object invoke(String operationName, Object[] params, String[] signature) throws SchedulerException {
		return null;
	}
}
