package cl.votainteligente.inspector.server;

import org.apache.log4j.Logger;
import org.springframework.web.context.ContextLoaderListener;

import java.util.TimerTask;

public class EmailNotifierTask extends TimerTask {
	private static final Logger logger = Logger.getLogger(EmailNotifier.class);

	@Override
	public void run() {
		new Thread() {
			@Override
			public void run() {
				logger.info("Begin");

//				try {
//					EmailNotifier emailNotifier = ContextLoaderListener.getCurrentWebApplicationContext().getBean(EmailNotifier.class);
//					emailNotifier.sendNotifications();
//				} catch (Throwable t) {
//					logger.error("EmailNotifier failed", t);
//				}

				logger.info("Finish");
			}
		}.start();
	}
}
