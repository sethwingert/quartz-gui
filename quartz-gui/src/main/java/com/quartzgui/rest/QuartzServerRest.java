package com.quartzgui.rest;

import java.util.List;

import javax.enterprise.context.RequestScoped;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;

import com.quartzgui.dao.ServerConfigDao;
import com.quartzgui.dao.ServerConfigMemoryDao;
import com.quartzgui.jmx.JmxServerConfig;

/**
 * @author sw8840
 *
 */
@Path("/server")
@RequestScoped
public class QuartzServerRest {

	/**TODO: Change this into a singleton and inject maybe**/
	private ServerConfigDao serverConfigDao = new ServerConfigMemoryDao();
	
	@GET
	public List<JmxServerConfig> findSavedConfigs() {
		/**Be sure not to list username/password**/
		List<JmxServerConfig> serverConfigs = serverConfigDao.findServerConfigs();
		for (JmxServerConfig config : serverConfigs) {
			config.setPassword("");
			config.setUsername("");
		}
		return serverConfigs;
	}
	
	@POST
	public void createNewConfig(JmxServerConfig config) {
		serverConfigDao.createServerConfig(config);
	}
	
	@DELETE
	@Path("/{id}")
	public void deleteConfig(@PathParam("id") String id) {
		serverConfigDao.deleteServerConfigById(id);
	}
	
	@GET
	@Path("/{id}")
	public void getServer(@PathParam("id") String id) {
		
	}
	
	@Path("/{id}/scheduler")
	public QuartzSchedulerRest getSchedulerRest(@PathParam("id") String serverId) {
		return new QuartzSchedulerRest(serverId);
	}
}
