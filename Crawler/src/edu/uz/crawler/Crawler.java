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
	private final static Pattern FILTERS = Pattern.compile(".*(\\.(css|js|gif|jpg|png|mp3|mp3|zip|gz))$");
	private static final ContentFilter contentFilter = new ContentFilter();
	public static final ConcurrentLinkedQueue<CrawledPage> PAGES_TO_SAVE = new ConcurrentLinkedQueue<CrawledPage>();
	public static CrawlingSettings SETTINGS;

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
	public boolean shouldVisit(final WebURL url) throws IllegalArgumentException, IllegalStateException {
		if (SETTINGS == null) {
			throw new IllegalStateException("Brak ustawieñ Crawlera!");
		}

		String crawledWebpageDomain = SETTINGS.getWebpageURL().getDomain().toLowerCase();
		String actualWebpageUrl = url.getURL().toLowerCase();

		boolean isPageWithContent = !FILTERS.matcher(actualWebpageUrl).matches();
		boolean isPageInSameDomainAsCrawlingWebpage = actualWebpageUrl.contains(crawledWebpageDomain);

		return isPageWithContent && isPageInSameDomainAsCrawlingWebpage;
	}

	/**
	 * @see edu.uci.ics.crawler4j.crawler.WebCrawler#visit(edu.uci.ics.crawler4j.crawler.Page)
	 */
	@Override
	public void visit(final Page page) throws IllegalStateException {
		if (SETTINGS == null) {
			throw new IllegalStateException("Brak ustawieñ Crawlera!");
		}

		ParseData dataOnPage = page.getParseData();

		if (dataOnPage instanceof HtmlParseData) {
			HtmlParseData htmlParseData = (HtmlParseData) dataOnPage;

			if (contentFilter.containsTopicsWithCrawlerSettings(htmlParseData)) {
				PAGES_TO_SAVE.add(CrawledPageCreator.create(page));
			}
		} else if (dataOnPage instanceof TextParseData) {
			TextParseData textParseData = (TextParseData) dataOnPage;

			if (contentFilter.containsTopicsWithCrawlerSettings(textParseData)) {
				PAGES_TO_SAVE.add(CrawledPageCreator.create(page));
			}
		}
	}
}
