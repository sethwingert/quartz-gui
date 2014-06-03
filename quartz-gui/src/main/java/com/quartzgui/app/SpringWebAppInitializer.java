package com.quartzgui.app;

import javax.servlet.ServletContext;

import org.springframework.web.WebApplicationInitializer;
import org.springframework.web.context.ContextLoaderListener;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;

public class SpringWebAppInitializer implements WebApplicationInitializer {
	@Override
    public void onStartup(ServletContext container) {
      // Create the 'root' Spring application context
      AnnotationConfigWebApplicationContext rootContext = new AnnotationConfigWebApplicationContext();
      rootContext.register(SpringWebAppConfig.class);
      // Manage the lifecycle of the root application context
      container.addListener(new ContextLoaderListener(rootContext));
    }
}
