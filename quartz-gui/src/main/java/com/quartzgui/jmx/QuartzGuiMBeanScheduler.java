package com.quartzgui.jmx;

import javax.management.AttributeList;
import javax.management.MBeanServerConnection;
import javax.management.ObjectName;

import org.quartz.SchedulerException;
import org.quartz.impl.RemoteMBeanScheduler;
/**
 * Used to connect to one Quartz scheduler on the JMX server.
 * 
 * @author Seth Wingert
 *
 */
public class QuartzGuiMBeanScheduler extends RemoteMBeanScheduler {

	protected MBeanServerConnection connection;
	protected ObjectName objectName;
	
	public QuartzGuiMBeanScheduler(MBeanServerConnection connection, ObjectName objectName) {
		this.connection = connection;
		this.objectName = objectName;
	}

	@Override
	public void initialize() throws SchedulerException {
		//JmxClient takes care of it
	}

	@Override
	protected Object getAttribute(String attribute) throws SchedulerException {
		try {
			return connection.getAttribute(objectName, attribute);
		} catch (Exception e) {
			throw new SchedulerException(e);
		}
	}

	@Override
	protected AttributeList getAttributes(String[] attributes) throws SchedulerException {
		try {
			return connection.getAttributes(objectName, attributes);
		} catch (Exception e) {
			throw new SchedulerException(e);
		}
	}

	@Override
	protected Object invoke(String operationName, Object[] params, String[] signature) throws SchedulerException {
		try {
			return connection.invoke(objectName, operationName, params, signature);
		} catch (Exception e) {
			throw new SchedulerException(e);
		}
	}

}
