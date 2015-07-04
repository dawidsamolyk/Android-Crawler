package edu.uz.crawler.config;

import edu.uci.ics.crawler4j.url.WebURL;
import edu.uz.validators.WebpageValidator;

public class CrawlingSettings {
    private WebURL webpageUrl;
    private String[] topics;
    
    public boolean contentSearch = false;
    public boolean requireAllTopicsOnOnePage = false;

    public CrawlingSettings(final WebURL webpageUrl, final String[] topics) {
	setTopics(topics);
	setWebpageUrl(webpageUrl);
    }

    private void setWebpageUrl(final WebURL webpageUrl) throws IllegalArgumentException {
	if (webpageUrl == null || webpageUrl.getURL() == null) {
	    throw new IllegalArgumentException("Nie podano adresu strony!");
	}
	//WebpageValidator.checkUrl(webpageUrl.getURL());

	this.webpageUrl = webpageUrl;
    }

    private void setTopics(final String[] topics) throws IllegalArgumentException {
	if (topics == null || topics.length == 0) {
	    throw new IllegalArgumentException("Nie podano tematów do wyfiltrowania!");
	}
	for (String eachTopic : topics) {
	    if (eachTopic == null || eachTopic.isEmpty()) {
		throw new IllegalArgumentException("Jeden z tematów jest pusty!");
	    }
	}
	this.topics = topics;
    }

    public WebURL getWebpageURL() {
	return webpageUrl;
    }

    public String[] getTopics() {
	return topics;
    }
}
