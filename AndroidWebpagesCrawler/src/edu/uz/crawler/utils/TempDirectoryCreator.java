package edu.uz.crawler.utils;

import java.io.File;
import java.io.IOException;

import android.util.Log;

public class TempDirectoryCreator {
	private static final String TAG = TempDirectoryCreator.class.getName();

	public static File createTempDirectory() throws IOException {
		final File temp = File.createTempFile("webpages_crawler_temp", Long.toString(System.nanoTime()));
		temp.deleteOnExit();

		Log.i(TAG, "Temp directory created: " + temp.getPath());

		if (!(temp.delete())) {
			throw new IOException("Could not delete temp file: " + temp.getAbsolutePath());
		}
		if (!(temp.mkdir())) {
			throw new IOException("Could not create temp directory: " + temp.getAbsolutePath());
		}

		return temp;
	}
}
