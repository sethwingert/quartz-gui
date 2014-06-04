package com.quartzgui.rest;

import java.util.List;

import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import com.quartzgui.dao.ServerConfigDao;
import com.quartzgui.jmx.JmxServerConfig;

/**
 * @author sw8840
 *
 */
@Path("/server")
@Component
public class QuartzServerRest {

	@Autowired
	@Qualifier("serverConfigMemoryDao") 	//TODO: REad from proeprties file instead
	private ServerConfigDao serverConfigDao;
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
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
		serverConfigDao.saveServerConfig(config);
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
