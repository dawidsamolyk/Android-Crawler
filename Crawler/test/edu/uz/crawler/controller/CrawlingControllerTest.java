package edu.uz.crawler.controller;

import java.io.File;
import java.io.IOException;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import edu.uz.crawler.config.CrawlingConfiguration;
import edu.uz.crawler.config.CrawlingSettings;
import edu.uz.crawler.config.CrawlingSettingsTest;

public class CrawlingControllerTest {
    @Rule
    public ExpectedException exception = ExpectedException.none();

    @Test
    public void shouldNotCreatesWithoutConfiguration() {
	exception.expect(IllegalArgumentException.class);
	new CrawlingController(null, getSettingsFixture());
    }

    @Test
    public void shouldNotCreatesWithoutSettings() throws IllegalArgumentException, IOException {
	exception.expect(IllegalArgumentException.class);
	new CrawlingController(getFixtureConfiguration(), null);
    }

    private CrawlingConfiguration getFixtureConfiguration() throws IllegalArgumentException,
	    IOException {
	return new CrawlingConfiguration(new File(""));
    }

    private CrawlingSettings getSettingsFixture() {
	return CrawlingSettingsTest.getFixture("http://wp.pl", new String[] { "Topic" });
    }
}
