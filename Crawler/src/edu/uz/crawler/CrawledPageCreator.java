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
		String title = "", foundTopics = "", content = "";

		ParseData dataOnPage = page.getParseData();
		
		if (dataOnPage instanceof HtmlParseData) {
			HtmlParseData data = (HtmlParseData) dataOnPage;
			title = data.getTitle();
			content = data.getHtml();

		} else if (dataOnPage instanceof TextParseData) {
			TextParseData textParseData = (TextParseData) dataOnPage;
			content = textParseData.getTextContent();
		}

		return new CrawledPage(date, url, title, foundTopics, content);
	}
}
