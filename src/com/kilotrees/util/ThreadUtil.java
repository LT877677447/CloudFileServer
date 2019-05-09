package com.kilotrees.util;

public class ThreadUtil {

	public static void trySleep(long millis) {
		try {
			Thread.sleep(millis);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
