package com.quartzgui.dao;

import java.util.List;

import com.quartzgui.jmx.JmxClientConfig;

/**
 * Interface for each DAO implementation, for instance SQLDao, FileDao,
 * NoSqlDao, etc. Properties file determine which one to use.
 * 
 * @author Seth
 * 
 */
public interface ClientConfigDao {

	/**
	 * Returns all QuartzInstances which contain enough information to connect
	 * to each one via JMX
	 * 
	 * @return
	 */
	List<JmxClientConfig> findClientConfigs();

	void createClientConfig(JmxClientConfig config);

	JmxClientConfig findClientConfigById(String id);

	void deleteClientConfigById(String id);

	void updateClientConfig(JmxClientConfig config);
}
