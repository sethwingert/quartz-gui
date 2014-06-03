package com.quartzgui.dao;

import java.util.List;

import com.quartzgui.jmx.JmxServerConfig;


public class ServerConfigFileDao implements ServerConfigDao {
	
	static {
		//load config file here
	}

	@Override
	public List<JmxServerConfig> findServerConfigs() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void createServerConfig(JmxServerConfig config) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public JmxServerConfig findServerConfigById(String id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void deleteServerConfigById(String id) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void updateServerConfig(JmxServerConfig config) {
		// TODO Auto-generated method stub
		
	}
}
