package edu.uz.crawler;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.concurrent.ConcurrentLinkedQueue;

import android.annotation.SuppressLint;
import android.app.IntentService;
import android.content.Intent;
import android.os.AsyncTask;
import android.util.Log;
import edu.uci.ics.crawler4j.url.WebURL;
import edu.uz.crawler.config.CrawlingConfiguration;
import edu.uz.crawler.controller.CrawlingController;
import edu.uz.crawler.controller.CrawlingMonitor;

@SuppressLint("NewApi")
public class CrawlingResultProvider extends IntentService {
	public static final String RESULT_NAME = "CRAWLING_RESULT";

	public CrawlingResultProvider() {
		super(CrawlingResultProvider.class.getName());
	}

	@Override
	protected void onHandleIntent(final Intent intent) {
		final CrawlingSettings settings = getCrawlingSettings(intent);
		try {
			startCrawler(settings);
		} catch (Exception e) {
			Log.e("CRAWLER", e.getMessage());
		}
	}

	private CrawlingSettings getCrawlingSettings(final Intent intent) {
		return (CrawlingSettings) intent.getSerializableExtra(CrawlingJob.CRAWLING_SETTINGS);
	}

	private synchronized void startCrawler(final CrawlingSettings settings) throws Exception {
		final WebURL url = new WebURL();
		url.setURL(settings.getWebpageUrl());
		final ArrayList<String> topicsList = settings.getTopics();
		final String[] topics = topicsList.toArray(new String[topicsList.size()]);
		final edu.uz.crawler.config.CrawlingSettings setttings = new edu.uz.crawler.config.CrawlingSettings(url, topics);
		final CrawlingConfiguration config = new CrawlingConfiguration(createTempDirectory());
		final CrawlingController controller = new CrawlingController(config, setttings);

		final CrawlingMonitor crawlerMonitor = controller.start();
		Log.i("CRAWLER", "Crawler started with parameters:");
		Log.i("CRAWLER", "URL: " + url.getURL());
		Log.i("CRAWLER", "Topics: " + topicsList.toString());

		runSender(crawlerMonitor);

		new AsyncTask<Object, Void, Void>() {
			private static final String TAG = "STOPPER";

			@Override
			protected Void doInBackground(Object... params) {
				try {
					int numberOfMinutesToSleep = 2;
					int minutesToMiliseconds = numberOfMinutesToSleep * 60 * 1000;
					Thread.sleep(minutesToMiliseconds);
				} catch (InterruptedException e) {
					Log.e(TAG, e.getMessage());
				}
				controller.stop();
				Log.e(TAG, "Crawler stopped");
				
				return null;
			}
		}.doInBackground(crawlerMonitor);
	}

	private void runSender(final CrawlingMonitor monitor) {
		new Runnable() {
			private final CrawlingMonitor crawlingMonitor = monitor;

			@Override
			public void run() {
				while (!crawlingMonitor.isFinished()) {
					ConcurrentLinkedQueue<edu.uz.crawler.CrawledPage> pagesToSave = Crawler.PAGES_TO_SAVE;

					while (!pagesToSave.isEmpty()) {
						edu.uz.crawler.CrawledPage page = pagesToSave.poll();
						sendByBroadcast(page);

						Log.i("CRAWLED", page.getTitle());
						Log.i("CRAWLED", page.getUrl());
					}
				}
			}

		}.run();
	}

	public static File createTempDirectory() throws IOException {
		final File temp;

		temp = File.createTempFile("temp", Long.toString(System.nanoTime()));
		temp.deleteOnExit();

		if (!(temp.delete())) {
			throw new IOException("Could not delete temp file: " + temp.getAbsolutePath());
		}

		if (!(temp.mkdir())) {
			throw new IOException("Could not create temp directory: " + temp.getAbsolutePath());
		}

		return (temp);
	}

	private Intent sendByBroadcast(final edu.uz.crawler.CrawledPage crawledPage) {
		Intent result = new Intent();

		result.setAction(CrawlingJob.CRAWLING_ACTION_RESPONSE);
		result.addCategory(Intent.CATEGORY_DEFAULT);
		result.putExtra(RESULT_NAME, crawledPage);

		sendBroadcast(result);

		return result;
	}

}