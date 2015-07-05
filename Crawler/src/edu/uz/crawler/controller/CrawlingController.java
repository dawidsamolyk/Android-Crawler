package edu.uz.crawler.controller;

import android.util.Log;
import edu.uci.ics.crawler4j.crawler.CrawlController;
import edu.uci.ics.crawler4j.fetcher.PageFetcher;
import edu.uci.ics.crawler4j.robotstxt.RobotstxtConfig;
import edu.uci.ics.crawler4j.robotstxt.RobotstxtServer;
import edu.uz.crawler.Crawler;
import edu.uz.crawler.config.CrawlingConfiguration;
import edu.uz.crawler.config.CrawlingSettings;

public class CrawlingController {

	private static final int CPU_CORES = Runtime.getRuntime().availableProcessors() * 2;
	private final CrawlController controller;
	private boolean started = false;

	public CrawlingController(final CrawlingConfiguration config, final CrawlingSettings settings) throws Exception {
		if (config == null) {
			throw new IllegalArgumentException("Not specified crawling configuration!");
		}
		if (settings == null) {
			throw new IllegalArgumentException("Not specified crawling settings!");
		}
		this.controller = getNewCrawlController(config);
		configureCrawling(settings);
	}

	protected CrawlController getNewCrawlController(final CrawlingConfiguration config) throws Exception {
		PageFetcher pageFetcher = new PageFetcher(config);
		RobotstxtConfig robotstxtConfig = new RobotstxtConfig();
		RobotstxtServer robotstxtServer = new RobotstxtServer(robotstxtConfig, pageFetcher);

		return new CrawlController(config, pageFetcher, robotstxtServer);
	}

	protected void configureCrawling(final CrawlingSettings settings) {
		controller.addSeed(settings.getWebpageURL().getURL());
		Crawler.SETTINGS = settings;
	}

	public CrawlingMonitor start() throws IllegalStateException {
		if (started) {
			throw new IllegalStateException("Cannot start again while crawling is running!");
		}

		controller.startNonBlocking(Crawler.class, CPU_CORES);
		started = true;

		runStopper();

		return new CrawlingMonitor(controller);
	}

	private void runStopper() {
		new Runnable() {
			private static final String TAG = "STOPPER";

			@Override
			public void run() {
				try {
					int oneMinuteInMiliseconds = 1 * 60 * 1000;
					Thread.sleep(oneMinuteInMiliseconds);
				} catch (InterruptedException e) {
					Log.e(TAG, e.getMessage());
				}
				CrawlingController.this.stop();
				Log.e(TAG, "Crawler stopped");
			}
		}.run();
	}

	public CrawlingMonitor stop() throws IllegalStateException {
		if (!started || controller.isFinished()) {
			throw new IllegalStateException("Crawler not started!");
		}
		controller.shutdown();
		started = false;

		return new CrawlingMonitor(controller);
	}

	public boolean isCrawlerStarted() {
		return started;
	}
}
