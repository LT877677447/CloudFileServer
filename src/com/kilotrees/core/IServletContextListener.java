package com.kilotrees.core;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

@WebListener
public class IServletContextListener implements ServletContextListener {

    public void contextInitialized(ServletContextEvent arg0)  { 
    	main_service.getInstance().start(arg0);;
    }
    
    public void contextDestroyed(ServletContextEvent arg0)  { 
    	main_service.getInstance().stop(arg0);
    }
	
}
