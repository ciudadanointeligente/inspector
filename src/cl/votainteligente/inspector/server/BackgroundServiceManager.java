package cl.votainteligente.inspector.server;

import org.apache.log4j.Logger;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

public class BackgroundServiceManager implements ServletContextListener {
	private static final Logger logger = Logger.getLogger(BackgroundServiceManager.class);
	private Scheduler scheduler;

	@Override
	public void contextDestroyed(ServletContextEvent event) {
		logger.info("Context destroyed");
		scheduler = null;
	}

	@Override
	public void contextInitialized(ServletContextEvent event) {
		logger.info("Context initialized");
		scheduler = Scheduler.getInstance();
		scheduler.run();
	}
}
