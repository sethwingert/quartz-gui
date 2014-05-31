package com.quartzgui.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import javax.management.MalformedObjectNameException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.quartzgui.dao.ServerConfigDao;
import com.quartzgui.dao.ServerConfigMemoryDao;
import com.quartzgui.jmx.JmxClient;
import com.quartzgui.jmx.JmxServerConfig;

/**
 * Application level service with static methods. Use this to initialize, create, and retrieve JMX Clients.
 * Possibly call a getInstance() method on this singleton instead of making everything static.
 * 
 * @author sw8840
 * 
 */
public class JmxService {

	Logger logger = LoggerFactory.getLogger(JmxService.class);

	/** JmxClients are stored in memory so no need to connect to JMX server with every request **/
	private static final Map<String, JmxClient> jmxClientCache = new ConcurrentHashMap<String, JmxClient>();

	private static final ServerConfigDao serverConfigDao;

	static {
		// Use properties file to determine which configDao to get**/
		serverConfigDao = new ServerConfigMemoryDao();
		// load up prestored clients
		for (JmxServerConfig clientConfig : serverConfigDao.findClientConfigs()) {
			try {
				getJmxClient(clientConfig);
			} catch (MalformedObjectNameException | IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	/**
	 * Gets a JMX client from cache or create one and store in cache if it doesn't exist.
	 * 
	 * @param clientConfig
	 * @return
	 * @throws MalformedObjectNameException
	 * @throws IOException
	 */
	public static JmxClient getJmxClient(JmxServerConfig clientConfig) throws MalformedObjectNameException, IOException {
		return getJmxClientByConfigId(clientConfig.getId());
	}

	/**
	 * TODO: Handle non-existent JMX client or connection closed or whatnot
	 * 
	 * @param jmxClientId
	 * @return
	 * @throws IOException
	 * @throws MalformedObjectNameException
	 */
	public static JmxClient getJmxClientByConfigId(String jmxClientConfigId) throws MalformedObjectNameException, IOException {
		JmxClient existingClient = jmxClientCache.get(jmxClientConfigId);
		if (existingClient != null) {
			// TODO: Fix possible race conditions between checking for existing client and creating a new one
			JmxClient client = new JmxClient(serverConfigDao.findClientConfigById(jmxClientConfigId));
			jmxClientCache.put(jmxClientConfigId, client); // store in cache
			return client;
		}
		return existingClient;
	}

	public static List<JmxClient> findAllJmxClients() {
		return new ArrayList<>(jmxClientCache.values());
	}

	/**
	 * TODO: Hook onto JVM shutting down probably through a ServletContextListener
	 * 
	 * @throws IOException
	 */
	public static void destroy() {
		for (JmxClient client : jmxClientCache.values()) {
			client.close();
		}
	}

	public static JmxService getInstance() {
		// TODO Auto-generated method stub
		return null;
	}
}
