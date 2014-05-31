package com.quartzgui.dao;

import java.util.List;

import com.quartzgui.jmx.JmxServerConfig;


public class ServerConfigFileDao implements ServerConfigDao {
	
	static {
		//load config file here
	}

	@Override
	public List<JmxServerConfig> findClientConfigs() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void createClientConfig(JmxServerConfig config) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public JmxServerConfig findClientConfigById(String id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void deleteClientConfigById(String id) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void updateClientConfig(JmxServerConfig config) {
		// TODO Auto-generated method stub
		
	}
}
