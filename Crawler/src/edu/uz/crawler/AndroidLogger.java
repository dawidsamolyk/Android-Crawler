package edu.uz.crawler;

import android.util.Log;

public class AndroidLogger {
	private final static String TAG = "Crawler4j";
	private static boolean enabled = true;

	public static void enable() {
		enabled = true;
	}

	public static void disable() {
		enabled = false;
	}

	public static void logInfo(String message) {
		if (enabled) {
			Log.i(TAG, message);
		} else {
			System.out.println(message);
		}
	}

	public static void logError(String message) {
		if (enabled) {
			Log.e(TAG, message);
		} else {
			System.err.println(message);
		}
	}
}
