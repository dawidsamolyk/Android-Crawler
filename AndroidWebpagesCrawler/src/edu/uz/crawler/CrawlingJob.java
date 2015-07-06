package edu.uz.crawler;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import edu.uz.crawler.db.CrawledPage;
import edu.uz.crawler.db.DatabaseHelper;

public class CrawlingJob {
	public static final String CRAWLING_ACTION_RESPONSE = "PAGE_DOWNLOADED";
	public static final String CRAWLING_SETTINGS = "CRAWLING_SETTINGS";

	private final BroadcastReceiver crawlingReceiver;
	private final FragmentActivity fragmentActivity;

	public CrawlingJob(final FragmentActivity fragmentActivity, final DatabaseHelper database) {
		if (fragmentActivity != null) {
			this.fragmentActivity = fragmentActivity;
		} else {
			throw new IllegalArgumentException("Fragment activity is not set!");
		}
		if (database == null) {
			throw new IllegalArgumentException("Destination database is not specified!");
		}

		crawlingReceiver = createCrawlingReceiverWhichUsing(database);
		registerCrawlingResultReceiverIn(fragmentActivity);
	}

	private BroadcastReceiver createCrawlingReceiverWhichUsing(final DatabaseHelper database) {
		return new BroadcastReceiver() {
			@Override
			public void onReceive(final Context context, final Intent intent) {
				CrawledPage page = (CrawledPage) intent.getSerializableExtra(CrawlingResultProvider.RESULT_NAME);
				database.insert(page);
			}
		};
	}

	private Intent registerCrawlingResultReceiverIn(final FragmentActivity fragmentActivity) {
		IntentFilter intentFilter = new IntentFilter(CRAWLING_ACTION_RESPONSE);
		intentFilter.addCategory(Intent.CATEGORY_DEFAULT);
		return fragmentActivity.registerReceiver(crawlingReceiver, intentFilter);
	}

	public synchronized void start(final CrawlingSettings settings) throws IllegalArgumentException {
		if (settings == null) {
			throw new IllegalArgumentException("Webpage address is not specified!");
		}

		Intent crawlingIntent = new Intent(fragmentActivity, CrawlingResultProvider.class);
		crawlingIntent.putExtra(CRAWLING_SETTINGS, settings);

		fragmentActivity.startService(crawlingIntent);
		
		Log.i("CrawlingJob", "After starting service...");
	}

}
