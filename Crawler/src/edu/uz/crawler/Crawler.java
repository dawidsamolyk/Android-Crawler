package edu.uz.crawler;

import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.regex.Pattern;

import edu.uci.ics.crawler4j.crawler.Page;
import edu.uci.ics.crawler4j.crawler.WebCrawler;
import edu.uci.ics.crawler4j.parser.HtmlParseData;
import edu.uci.ics.crawler4j.parser.ParseData;
import edu.uci.ics.crawler4j.parser.TextParseData;
import edu.uci.ics.crawler4j.url.WebURL;
import edu.uz.crawler.config.CrawlingSettings;
import edu.uz.crawler.utils.ContentFilter;

public class Crawler extends WebCrawler {
    private final static Pattern FILTERS = Pattern
	    .compile(".*(\\.(css|js|gif|jpg|png|mp3|mp3|zip|gz))$");
    private ContentFilter filter = new ContentFilter();
    public static CrawlingSettings SETTINGS;
    public static final ConcurrentLinkedQueue<Page> PAGES_TO_SAVE = new ConcurrentLinkedQueue<Page>();

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
    public final boolean shouldVisit(final Page referringPage, final WebURL url)
	    throws IllegalArgumentException, IllegalStateException {
	if (SETTINGS == null) {
	    throw new IllegalStateException("Brak ustawieñ Crawlera!");
	}

	String webpageUrl = SETTINGS.getWebpageUrl();

	if (webpageUrl == null || webpageUrl.isEmpty()) {
	    throw new IllegalArgumentException(
		    "Nie podano adresu serwisu www, z którego maj¹ byæ œci¹gane strony z podanymi tematami!");
	}

	String href = url.getURL().toLowerCase();
	webpageUrl = webpageUrl.toLowerCase();

	return !FILTERS.matcher(href).matches() && href.contains(webpageUrl);
    }

    /**
     * @see edu.uci.ics.crawler4j.crawler.WebCrawler#visit(edu.uci.ics.crawler4j.crawler.Page)
     */
    @Override
    public void visit(final Page page) throws IllegalStateException {
	if (SETTINGS == null) {
	    throw new IllegalStateException("Brak ustawieñ Crawlera!");
	}

	ParseData parseData = page.getParseData();

	if (parseData instanceof HtmlParseData) {
	    HtmlParseData htmlParseData = (HtmlParseData) parseData;

	    if (filter.containsTopicsWithSettings(htmlParseData)) {
		PAGES_TO_SAVE.add(page);
	    }
	} else if (parseData instanceof TextParseData) {
	    TextParseData textParseData = (TextParseData) parseData;

	    if (filter.containsTopicsWithSettings(textParseData)) {
		PAGES_TO_SAVE.add(page);
	    }
	}
    }
}
