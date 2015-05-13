package edu.uz.crawler;

import edu.uci.ics.crawler4j.crawler.CrawlController;
import edu.uci.ics.crawler4j.fetcher.PageFetcher;
import edu.uci.ics.crawler4j.robotstxt.RobotstxtConfig;
import edu.uci.ics.crawler4j.robotstxt.RobotstxtServer;
import edu.uz.validators.WebpageValidator;

public class CrawlingController {
    private final CrawlingConfiguration config;
    private final CrawlingSettings settings;
    private CrawlController controller;

    public CrawlingController(final CrawlingConfiguration config, final CrawlingSettings settings)
	    throws IllegalArgumentException {
	if (config == null) {
	    throw new IllegalArgumentException("Not specified crawling configuration!");
	}
	if (settings == null) {
	    throw new IllegalArgumentException("Not specified crawling settings!");
	}
	this.config = config;
	this.settings = settings;
    }

    public CrawlingMonitor start(final String pageUrl) throws IllegalArgumentException,
	    IllegalStateException, Exception {
	if (controller != null && !controller.isFinished()) {
	    throw new IllegalStateException("Cannot start while another crawling is running!");
	}
	WebpageValidator.checkUrl(pageUrl);

	PageFetcher pageFetcher = new PageFetcher(config);
	RobotstxtConfig robotstxtConfig = new RobotstxtConfig();
	RobotstxtServer robotstxtServer = new RobotstxtServer(robotstxtConfig, pageFetcher);

	controller = new CrawlController(config, pageFetcher, robotstxtServer);

	controller.addSeed(pageUrl);

	int numberOfCrawlers = Runtime.getRuntime().availableProcessors();
	controller.startNonBlocking(MyCrawler.class, numberOfCrawlers);

	return new CrawlingMonitor(controller);
    }

    public CrawlingMonitor stop() {
	if (controller == null) {
	    throw new IllegalStateException("Crawler not started!");
	}
	if (!controller.isFinished()) {
	    controller.shutdown();
	}
	return new CrawlingMonitor(controller);
    }
}
