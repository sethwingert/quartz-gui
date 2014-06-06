package com.quartzgui.dao;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.stereotype.Repository;

import com.quartzgui.jmx.JmxServerConfig;

/**
 * Used primarily for testing
 * 
 * @author Seth
 *
 */
@Repository("serverConfigMemoryDao")
public class ServerConfigMemoryDao implements ServerConfigDao {

	/**In Memory Database**/
	protected static Map<String, JmxServerConfig> clientConfigs = new ConcurrentHashMap<>();
	
	@Override
	public List<JmxServerConfig> findServerConfigs() {
		return new ArrayList<>(clientConfigs.values());
	}

	@Override
	public JmxServerConfig saveServerConfig(JmxServerConfig config) {
		if (config.getId() == null || "".equals(config.getId())) {
			config.generateId();
		}
		clientConfigs.put(config.getId(), config);
		return config;
		
	}

	@Override
	public JmxServerConfig findServerConfigById(String id) {
		return clientConfigs.get(id);
	}

	@Override
	public void deleteServerConfigById(String id) {
		clientConfigs.remove(id);
	}

	@Override
	public void updateServerConfig(JmxServerConfig config) {
		clientConfigs.put(config.getId(), config);
	}

}
