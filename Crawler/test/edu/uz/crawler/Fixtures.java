package edu.uz.crawler;

import edu.uci.ics.crawler4j.crawler.Page;
import edu.uci.ics.crawler4j.parser.HtmlParseData;
import edu.uci.ics.crawler4j.parser.ParseData;
import edu.uci.ics.crawler4j.parser.TextParseData;
import edu.uci.ics.crawler4j.url.WebURL;

public class Fixtures {

	public final static Page getHtmlPageFixtureWith(String title, String content) {
		HtmlParseData parseData = new HtmlParseData();
		parseData.setTitle(title);
		parseData.setText(content);
		parseData.setHtml(content);

		Page pageFixture = new Page(Fixtures.getFixtureWebpageUrl());
		pageFixture.setParseData(parseData);

		return pageFixture;
	}

	public final static Page getTextPageFixtureWith(String content) {
		TextParseData parseData = new TextParseData();
		parseData.setTextContent(content);

		return Fixtures.getFixturePage(parseData);
	}

	public static Page getFixturePage(ParseData parseData) {
		Page pageFixture = new Page(Fixtures.getFixtureWebpageUrl());
		pageFixture.setParseData(parseData);

		return pageFixture;
	}

	public static final String[] getFixtureTopics(final String... topics) {
		return topics;
	}

	public static final WebURL getFixtureWebpageUrl() {
		WebURL url = new WebURL();
		url.setURL("http://fakeurl.com/");
		return url;
	}

}
