package edu.uz.crawler.controller;

import edu.uci.ics.crawler4j.crawler.CrawlController;

public class CrawlingMonitor {
	private final CrawlController controller;

	public CrawlingMonitor(final CrawlController controller) throws IllegalArgumentException {
		if (controller == null) {
			throw new IllegalArgumentException("Not specified crawling configuration for monitoring!");
		}
		this.controller = controller;
	}

	public boolean isFinished() {
		return controller.isFinished();
	}
}
