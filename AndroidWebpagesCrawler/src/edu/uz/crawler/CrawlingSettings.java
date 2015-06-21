package edu.uz.crawler;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Map;

import edu.uz.crawler.view.main.fragments.settings.CrawlingOption;

public class CrawlingSettings implements Serializable {
	private static final long serialVersionUID = -4267084094923986600L;
	
	private final String webpageUrl;
	private final ArrayList<String> topics;
	private final Map<CrawlingOption, Boolean> crawlingOptions;
	
	public CrawlingSettings(final String webpageUrl, final ArrayList<String> topics,
			final Map<CrawlingOption, Boolean> crawlingOptions) {
		if (webpageUrl != null && webpageUrl.length() > 0) {
			this.webpageUrl = webpageUrl;
		} else {
			throw new IllegalArgumentException("Webpage address is not specified!");
		}

		if (topics != null && !topics.isEmpty()) {
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

	public String getWebpageUrl() {
		return webpageUrl;
	}

	public ArrayList<String> getTopics() {
		return topics;
	}

	public Map<CrawlingOption, Boolean> getCrawlingOptions() {
		return crawlingOptions;
	}
}
