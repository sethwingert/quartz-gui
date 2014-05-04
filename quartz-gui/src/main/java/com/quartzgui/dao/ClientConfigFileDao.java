package com.quartzgui.dao;

import java.util.List;

import com.quartzgui.jmx.JmxClientConfig;


public class ClientConfigFileDao implements ClientConfigDao {
	
	static {
		//load config file here
	}

	@Override
	public List<JmxClientConfig> findClientConfigs() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void createClientConfig(JmxClientConfig config) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public JmxClientConfig findClientConfigById(String id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void deleteClientConfigById(String id) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void updateClientConfig(JmxClientConfig config) {
		// TODO Auto-generated method stub
		
	}
}
