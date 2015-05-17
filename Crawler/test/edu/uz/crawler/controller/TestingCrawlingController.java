package edu.uz.crawler.controller;

import edu.uci.ics.crawler4j.crawler.CrawlController;
import edu.uz.crawler.config.CrawlingConfiguration;
import edu.uz.crawler.config.CrawlingSettings;

public class TestingCrawlingController extends CrawlingController {
    public static CrawlController mock;

    public TestingCrawlingController(CrawlingConfiguration config, CrawlingSettings settings)
	    throws Exception {
	super(config, settings);
    }

    @Override
    protected CrawlController getNewCrawlController(CrawlingConfiguration config) throws Exception {
	return mock;
    }

    @Override
    protected void configureCrawling(CrawlingSettings settings) {
    }
}
