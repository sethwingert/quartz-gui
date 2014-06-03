package com.quartzgui.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import javax.management.MalformedObjectNameException;

import org.quartz.SchedulerException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.quartzgui.dao.ServerConfigDao;
import com.quartzgui.dao.ServerConfigMemoryDao;
import com.quartzgui.jmx.JmxClient;
import com.quartzgui.jmx.JmxServerConfig;

/**
 * Application level singleton. Use this to initialize, create, and retrieve JMX Clients.
 * 
 * @author Seth
 * 
 */
public class JmxService {

	Logger logger = LoggerFactory.getLogger(JmxService.class);

	/** JmxClients are stored in memory so no need to connect to JMX server with every request **/
	private final Map<String, JmxClient> jmxClientCache = new ConcurrentHashMap<String, JmxClient>();

	private final ServerConfigDao serverConfigDao;
	
	private static JmxService singletonJmxService = new JmxService();
	
	//make protected to defeat instnatiation/subclassing except by those in same package
	protected JmxService() {
		// Use properties file to determine which configDao to get**/
		serverConfigDao = new ServerConfigMemoryDao();
		// load up prestored clients
		for (JmxServerConfig clientConfig : serverConfigDao.findServerConfigs()) {
			try {
				getJmxClient(clientConfig);
			} catch (MalformedObjectNameException | IOException | SchedulerException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	public static JmxService getInstance() {
		return singletonJmxService;
	}

	/**
	 * Gets a JMX client from cache or create one and store in cache if it doesn't exist.
	 * 
	 * @param serverConfig
	 * @return
	 * @throws MalformedObjectNameException
	 * @throws IOException
	 * @throws SchedulerException 
	 */
	public JmxClient getJmxClient(JmxServerConfig serverConfig) throws MalformedObjectNameException, IOException, SchedulerException {
		return getJmxClientByConfigId(serverConfig.getId());
	}

	/**
	 * TODO: Handle non-existent JMX client or connection closed or whatnot
	 * 
	 * @param jmxClientId
	 * @return
	 * @throws IOException
	 * @throws MalformedObjectNameException
	 * @throws SchedulerException 
	 */
	public synchronized JmxClient getJmxClientByConfigId(String jmxServerId) throws SchedulerException {
		JmxClient existingClient = jmxClientCache.get(jmxServerId);
		if (existingClient == null) {
			JmxServerConfig serverConfig = serverConfigDao.findServerConfigById(jmxServerId);
			if (serverConfig == null) {
				throw new SchedulerException("Cannot find server. Please save a server configuration first.");
			}
			JmxClient client = new JmxClient(serverConfig);
			jmxClientCache.put(jmxServerId, client); // store in cache
			return client;
		}
		return existingClient;
	}

	public List<JmxClient> findAllJmxClients() {
		return new ArrayList<>(jmxClientCache.values());
	}

	/**
	 * TODO: Hook onto JVM shutting down probably through a ServletContextListener
	 * 
	 * @throws IOException
	 */
	public void destroy() {
		for (JmxClient client : jmxClientCache.values()) {
			client.close();
		}
	}
}
