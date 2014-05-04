package com.quartzgui.data;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.enterprise.context.ApplicationScoped;

import com.quartzgui.jmx.JmxClient;

@ApplicationScoped
public class QuartzDatabase {
	
	protected Map<String, JmxClient> jmxClients;
	
	@PostConstruct
	public void init() {
		jmxClients = new HashMap<>();
		//load up config file here and create clients
	}
	
	@PreDestroy
	public void destroy() {
		//close all clients
	}
}
