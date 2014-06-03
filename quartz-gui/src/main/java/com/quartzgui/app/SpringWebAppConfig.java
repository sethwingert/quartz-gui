package com.quartzgui.app;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import com.quartzgui.dao.ServerConfigFileDao;

@Configuration
@ComponentScan(basePackages="com.quartzgui")
public class SpringWebAppConfig {
	
	@Bean
	public ServerConfigFileDao serverConfigFileDao() {
		return new ServerConfigFileDao();
	}
}
