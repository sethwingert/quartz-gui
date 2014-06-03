package com.quartzgui.jmx;

import java.io.Closeable;
import java.io.IOException;
import java.lang.management.ManagementFactory;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import javax.management.MBeanServerConnection;
import javax.management.MalformedObjectNameException;
import javax.management.ObjectName;
import javax.management.remote.JMXConnector;
import javax.management.remote.JMXConnectorFactory;
import javax.management.remote.JMXServiceURL;

import org.quartz.SchedulerException;
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
	protected Map<String, QuartzGuiMBeanScheduler> schedulers;

	/**
	 * Uses config file to connect to remote JMX server
	 * 
	 * @param config
	 * @throws IOException
	 * @throws MalformedObjectNameException
	 * @throws SchedulerException 
	 */
	public JmxClient(JmxServerConfig config) throws SchedulerException {
		if (config == null) {
			throw new SchedulerException("Configuration cannot be empty");
		}
		id = config.getId(); //same ID as config file
		// StringBuffer stringBuffer = new
		// StringBuffer().append("service:jmx:remoting-jmx://")
		// .append("localhost").append(":").append("4447");
		this.config = config;
		MBeanServerConnection connection = null;
		Set<ObjectName> names = new HashSet<>();
		try {
			if (config.getLocal()!=null && config.getLocal()) {
				connection = ManagementFactory.getPlatformMBeanServer();  //local server
			} else {
				logger.info("Attempting to connect to [{}] with username [{}] and password [{}]", config.getUrl(),
						config.getUsername(), config.getPassword());
				JMXServiceURL jmxServiceUrl = new JMXServiceURL(config.getUrl());
				Map<String, String[]> env = new HashMap<String, String[]>();
				env.put(JMXConnector.CREDENTIALS, new String[] { config.getUsername(), config.getPassword() });
				jmxConnector = JMXConnectorFactory.connect(jmxServiceUrl, env);
				connection = jmxConnector.getMBeanServerConnection();
			}
			ObjectName mBeanName = new ObjectName("quartz:type=QuartzScheduler,*");
			names = connection.queryNames(mBeanName, null);
		} catch (MalformedObjectNameException | IOException e) {
			throw new SchedulerException(e);
		}
		schedulers = new HashMap<>();
		for (ObjectName name : names) {
			QuartzGuiMBeanScheduler quartzScheduler = new QuartzGuiMBeanScheduler(connection, name);
			schedulers.put(quartzScheduler.getSchedulerInstanceId(), quartzScheduler);
		}
	}

	@Override
	public void close() {
		if (jmxConnector == null) {
			return;
		}
		try {
			jmxConnector.close();
		} catch (IOException e) {
			logger.warn("Unable to close connection to JMX",e);
		}
	}
	
	public QuartzGuiMBeanScheduler getSchedulerById(String instanceId) {
		return schedulers.get(instanceId);
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

	public Map<String, QuartzGuiMBeanScheduler> getSchedulers() {
		return schedulers;
	}

	public void setSchedulers(Map<String, QuartzGuiMBeanScheduler> schedulers) {
		this.schedulers = schedulers;
	}
}
