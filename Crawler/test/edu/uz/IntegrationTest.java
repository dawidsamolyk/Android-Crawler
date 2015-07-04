package edu.uz;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.concurrent.ConcurrentLinkedQueue;

import org.junit.Test;

import edu.uci.ics.crawler4j.url.WebURL;
import edu.uz.crawler.CrawledPage;
import edu.uz.crawler.Crawler;
import edu.uz.crawler.config.CrawlingConfiguration;
import edu.uz.crawler.controller.CrawlingController;
import edu.uz.crawler.controller.CrawlingMonitor;

public class IntegrationTest {
	public static void main(String[] args) throws Exception {
		WebURL url = new WebURL();
		url.setURL("http://overclock.pl/articles/show/id/714,evga-gtx-980-ti-acx-20-test-topowej-karty-graficznej");
		ArrayList<String> topicsList = new ArrayList<String>();
		topicsList.add("980");
		String[] topics = topicsList.toArray(new String[topicsList.size()]);
		edu.uz.crawler.config.CrawlingSettings setttings = new edu.uz.crawler.config.CrawlingSettings(url, topics);
		CrawlingConfiguration config = new CrawlingConfiguration(createTempDirectory());
		config.searchOnlyInSelectedPage();
		CrawlingController controller = new CrawlingController(config, setttings);

		final CrawlingMonitor monitor = controller.start();

		new Runnable() {
			public void run() {
				while (!monitor.isFinished()) {
					ConcurrentLinkedQueue<CrawledPage> pagesToSave = Crawler.PAGES_TO_SAVE;

					while (!pagesToSave.isEmpty()) {
						System.out.println(pagesToSave.poll().getTitle());
					}
				}
			}

		}.run();
	}

	public static File createTempDirectory() throws IOException {
		final File temp;

		temp = File.createTempFile("temp", Long.toString(System.nanoTime()));

		if (!(temp.delete())) {
			throw new IOException("Could not delete temp file: " + temp.getAbsolutePath());
		}

		if (!(temp.mkdir())) {
			throw new IOException("Could not create temp directory: " + temp.getAbsolutePath());
		}
		
		temp.deleteOnExit();

		return (temp);
	}
}
