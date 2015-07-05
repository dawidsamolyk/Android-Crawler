package edu.uz.crawler;

import java.util.Date;

import edu.uci.ics.crawler4j.crawler.Page;
import edu.uci.ics.crawler4j.parser.HtmlParseData;
import edu.uci.ics.crawler4j.parser.ParseData;
import edu.uci.ics.crawler4j.parser.TextParseData;

public class CrawledPageCreator {

	public static CrawledPage create(Page page) {
		String date = new Date().toString();
		String url = page.getWebURL().getDomain();
		String title = "", content = "";

		ParseData dataOnPage = page.getParseData();

		if (dataOnPage instanceof HtmlParseData) {
			HtmlParseData data = (HtmlParseData) dataOnPage;
			title = data.getTitle();
			content = data.getHtml();

		} else if (dataOnPage instanceof TextParseData) {
			TextParseData textParseData = (TextParseData) dataOnPage;
			content = textParseData.getTextContent();
		}

		return new CrawledPage(date, url, title, getFoundTopics(title, content), content);
	}

	private static String getFoundTopics(final String title, final String content) {
		StringBuilder foundTopics = new StringBuilder("Found topics: ");

		for (String eachTopic : Crawler.SETTINGS.getTopics()) {
			if (title.contains(eachTopic) || content.contains(eachTopic)) {
				foundTopics.append(eachTopic + ", ");
			}
		}
		// Delete last comma and space characters
		foundTopics.deleteCharAt(foundTopics.length() - 1);
		foundTopics.deleteCharAt(foundTopics.length() - 1);

		return foundTopics.toString();
	}
}
