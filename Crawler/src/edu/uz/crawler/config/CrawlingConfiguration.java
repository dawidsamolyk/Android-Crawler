package edu.uz.crawler.config;

import java.io.File;
import java.io.IOException;

import edu.uci.ics.crawler4j.crawler.CrawlConfig;
import edu.uz.validators.DiskObjectValidator;

public class CrawlingConfiguration extends CrawlConfig {
    private static final String CRAWLER_NAME = "Android Crawler (https://github.com/dawidsamolyk/Android-Crawler/)";

    public CrawlingConfiguration(final File storageDirectory) throws IllegalArgumentException,
	    IOException {
	DiskObjectValidator.checkDirectory(storageDirectory);
	this.setCrawlStorageFolder(storageDirectory.getPath());

	initialize();
    }

    private void initialize() {
	this.setConnectionTimeout(500);
	this.setSocketTimeout(500);
	this.setIncludeHttpsPages(true);
	this.setFollowRedirects(true);
	this.setPolitenessDelay(100);
	this.setMaxTotalConnections(100);
	this.setResumableCrawling(false);
	this.setUserAgentString(CRAWLER_NAME);
    }

    public void searchAlsoInSubpages() {
	this.setMaxDepthOfCrawling(10);
    }

    public void searchOnlyInSelectedPage() {
	this.setMaxDepthOfCrawling(1);
    }
}
