package edu.uz.crawler;

import android.annotation.SuppressLint;
import android.app.IntentService;
import android.content.Intent;

@SuppressLint("NewApi")
public class CrawlingResultProvider extends IntentService {
	public static final String RESULT_NAME = "CRAWLING_RESULT";

	public CrawlingResultProvider() {
		super(CrawlingResultProvider.class.getName());
	}

	@Override
	protected void onHandleIntent(final Intent intent) {
		final CrawlingSettings settings = getCrawlingSettings(intent);
		startCrawler(settings);
	}

	private CrawlingSettings getCrawlingSettings(final Intent intent) {
		return (CrawlingSettings) intent.getSerializableExtra(CrawlingJob.CRAWLING_SETTINGS);
	}

	// TODO zrob ¿eby nie trzeba by³o synchronized - kazde zadanie crawlera w nowym watku
	private synchronized void startCrawler(final CrawlingSettings settings) {
		// TODO uruchom crawler
		
		CrawledPage crawledPage = new CrawledPage("21-06-2015", "wp.pl", "Tytul", "temat 1; temat 2", "Zawartosc..");
		
		sendByBroadcast(crawledPage);
	}

	private Intent sendByBroadcast(final CrawledPage downloadedPage) {
		Intent result = new Intent();

		result.setAction(CrawlingJob.CRAWLING_ACTION_RESPONSE);
		result.addCategory(Intent.CATEGORY_DEFAULT);
		result.putExtra(RESULT_NAME, downloadedPage);

		sendBroadcast(result);
		
		return result;
	}

}