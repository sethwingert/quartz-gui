package com.quartzgui.service;

import java.io.IOException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import javax.management.MalformedObjectNameException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.quartzgui.dao.ClientConfigDao;
import com.quartzgui.dao.ClientConfigMemoryDao;
import com.quartzgui.jmx.JmxClient;
import com.quartzgui.jmx.JmxClientConfig;

public class JmxService {

	Logger logger = LoggerFactory.getLogger(JmxService.class);
	
	/**Stored in memory so no need to connect to JMX server every time**/
	private Map<String, JmxClient> jmxClients = new ConcurrentHashMap<String, JmxClient>();
	
	private ClientConfigDao clientConfigDao;
	
	public void init() {
		/**Use properties file to determine which configDao to get**/
		clientConfigDao = new ClientConfigMemoryDao();
	}
	
	public JmxClient connect(JmxClientConfig clientConfig) throws MalformedObjectNameException, IOException {
		JmxClient client = new JmxClient(clientConfig);
		//store in cache
		jmxClients.put(client.getId(), client);
		return client;
	}
}
