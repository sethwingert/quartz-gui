package com.quartzgui.rest;

import java.io.IOException;

import javax.management.MalformedObjectNameException;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;

import org.quartz.SchedulerException;

import com.quartzgui.jmx.JmxClient;
import com.quartzgui.service.JmxService;

/**
 * Get schedulers for each clientConfig
 * TODO: Path should be something like <code>/rest/server/1234/scheduler/1/job/3</code>
 * or <code>/rest/server/1234/scheduler/1/trigger/1</code>
 * @author sw8840
 *
 */
public class QuartzSchedulerRest {
	
	JmxService jmxService = JmxService.getInstance();
	
	String serverId;

	public QuartzSchedulerRest(String serverId) {
		this.serverId = serverId;
	}

	@GET
	public void findAllSchedulers() throws SchedulerException {
		System.out.println("Calling find all schedulers for serverId: " + serverId);
		JmxClient jmxClient = jmxService.getJmxClientByConfigId(serverId);
		jmxClient.getSchedulers(); //there will be a scheduler ID for each one, so user can use that + clientConfigId for subsequent requests
	}
	
	/**
	 * Can pass in full client config instead of grabbing one from configuration file on disk
	 * 
	 * @param clientConfig
	 * @throws MalformedObjectNameException
	 * @throws IOException
	 * @throws SchedulerException 
	 */
	@GET
	@Path("/{id}")
	public void findSchedulerById(@PathParam("id") String schedulerId) throws SchedulerException {
		JmxClient jmxClient = jmxService.getJmxClientByConfigId(serverId);
		//return list of schedulers
		jmxClient.getSchedulers();  //needs to have a client config ID with it
	}
}
