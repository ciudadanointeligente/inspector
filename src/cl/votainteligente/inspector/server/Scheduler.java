package cl.votainteligente.inspector.server;

import org.apache.log4j.Logger;

import java.util.Timer;

public class Scheduler implements Runnable {
	private static final Logger logger = Logger.getLogger(Scheduler.class);
	private static Scheduler instance = new Scheduler();
	private Timer timer;

	private Scheduler() {
		timer = new Timer();
	}

	public static Scheduler getInstance() {
		return instance;
	}

	@Override
	public void run() {
		logger.info("Running");
		Timer timer = new Timer();
		timer.scheduleAtFixedRate(new EmailNotifierTask(), 60000, 3600000);
	}
}
