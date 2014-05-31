package com.quartzgui.dao;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import com.quartzgui.jmx.JmxServerConfig;

/**
 * Used primarily for testing
 * 
 * @author Seth
 *
 */
public class ServerConfigMemoryDao implements ServerConfigDao {

	/**In Memory Database**/
	protected static Map<String, JmxServerConfig> clientConfigs = new ConcurrentHashMap<>();
	
	@Override
	public List<JmxServerConfig> findClientConfigs() {
		return new ArrayList<>(clientConfigs.values());
	}

	@Override
	public void createClientConfig(JmxServerConfig config) {
		clientConfigs.put(config.getId(), config);
		
	}

	@Override
	public JmxServerConfig findClientConfigById(String id) {
		return clientConfigs.get(id);
	}

	@Override
	public void deleteClientConfigById(String id) {
		clientConfigs.remove(id);
	}

	@Override
	public void updateClientConfig(JmxServerConfig config) {
		clientConfigs.put(config.getId(), config);
	}

}
