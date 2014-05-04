package com.quartzgui.dao;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import com.quartzgui.jmx.JmxClientConfig;

/**
 * Used primarily for testing
 * 
 * @author Seth
 *
 */
public class ClientConfigMemoryDao implements ClientConfigDao {

	/**In Memory Database**/
	protected Map<String, JmxClientConfig> clientConfigs = new ConcurrentHashMap<>();
	
	@Override
	public List<JmxClientConfig> findClientConfigs() {
		return new ArrayList<>(clientConfigs.values());
	}

	@Override
	public void createClientConfig(JmxClientConfig config) {
		clientConfigs.put(config.getId(), config);
		
	}

	@Override
	public JmxClientConfig findClientConfigById(String id) {
		return clientConfigs.get(id);
	}

	@Override
	public void deleteClientConfigById(String id) {
		clientConfigs.remove(id);
	}

	@Override
	public void updateClientConfig(JmxClientConfig config) {
		clientConfigs.put(config.getId(), config);
	}

}
