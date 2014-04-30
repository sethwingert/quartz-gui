package com.quartzgui.listener;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Listener to register with container if using the text storage method of quartz-gui
 * 
 * @author Seth
 *
 */
@WebListener
public class FileSettingsListener implements ServletContextListener {

	Logger logger = LoggerFactory.getLogger(FileSettingsListener.class);
	
	@Override
	public void contextInitialized(ServletContextEvent sce) {
		logger.info("Loading quartz-gui.properties file...");
		// TODO Auto-generated method stub
		
	}

	@Override
	public void contextDestroyed(ServletContextEvent sce) {
		// TODO Auto-generated method stub
		
	}

}
