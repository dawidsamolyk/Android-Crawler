package edu.uz.crawler;

import java.io.IOException;
import java.util.ArrayList;
import java.util.concurrent.ConcurrentLinkedQueue;

import android.annotation.SuppressLint;
import android.app.IntentService;
import android.content.Intent;
import android.util.Log;
import edu.uci.ics.crawler4j.url.WebURL;
import edu.uz.crawler.config.CrawlingConfiguration;
import edu.uz.crawler.controller.CrawlingController;
import edu.uz.crawler.controller.CrawlingMonitor;
import edu.uz.crawler.utils.TempDirectoryCreator;

@SuppressLint("NewApi")
public class CrawlingResultProvider extends IntentService {
	public static final String PAGES_NUMBER = "CRAWLED_PAGES_NUMBER";
	public static final String ACTION = "CRAWLING_DONE";
	public static boolean stopCrawler = false;
	private static final String TAG = CrawlingResultProvider.class.getName();
	public static final String RESULT_NAME = "CRAWLING_RESULT";
	private CrawlingController controller;
	public static CrawlingMonitor crawlerMonitor;

	public CrawlingResultProvider() {
		super(CrawlingResultProvider.class.getName());
	}

	@Override
	protected void onHandleIntent(final Intent intent) {
		stopCrawler = false;
		CrawlingSettings settings = (CrawlingSettings) intent.getSerializableExtra(CrawlingJob.CRAWLING_SETTINGS);

		int crawledPages = startCrawlingWithNotificationManager(settings);
		sendByBroadcast(crawledPages);
	}

	private int startCrawlingWithNotificationManager(final CrawlingSettings settings) {
		try {
			return startCrawler(settings);
		} catch (Exception e) {
			Log.e(TAG, e.getMessage());
		}
		return 0;
	}

	private synchronized int startCrawler(final CrawlingSettings settings) throws Exception {
		if (controller != null && controller.isCrawlerStarted()) {
			controller.stop();
		}

		controller = new CrawlingController(getConfiguration(), getSettings(settings));
		crawlerMonitor = controller.start();

		return runSender(crawlerMonitor);
	}

	private CrawlingConfiguration getConfiguration() throws IOException {
		return new CrawlingConfiguration(TempDirectoryCreator.createTempDirectory());
	}

	private edu.uz.crawler.config.CrawlingSettings getSettings(final CrawlingSettings settings) {
		final WebURL url = new WebURL();
		url.setURL(settings.getWebpageUrl());
		final ArrayList<String> topicsList = settings.getTopics();
		final String[] topics = topicsList.toArray(new String[topicsList.size()]);
		final edu.uz.crawler.config.CrawlingSettings result = new edu.uz.crawler.config.CrawlingSettings(url, topics);

		Log.i(TAG, "Crawler initialized with parameters:");
		Log.i(TAG, "URL: " + url.getURL());
		Log.i(TAG, "Topics: " + topicsList.toString());

		return result;
	}

	private int runSender(final CrawlingMonitor monitor) {
		int crawledPages = 0;

		while (!monitor.isFinished() && !stopCrawler) {
			ConcurrentLinkedQueue<edu.uz.crawler.CrawledPage> pagesToSave = Crawler.PAGES_TO_SAVE;

			while (!pagesToSave.isEmpty()) {
				edu.uz.crawler.CrawledPage page = pagesToSave.poll();
				sendByBroadcast(page);

				Log.i(TAG, "Downloading page titled " + page.getTitle() + " from: " + page.getUrl());

				crawledPages++;
			}

			if (stopCrawler) {
				controller.stop();
				break;
			}
		}

		return crawledPages;
	}

	private Intent sendByBroadcast(final CrawledPage crawledPage) {
		Intent result = new Intent();

		result.setAction(CrawlingJob.CRAWLING_ACTION_RESPONSE);
		result.addCategory(Intent.CATEGORY_DEFAULT);
		result.putExtra(RESULT_NAME, crawledPage);

		sendBroadcast(result);

		return result;
	}

	private Intent sendByBroadcast(final int crawledPagesNumber) {
		Intent result = new Intent();

		result.setAction(ACTION);
		result.addCategory(Intent.CATEGORY_DEFAULT);
		result.putExtra(PAGES_NUMBER, crawledPagesNumber);

		sendBroadcast(result);

		return result;
	}

	@Override
	public void onDestroy() {
		stopCrawler = true;
		controller.stop();
		// wait while crawling is end
		while (!crawlerMonitor.isFinished()) {
			try {
				Thread.sleep(100);
			} catch (InterruptedException e) {
				Log.e("EXCEPTION", e.getMessage());
			}
		}
		super.onDestroy();
	}
}