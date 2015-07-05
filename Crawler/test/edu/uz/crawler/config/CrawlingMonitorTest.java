package edu.uz.crawler.config;

import static org.junit.Assert.assertTrue;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.mockito.Mockito;

import edu.uci.ics.crawler4j.crawler.CrawlController;
import edu.uz.crawler.controller.CrawlingMonitor;

public class CrawlingMonitorTest {
	@Rule
	public ExpectedException exception = ExpectedException.none();

	@Test
	public void shouldNotCreatesWithoutCrawlController() {
		exception.expect(IllegalArgumentException.class);
		new CrawlingMonitor(null);
	}

	@Test
	public void shouldIndicateEndOfCrawling() {
		CrawlController controller = Mockito.mock(CrawlController.class);
		Mockito.when(controller.isFinished()).thenReturn(true);
		CrawlingMonitor fixture = new CrawlingMonitor(controller);

		boolean isFinished = fixture.isFinished();

		assertTrue(isFinished);
	}
}
