package edu.uz.crawler.controller;

import edu.uci.ics.crawler4j.crawler.CrawlController;
import edu.uci.ics.crawler4j.fetcher.PageFetcher;
import edu.uci.ics.crawler4j.robotstxt.RobotstxtConfig;
import edu.uci.ics.crawler4j.robotstxt.RobotstxtServer;
import edu.uz.crawler.Crawler;
import edu.uz.crawler.config.CrawlingConfiguration;
import edu.uz.crawler.config.CrawlingSettings;

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

    public CrawlingMonitor start() throws IllegalArgumentException,
	    IllegalStateException, Exception {
	if (controller != null && !controller.isFinished()) {
	    throw new IllegalStateException("Cannot start while another crawling is running!");
	}

	PageFetcher pageFetcher = new PageFetcher(config);
	RobotstxtConfig robotstxtConfig = new RobotstxtConfig();
	RobotstxtServer robotstxtServer = new RobotstxtServer(robotstxtConfig, pageFetcher);

	controller = new CrawlController(config, pageFetcher, robotstxtServer);

	controller.addSeed(settings.getWebpageUrl());

	int numberOfCrawlers = Runtime.getRuntime().availableProcessors();
	Crawler.SETTINGS = settings;
	controller.startNonBlocking(Crawler.class, numberOfCrawlers);

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
