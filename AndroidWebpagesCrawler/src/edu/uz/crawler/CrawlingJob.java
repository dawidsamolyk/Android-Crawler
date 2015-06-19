package edu.uz.crawler;

import java.util.Arrays;
import java.util.Map;

import android.util.Log;
import edu.uz.crawler.view.main.fragments.settings.CrawlingOption;

public class CrawlingJob {
	private final String webpageUrl;
	private final String[] topics;
	private final Map<CrawlingOption, Boolean> crawlingOptions;

	public CrawlingJob(final String webpageUrl, final String[] topics,
			final Map<CrawlingOption, Boolean> crawlingOptions) {
		if (webpageUrl != null && webpageUrl.length() > 0) {
			this.webpageUrl = webpageUrl;
		} else {
			throw new IllegalArgumentException("Webpage address is not specified!");
		}

		if (topics != null && topics.length > 0) {
			this.topics = topics;
		} else {
			throw new IllegalArgumentException("You must add at least one topic!");
		}

		if (crawlingOptions != null) {
			this.crawlingOptions = crawlingOptions;
		} else {
			throw new IllegalArgumentException("Crawling options are not set!");
		}
	}

	public void start() {
		Log.i("CrawlingJob: webpageUrl", webpageUrl);
		Log.i("CrawlingJob: topics", Arrays.toString(topics));
		Log.i("CrawlingJob: crawlingOptions", crawlingOptions.toString());
	}

}
