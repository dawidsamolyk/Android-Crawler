package edu.uz.crawler;

import java.util.ArrayList;
import java.util.Map;
import java.util.concurrent.ConcurrentLinkedQueue;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.v4.app.FragmentActivity;
import edu.uci.ics.crawler4j.crawler.Page;
import edu.uz.crawler.view.main.fragments.settings.CrawlingOption;

public class CrawlingJob {
	public static final String REQUIRE_ALL_TOPICS_ON_ONE_PAGE = "requireAllTopicsOnOnePage";
	public static final String CONTENT_SEARCH = "contentSearch";
	public static final String SEARCH_ALSO_IN_SUBPAGES = "searchAlsoInSubpages";
	public static final String TOPICS = "topics";
	public static final String WEBPAGE_URL = "webpageUrl";
	public static final String CRAWLING_ACTION_RESPONSE = "CRAWLING_STARTED";

	private final BroadcastReceiver crawlingReceiver;
	private final FragmentActivity fragmentActivity;
	private final CrawlingSettings settings;

	public CrawlingJob(final FragmentActivity fragmentActivity, final CrawlingSettings settings) {
		if (settings != null) {
			this.settings = settings;
		} else {
			throw new IllegalArgumentException("Webpage address is not specified!");
		}
		if (fragmentActivity != null) {
			this.fragmentActivity = fragmentActivity;
		} else {
			throw new IllegalArgumentException("Fragment activity is not set!");
		}

		crawlingReceiver = new BroadcastReceiver() {
			@Override
			public void onReceive(Context context, Intent intent) {
				ConcurrentLinkedQueue<Page> downloadedPages = (ConcurrentLinkedQueue<Page>) intent.getExtras().get(
						CrawlingResultProvider.RESULT);

			}
		};
		startLocalTimeReceiver(fragmentActivity);
	}

	private void startLocalTimeReceiver(final FragmentActivity fragmentActivity) {
		IntentFilter intentFilter = new IntentFilter(CRAWLING_ACTION_RESPONSE);
		intentFilter.addCategory(Intent.CATEGORY_DEFAULT);
		fragmentActivity.registerReceiver(crawlingReceiver, intentFilter);
	}

	public synchronized void start() {
		Intent crawlingIntent = new Intent(fragmentActivity, CrawlingResultProvider.class);
		putCrawlingSettings(crawlingIntent);

		fragmentActivity.startService(crawlingIntent);
	}

	private void putCrawlingSettings(final Intent crawlingIntent) {
		crawlingIntent.putExtra(WEBPAGE_URL, settings.getWebpageUrl());

		crawlingIntent.putStringArrayListExtra(TOPICS, settings.getTopics());

		Map<CrawlingOption, Boolean> crawlingOptions = settings.getCrawlingOptions();
		crawlingIntent.putExtra(SEARCH_ALSO_IN_SUBPAGES, crawlingOptions.get(CrawlingOption.searchAlsoInSubpages));
		crawlingIntent.putExtra(CONTENT_SEARCH, crawlingOptions.get(CrawlingOption.contentSearch));
		crawlingIntent.putExtra(REQUIRE_ALL_TOPICS_ON_ONE_PAGE,
				crawlingOptions.get(CrawlingOption.requireAllTopicsOnOnePage));
	}

}
