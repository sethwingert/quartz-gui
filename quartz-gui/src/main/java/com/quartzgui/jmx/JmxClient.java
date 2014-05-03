package com.quartzgui.jmx;

import java.io.Closeable;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.management.MBeanServerConnection;
import javax.management.MalformedObjectNameException;
import javax.management.ObjectName;
import javax.management.remote.JMXConnector;
import javax.management.remote.JMXConnectorFactory;
import javax.management.remote.JMXServiceURL;

/**
 * Connects to one Quartz JMX Server.
 * 
 * @author Seth Wingert
 * 
 */
public class JmxClient implements Closeable{

	protected JMXConnector jmxConnector;
	protected List<QuartzGuiMBeanScheduler> schedulers;

	public JmxClient(JmxClientConfig config) throws IOException, MalformedObjectNameException {
		// StringBuffer stringBuffer = new StringBuffer().append("service:jmx:remoting-jmx://")
		// .append("localhost").append(":").append("4447");
		JMXServiceURL jmxServiceUrl = new JMXServiceURL(config.getUrl());
		Map<String, String[]> env = new HashMap<String, String[]>();
		env.put(JMXConnector.CREDENTIALS, new String[] { config.getUsername(), config.getPassword() });
		jmxConnector = JMXConnectorFactory.connect(jmxServiceUrl, env);
		MBeanServerConnection connection = jmxConnector.getMBeanServerConnection();
		ObjectName mBeanName = new ObjectName("quartz:type=QuartzScheduler,*");
	    Set<ObjectName> names = connection.queryNames(mBeanName, null);
	    schedulers = new ArrayList<>();
	    for (ObjectName name : names) {
	    	schedulers.add(new QuartzGuiMBeanScheduler(connection, name));
	    }
	}

	@Override
	public void close() throws IOException {
		jmxConnector.close();
	}

	public List<QuartzGuiMBeanScheduler> getSchedulers() {
		return schedulers;
	}

	public void setSchedulers(List<QuartzGuiMBeanScheduler> schedulers) {
		this.schedulers = schedulers;
	}
}
