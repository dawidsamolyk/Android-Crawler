package edu.uz.crawler;

import edu.uci.ics.crawler4j.crawler.CrawlConfig;
import edu.uci.ics.crawler4j.crawler.CrawlController;
import edu.uci.ics.crawler4j.fetcher.PageFetcher;
import edu.uci.ics.crawler4j.robotstxt.RobotstxtConfig;
import edu.uci.ics.crawler4j.robotstxt.RobotstxtServer;

public class CrawlingController {

    public static void main(String[] args) throws Exception {
	CrawlConfig config = new CrawlConfig();
	// TODO - parametryzuj
	config.setCrawlStorageFolder("data/crawl");
	// config.setConnectionTimeout(500);
	// config.setSocketTimeout(500);
	config.setIncludeHttpsPages(true);
	config.setFollowRedirects(true);
	config.setPolitenessDelay(100);
	// TODO - ustawienia Proxy
	config.setMaxDepthOfCrawling(100);
	// config.setMaxOutgoingLinksToFollow(100);
	// config.setMaxTotalConnections(100);
	config.setResumableCrawling(false);
	config.setUserAgentString("Android Crawler App");

	PageFetcher pageFetcher = new PageFetcher(config);
	RobotstxtConfig robotstxtConfig = new RobotstxtConfig();
	robotstxtConfig.setUserAgentName("Android Crawler App");
	RobotstxtServer robotstxtServer = new RobotstxtServer(robotstxtConfig, pageFetcher);

	CrawlController controller = new CrawlController(config, pageFetcher, robotstxtServer);

	controller.addSeed("http://www.intel.pl/");

	// TODO - parametryzuj
	int numberOfCrawlers = 8;
	controller.startNonBlocking(MyCrawler.class, numberOfCrawlers);

	controller.isFinished();
	controller.isShuttingDown();
    }
}
