package edu.uz.crawler;

import static org.junit.Assert.*;

import org.junit.Test;

import edu.uci.ics.crawler4j.crawler.Page;
import edu.uci.ics.crawler4j.parser.HtmlParseData;
import edu.uci.ics.crawler4j.url.WebURL;

public class MyCrawlerTest {
    @Test
    public void shouldDownloadPageIfContainsAnyTopicInTitle() {
	MyCrawler.setTopics(new String[]{"Dog"});
	MyCrawler.contentSearch = false;
	MyCrawler.requireAllTopicsOnOnePage = false;

	MyCrawler fixture = new MyCrawler();
	String content = "I love my dog!";
	String title = "My dog is my best friend...";
	Page pageFixture = getPageFixtureWithTitleAndContent(title, content);
	
	fixture.visit(pageFixture);
	
	assertTrue(MyCrawler.pagesToSave.contains(pageFixture));
    }
    
    public static Page getPageFixtureWithTitleAndContent(String title, String content) {
	WebURL url = new WebURL();
	url.setURL("http://fakeurl.com");
	Page pageFixture = new Page(url);
	HtmlParseData parseData = new HtmlParseData();
	parseData.setTitle(title);
	parseData.setText(content);
	pageFixture.setParseData(parseData);
	
	return pageFixture;
    }
}
