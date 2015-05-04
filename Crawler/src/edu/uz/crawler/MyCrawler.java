package edu.uz.crawler;

import java.util.Set;
import java.util.regex.Pattern;

import edu.uci.ics.crawler4j.crawler.Page;
import edu.uci.ics.crawler4j.crawler.WebCrawler;
import edu.uci.ics.crawler4j.parser.HtmlParseData;
import edu.uci.ics.crawler4j.url.WebURL;

public class MyCrawler extends WebCrawler {
    private final static Pattern FILTERS = Pattern.compile(".*(\\.(css|js|gif|jpg" + "|png|mp3|mp3|zip|gz))$");

    /**
     * This method receives two parameters. The first parameter is the page in
     * which we have discovered this new url and the second parameter is the new
     * url. You should implement this function to specify whether the given url
     * should be crawled or not.
     * 
     * (non-Javadoc)
     * 
     * @see edu.uci.ics.crawler4j.crawler.WebCrawler#shouldVisit(edu.uci.ics.crawler4j.crawler.Page,
     *      edu.uci.ics.crawler4j.url.WebURL)
     */
    @Override
    public boolean shouldVisit(Page referringPage, WebURL url) {
	String href = url.getURL().toLowerCase();
	return !FILTERS.matcher(href).matches() && href.contains("intel");
    }

    /**
     * (non-Javadoc)
     * 
     * @see edu.uci.ics.crawler4j.crawler.WebCrawler#visit(edu.uci.ics.crawler4j.crawler.Page)
     */
    @Override
    public void visit(Page page) {
	String url = page.getWebURL().getURL();
	System.out.println("URL: " + url);

	if (page.getParseData() instanceof HtmlParseData) {
	    HtmlParseData htmlParseData = (HtmlParseData) page.getParseData();
	    String text = htmlParseData.getText();
	    String html = htmlParseData.getHtml();
	    Set<WebURL> links = htmlParseData.getOutgoingUrls();

	    System.out.println("Text length: " + text.length());
	    System.out.println("Html length: " + html.length());
	    System.out.println("Number of outgoing links: " + links.size());
	}
    }
}
