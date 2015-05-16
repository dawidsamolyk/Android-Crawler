package edu.uz.crawler.controller;

import static org.junit.Assert.*;

import java.io.IOException;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.mockito.Mockito;

import edu.uci.ics.crawler4j.crawler.CrawlController;
import edu.uz.crawler.config.CrawlingConfiguration;
import edu.uz.crawler.config.CrawlingConfigurationTest;
import edu.uz.crawler.config.CrawlingSettings;
import edu.uz.crawler.config.CrawlingSettingsTest;

public class CrawlingControllerTest {
    @Rule
    public ExpectedException exception = ExpectedException.none();

    @Test
    public void shouldNotCreatesWithoutConfiguration() throws Exception {
	exception.expect(IllegalArgumentException.class);
	new CrawlingController(null, getFixtureSettings());
    }

    @Test
    public void shouldNotCreatesWithoutSettings() throws Exception {
	exception.expect(IllegalArgumentException.class);
	new CrawlingController(getFixtureConfiguration(), null);
    }

    @Test
    public void shouldNotStopsWhenCrawlerWasNotStarted() throws Exception {
	CrawlingController fixture = new CrawlingController(getFixtureConfiguration(),
		getFixtureSettings());

	exception.expect(IllegalStateException.class);
	fixture.stop();
    }

    @Test
    public void shouldNotStopsWhenCrawlerFinished() throws Exception {
	TestingCrawlingController fixture = getTestingCrawlingController();
	fixture.mock = Mockito.mock(CrawlController.class);
	Mockito.when(fixture.mock.isFinished()).thenReturn(true);

	fixture.start();

	exception.expect(IllegalStateException.class);
	fixture.stop();
    }
    
    @Test
    public void shouldGivesCrawlingMonitor() throws Exception {
	TestingCrawlingController fixture = getTestingCrawlingController();
	fixture.mock = Mockito.mock(CrawlController.class);
	Mockito.when(fixture.mock.isFinished()).thenReturn(true);

	CrawlingMonitor monitor = fixture.start();

	assertTrue(monitor.isFinished());
    }

    public TestingCrawlingController getTestingCrawlingController() throws Exception, IOException {
	return new TestingCrawlingController(getFixtureConfiguration(), getFixtureSettings());
    }

    private CrawlingConfiguration getFixtureConfiguration() throws IllegalArgumentException,
	    IOException {
	return new CrawlingConfiguration(CrawlingConfigurationTest.getFixtureDirectory());
    }

    private CrawlingSettings getFixtureSettings() {
	return CrawlingSettingsTest.getFixture("http://wp.pl", new String[] { "Topic" });
    }
}
