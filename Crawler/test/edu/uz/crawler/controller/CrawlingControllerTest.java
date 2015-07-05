package edu.uz.crawler.controller;

import static org.junit.Assert.assertTrue;

import java.io.IOException;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.mockito.Mockito;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;

import edu.uci.ics.crawler4j.crawler.CrawlController;
import edu.uz.crawler.config.CrawlingConfiguration;
import edu.uz.crawler.config.CrawlingConfigurationTest;
import edu.uz.crawler.config.CrawlingSettings;
import edu.uz.crawler.config.CrawlingSettingsTest;

public class CrawlingControllerTest {
    @Rule
    public ExpectedException exception = ExpectedException.none();

    @Before
    public void setUp() throws Exception {
	TestingCrawlingController.mock = null;
    }

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
	TestingCrawlingController.mock = Mockito.mock(CrawlController.class);
	Mockito.when(TestingCrawlingController.mock.isFinished()).thenReturn(true);
	TestingCrawlingController fixture = getTestingCrawlingController();

	fixture.start();

	exception.expect(IllegalStateException.class);
	fixture.stop();
    }

    @Test
    public void shouldNotStartWhenCrawlerIsWorking() throws Exception {
	TestingCrawlingController.mock = Mockito.mock(CrawlController.class);
	Mockito.when(TestingCrawlingController.mock.isFinished()).thenReturn(true);
	TestingCrawlingController fixture = getTestingCrawlingController();

	fixture.start();
	Mockito.when(TestingCrawlingController.mock.isFinished()).thenReturn(false);

	exception.expect(IllegalStateException.class);
	fixture.start();
    }

    @Test
    public void shouldGivesCrawlingMonitor() throws Exception {
	TestingCrawlingController.mock = Mockito.mock(CrawlController.class);
	Mockito.when(TestingCrawlingController.mock.isFinished()).thenReturn(true);
	TestingCrawlingController fixture = getTestingCrawlingController();

	CrawlingMonitor monitor = fixture.start();

	assertTrue(monitor.isFinished());
    }

    @Test
    public void shouldStopCrawlingWhenItIsInProgress() throws Exception {
	TestingCrawlingController.mock = Mockito.mock(CrawlController.class);
	Mockito.doAnswer(new Answer<Object>() {
	    public Object answer(InvocationOnMock invocation) throws Throwable {
		Mockito.when(TestingCrawlingController.mock.isFinished()).thenReturn(true);
		return null;
	    }
	}).when(TestingCrawlingController.mock).shutdown();
	Mockito.when(TestingCrawlingController.mock.isFinished()).thenReturn(true);
	TestingCrawlingController fixture = getTestingCrawlingController();

	fixture.start();
	Mockito.when(TestingCrawlingController.mock.isFinished()).thenReturn(false);

	CrawlingMonitor monitor = fixture.stop();

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
	return CrawlingSettingsTest.getFixture("http://wp.pl/", new String[] { "Topic" });
    }
}
