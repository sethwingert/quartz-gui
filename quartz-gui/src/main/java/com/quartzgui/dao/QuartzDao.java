package com.quartzgui.dao;

import java.util.List;

import com.quartzgui.model.QuartzInstance;

/**
 * Subclass for each DAO implementation, for instance SQLDao, FileDao, NoSqlDao, etc
 * @author Seth
 *
 */
public interface QuartzDao {
	
	/**
	 * Returns all QuartzInstances which contain enough information to connect to each one via JMX
	 * @return
	 */
	List<QuartzInstance> findAllQuartzInstances();
}
