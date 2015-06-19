package edu.uz.crawler.db;

public class CrawledPage {
	private String date;
	private String url;
	private String title;
	private String foundTopics;
	private String content;

	public CrawledPage(String date, String url, String title, String foundTopics, String content) {
		super();
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
