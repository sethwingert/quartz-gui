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

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Connects to one Quartz JMX Server. Each server can contain many schedulers, which are
 * represented by a list of {@link QuartzGuiMBeanScheduler}.  Each JmxClient is associated with one
 * {@link JmxServerConfig}.
 * 
 * @author Seth Wingert
 * 
 */
public class JmxClient implements Closeable {

	private static final Logger logger = LoggerFactory.getLogger(JmxClient.class);

	protected final String id;
	protected JmxServerConfig config;
	protected JMXConnector jmxConnector;
	protected List<QuartzGuiMBeanScheduler> schedulers;

	public JmxClient(JmxServerConfig config) throws IOException, MalformedObjectNameException {
		id = config.getId(); //same ID as config file
		// StringBuffer stringBuffer = new
		// StringBuffer().append("service:jmx:remoting-jmx://")
		// .append("localhost").append(":").append("4447");
		this.config = config;
		logger.info("Attempting to connect to [{}] with username [{}] and password [{}]", config.getUrl(),
				config.getUsername(), config.getPassword());
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
	public void close() {
		try {
			jmxConnector.close();
		} catch (IOException e) {
			logger.warn("Unable to close connection to JMX",e);
		}
	}

	public List<QuartzGuiMBeanScheduler> getSchedulers() {
		return schedulers;
	}

	public void setSchedulers(List<QuartzGuiMBeanScheduler> schedulers) {
		this.schedulers = schedulers;
	}

	public JmxServerConfig getConfig() {
		return config;
	}

	public void setConfig(JmxServerConfig config) {
		this.config = config;
	}
	
	public String getId() {
		return id;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		JmxClient other = (JmxClient) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
}
