package com.quartzgui.rest;

import java.io.IOException;

import javax.management.MalformedObjectNameException;
import javax.ws.rs.GET;
import javax.ws.rs.Path;

import com.quartzgui.jmx.JmxClient;
import com.quartzgui.jmx.JmxServerConfig;
import com.quartzgui.service.JmxService;

/**
 * Get schedulers for each clientConfig
 * TODO: Path should be something like <code>/rest/server/1234/scheduler/1/job/3</code>
 * or <code>/rest/server/1234/scheduler/1/trigger/1</code>
 * @author sw8840
 *
 */
@Path("/scheduler")
public class QuartzSchedulerRest {
	
	JmxService jmxService = JmxService.getInstance();

	@GET
	public void findAllSchedulers(String jmxClientConfigId) throws MalformedObjectNameException, IOException {
		JmxClient jmxClient = jmxService.getJmxClientByConfigId(jmxClientConfigId);
		jmxClient.getSchedulers(); //there will be a scheduler ID for each one, so user can use that + clientConfigId for subsequent requests
	}
	
	/**
	 * Can pass in full client config instead of grabbing one from configuration file on disk
	 * 
	 * @param clientConfig
	 * @throws MalformedObjectNameException
	 * @throws IOException
	 */
	@GET
	public void findAllSchedulers(JmxServerConfig clientConfig) throws MalformedObjectNameException, IOException {
		JmxClient jmxClient = jmxService.getJmxClient(clientConfig);
		//return list of schedulers
		jmxClient.getSchedulers();  //needs to have a client config ID with it
	}
}
