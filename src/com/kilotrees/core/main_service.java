package com.kilotrees.core;

import javax.servlet.ServletContextEvent;

import org.apache.log4j.Logger;

import com.kilotrees.util.ThreadUtil;

public class main_service {
	public static Logger log = Logger.getLogger(main_service.class);

	private static main_service instance = null;
	private RefreshThread refreshThread = null;

	private main_service() {
		refreshThread = new RefreshThread();
		refreshThread.start();
	}

	public static main_service getInstance() {
		if (instance == null) {
			instance = new main_service();
		}
		return instance;
	}

	public void start(ServletContextEvent arg0) {
		Config.init(arg0.getServletContext());
	}

	public void stop(ServletContextEvent arg0) {
		refreshThread.doing = false;
		refreshThread.interrupt();
	}

	class RefreshThread extends Thread {

		public boolean doing = true;

		public void run() {
			log.info("RefreshThread run....");

			while (doing) {
				Config.refreshConfigJSON();
				ThreadUtil.trySleep(10 * 1000);
			}

		}
	}
}
