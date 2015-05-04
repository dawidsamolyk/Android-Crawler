package edu.uz.crawler;

import java.io.IOException;
import java.nio.file.Path;

import edu.uci.ics.crawler4j.crawler.CrawlConfig;
import edu.uz.validators.DiskObjectValidator;

public class CrawlingConfiguration extends CrawlConfig {
    private static final String CRAWLER_NAME = "Android Crawler App (https://github.com/dawidsamolyk/Android-Crawler/)";

    public CrawlingConfiguration(final Path storageFolderPath) throws IllegalArgumentException,
	    IOException {
	DiskObjectValidator.checkDirectory(storageFolderPath);
	this.setCrawlStorageFolder(storageFolderPath.toString());
	initialize();
    }

    private void initialize() {
	this.setConnectionTimeout(500);
	this.setSocketTimeout(500);
	this.setIncludeHttpsPages(true);
	this.setFollowRedirects(true);
	this.setPolitenessDelay(100);
	this.setMaxDepthOfCrawling(1);
	this.setMaxTotalConnections(100);
	this.setResumableCrawling(false);
	this.setUserAgentString(CRAWLER_NAME);
    }

    public void setMaxDepthOfCrawling(int levels) {
	this.setMaxDepthOfCrawling(levels);
    }
}
