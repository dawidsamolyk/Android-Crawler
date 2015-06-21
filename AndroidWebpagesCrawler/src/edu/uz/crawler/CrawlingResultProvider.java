package edu.uz.crawler;

import java.util.ArrayList;
import java.util.concurrent.ConcurrentLinkedQueue;

import android.annotation.SuppressLint;
import android.app.IntentService;
import android.content.Intent;
import android.util.Log;
import edu.uci.ics.crawler4j.crawler.Page;

@SuppressLint("NewApi")
public class CrawlingResultProvider extends IntentService {
	public static final String RESULT = "LOCAL_TIMES";

	public CrawlingResultProvider() {
		super(CrawlingResultProvider.class.getName());
	}

	@Override
	protected void onHandleIntent(final Intent intent) {
		ConcurrentLinkedQueue<Page> downloadedPages = new ConcurrentLinkedQueue<Page>();

		getCrawlingSettings(intent);

		sendByBroadcast(downloadedPages);
	}

	private void getCrawlingSettings(final Intent intent) {
		String stringExtra = intent.getStringExtra(CrawlingJob.WEBPAGE_URL);
		Log.i("CrawlingJob: webpageUrl", stringExtra);
		
		ArrayList<String> stringArrayExtra = intent.getStringArrayListExtra(CrawlingJob.TOPICS);
		for(String eachTopic : stringArrayExtra) {
			Log.i("CrawlingJob: topic", eachTopic);
		}
		
		boolean searchAlsoInSubpages = intent.getBooleanExtra(CrawlingJob.SEARCH_ALSO_IN_SUBPAGES, true);
		boolean contentSearch = intent.getBooleanExtra(CrawlingJob.CONTENT_SEARCH, false);
		boolean requireAllTopicsOnOnePage = intent.getBooleanExtra(CrawlingJob.REQUIRE_ALL_TOPICS_ON_ONE_PAGE, false);
		Log.i("CrawlingJob: crawlingOptions", "Options: " + searchAlsoInSubpages + contentSearch
				+ requireAllTopicsOnOnePage);
	}

	private void sendByBroadcast(final ConcurrentLinkedQueue<Page> downloadedPages) {
		Intent broadcastIntent = new Intent();

		broadcastIntent.setAction(CrawlingJob.CRAWLING_ACTION_RESPONSE);
		broadcastIntent.addCategory(Intent.CATEGORY_DEFAULT);
		broadcastIntent.putExtra(RESULT, downloadedPages);

		sendBroadcast(broadcastIntent);
	}

}