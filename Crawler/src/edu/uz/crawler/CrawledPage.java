package edu.uz.crawler;

import java.io.Serializable;
import java.util.Date;

import edu.uci.ics.crawler4j.crawler.Page;
import edu.uci.ics.crawler4j.parser.HtmlParseData;
import edu.uci.ics.crawler4j.parser.ParseData;
import edu.uci.ics.crawler4j.parser.TextParseData;

public class CrawledPage implements Serializable {
	private static final long serialVersionUID = 2482955988269566735L;
	
	private String date;
	private String url;
	private String title;
	private String foundTopics;
	private String content;

	public CrawledPage(String date, String url, String title, String foundTopics, String content) {
		this.date = date;
		this.url = url;
		this.title = title;
		this.foundTopics = foundTopics;
		this.content = content;
	}

	public String getDate() {
		return date;
	}

	public String getUrl() {
		return url;
	}

	public String getTitle() {
		return title;
	}

	public String getFoundTopics() {
		return foundTopics;
	}

	public String getContent() {
		return content;
	}
}
