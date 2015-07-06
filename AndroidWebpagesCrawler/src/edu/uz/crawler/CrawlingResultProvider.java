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
	private static final String TAG = CrawlingResultProvider.class.getName();
	private static final int CRAWLED_PAGES_LIMIT = 50;
	public static final String RESULT_NAME = "CRAWLING_RESULT";

	public CrawlingResultProvider() {
		super(CrawlingResultProvider.class.getName());
	}

	@Override
	protected void onHandleIntent(final Intent intent) {
		CrawlingSettings settings = getCrawlingSettings(intent);
		Log.i("CrawlingResultProvider", "onHandleIntent - begin");
		try {
			startCrawler(settings);
		} catch (Exception e) {
			Log.e(TAG, e.getMessage());
		}
		Log.i("CrawlingResultProvider", "onHandleIntent - end");
	}

	private CrawlingSettings getCrawlingSettings(final Intent intent) {
		return (CrawlingSettings) intent.getSerializableExtra(CrawlingJob.CRAWLING_SETTINGS);
	}

	private synchronized void startCrawler(final CrawlingSettings settings) throws Exception {
		final CrawlingController controller = new CrawlingController(getConfiguration(), getSettings(settings));
		final CrawlingMonitor crawlerMonitor = controller.start();

		runSender(crawlerMonitor);

		controller.stop();
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

	private void runSender(final CrawlingMonitor monitor) {
		int crawledPages = 0;
		
		while (!monitor.isFinished()) {
			ConcurrentLinkedQueue<edu.uz.crawler.CrawledPage> pagesToSave = Crawler.PAGES_TO_SAVE;

			while (!pagesToSave.isEmpty()) {
				edu.uz.crawler.CrawledPage page = pagesToSave.poll();
				sendByBroadcast(page);

				Log.i(TAG, "Downloading page titled " + page.getTitle() + " from: " + page.getUrl());
				
				crawledPages++;
			}
			
			if(crawledPages >= CRAWLED_PAGES_LIMIT) {
				break;
			}
		}
	}

	private Intent sendByBroadcast(final CrawledPage crawledPage) {
		Intent result = new Intent();

		result.setAction(CrawlingJob.CRAWLING_ACTION_RESPONSE);
		result.addCategory(Intent.CATEGORY_DEFAULT);
		result.putExtra(RESULT_NAME, crawledPage);

		sendBroadcast(result);

		return result;
	}
}