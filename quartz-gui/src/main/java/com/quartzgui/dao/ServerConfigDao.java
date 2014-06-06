package com.quartzgui.dao;

import java.util.List;

import com.quartzgui.jmx.JmxServerConfig;

/**
 * Interface for each DAO implementation, for instance SQLDao, FileDao,
 * NoSqlDao, etc. Properties file determine which one to use.
 * 
 * @author Seth
 * 
 */
public interface ServerConfigDao {

	/**
	 * Returns all QuartzInstances which contain enough information to connect
	 * to each one via JMX
	 * 
	 * @return
	 */
	List<JmxServerConfig> findServerConfigs();

	JmxServerConfig saveServerConfig(JmxServerConfig config);

	JmxServerConfig findServerConfigById(String id);

	void deleteServerConfigById(String id);

	void updateServerConfig(JmxServerConfig config);
}
