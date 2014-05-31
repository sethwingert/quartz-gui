package com.quartzgui.rest;

import javax.enterprise.context.RequestScoped;
import javax.ws.rs.GET;
import javax.ws.rs.Path;

import com.quartzgui.dao.ServerConfigDao;
import com.quartzgui.dao.ServerConfigMemoryDao;

/**
 * TODO: Client config should be changed to "serverConfig" to more accurately reflect what's represented
 * @author sw8840
 *
 */
@Path("/config")
@RequestScoped
public class QuartzClientConfigRest {

	/**TODO: Change this into a singleton and inject maybe**/
	private ServerConfigDao clientConfigDao = new ServerConfigMemoryDao();
	
	@GET
	public void findSavedConfigs() {
		/**Be sure not to list username/password**/
		clientConfigDao.findClientConfigs();
	}
	
	public void createNewConfig(/*JSON ClientConfig*/) {
	}
	
	public void deleteConfig(/*ID of clientConfig*/) {
	}
}