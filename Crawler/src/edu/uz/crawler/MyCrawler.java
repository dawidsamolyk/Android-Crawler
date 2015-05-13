package edu.uz.crawler;

import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.regex.Pattern;

import edu.uci.ics.crawler4j.crawler.Page;
import edu.uci.ics.crawler4j.crawler.WebCrawler;
import edu.uci.ics.crawler4j.parser.HtmlParseData;
import edu.uci.ics.crawler4j.parser.ParseData;
import edu.uci.ics.crawler4j.url.WebURL;

public class MyCrawler extends WebCrawler {
    private final static Pattern FILTERS = Pattern.compile(".*(\\.(css|js|gif|jpg"
	    + "|png|mp3|mp3|zip|gz))$");
    private static String[] topics;
    public static String webpageUrl;
    public static final CrawlingSettings SETTINGS = new CrawlingSettings();
    public static ConcurrentLinkedQueue<Page> pagesToSave = new ConcurrentLinkedQueue<Page>();

    public static void setTopics(String[] topics) {
	if (topics == null || topics.length == 0) {
	    throw new IllegalArgumentException("Nie podano tematów do wyfiltrowania!");
	}
	for (String eachTopic : topics) {
	    if (eachTopic == null || eachTopic.isEmpty()) {
		throw new IllegalArgumentException("Jeden z tematów jest pusty!");
	    }
	}
	MyCrawler.topics = topics;
    }

    /**
     * This method receives two parameters. The first parameter is the page in
     * which we have discovered this new url and the second parameter is the new
     * url. You should implement this function to specify whether the given url
     * should be crawled or not.
     * 
     * @see edu.uci.ics.crawler4j.crawler.WebCrawler#shouldVisit(edu.uci.ics.crawler4j.crawler.Page,
     *      edu.uci.ics.crawler4j.url.WebURL)
     */
    @Override
    public boolean shouldVisit(Page referringPage, WebURL url) throws IllegalArgumentException {
	if (webpageUrl == null || webpageUrl.isEmpty()) {
	    throw new IllegalArgumentException(
		    "Nie podano adresu serwisu www, z którego maj¹ byæ œci¹gane strony z podanymi tematami!");
	}

	String href = url.getURL().toLowerCase();

	return !FILTERS.matcher(href).matches() && href.contains(webpageUrl);
    }

    private boolean containsAllTopics(String text) {
	String textToAnalyze = text.toLowerCase();

	for (String eachTopic : topics) {
	    String topicToCheck = eachTopic.toLowerCase();

	    if (!textToAnalyze.contains(topicToCheck)) {
		return false;
	    }
	}
	return true;
    }

    private boolean containsAnyTopic(String text) {
	String textToAnalyze = text.toLowerCase();

	for (String eachTopic : topics) {
	    String topicToCheck = eachTopic.toLowerCase();

	    if (textToAnalyze.contains(topicToCheck)) {
		return true;
	    }
	}
	return false;
    }

    /**
     * @see edu.uci.ics.crawler4j.crawler.WebCrawler#visit(edu.uci.ics.crawler4j.crawler.Page)
     */
    @Override
    public void visit(Page page) {
	boolean downloadThisPage = false;

	ParseData parseData = page.getParseData();

	if (parseData instanceof HtmlParseData) {
	    HtmlParseData htmlParseData = (HtmlParseData) parseData;

	    String contentToAnalyze = htmlParseData.getTitle();

	    if (SETTINGS.contentSearch) {
		contentToAnalyze += htmlParseData.getText();
	    }

	    if (SETTINGS.requireAllTopicsOnOnePage) {
		downloadThisPage = containsAllTopics(contentToAnalyze);
	    }
	    else {
		downloadThisPage = containsAnyTopic(contentToAnalyze);
	    }
	}

	if (downloadThisPage) {
	    pagesToSave.add(page);
	}
    }
}
