package edu.uz.crawler.config;

import static org.junit.Assert.assertEquals;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import edu.uci.ics.crawler4j.url.WebURL;

public class CrawlingSettingsTest {
	@Rule
	public ExpectedException exception = ExpectedException.none();

	@Test
	public void shouldNotCreatesWithoutWebpageUrl() {
		exception.expect(IllegalArgumentException.class);
		new CrawlingSettings(null, new String[] { "Topic" });
	}

	@Test
	public void shouldNotCreatesWithoutTopics() {
		exception.expect(IllegalArgumentException.class);
		new CrawlingSettings(new WebURL(), null);
	}

	@Test
	public void shouldNotCreatesWithNotSpecifiedWebAddress() {
		exception.expect(IllegalArgumentException.class);
		new CrawlingSettings(new WebURL(), new String[] { "Topic" });
	}

	@Test
	public void shouldNotCreatesWithEmptyTopicsList() {
		exception.expect(IllegalArgumentException.class);
		WebURL url = new WebURL();
		url.setURL("wp.pl/");
		new CrawlingSettings(url, new String[] {});
	}

	@Test
	public void shouldNotCreatesWithAnyNotSpecifiedTopic() {
		exception.expect(IllegalArgumentException.class);
		WebURL url = new WebURL();
		url.setURL("wp.pl/");
		String[] topics = new String[] { "Topic", null, "Topic 2" };
		new CrawlingSettings(url, topics);
	}

	@Test
	public void shouldNotCreatesWithAnyEmptyTopic() {
		exception.expect(IllegalArgumentException.class);
		WebURL url = new WebURL();
		url.setURL("http://wp.pl/");
		String[] topics = new String[] { "Topic", "", "Topic 2" };
		new CrawlingSettings(url, topics);
	}

	@Test
	public void shouldGivesWebpageAddress() {
		String webpageAddress = "http://wp.pl/";
		CrawlingSettings fixture = getFixture(webpageAddress, new String[] { "Topic" });

		assertEquals(webpageAddress, fixture.getWebpageURL().getURL());
	}

	public static CrawlingSettings getFixture(String webUrl, String[] topics) {
		WebURL url = new WebURL();
		url.setURL(webUrl);

		return new CrawlingSettings(url, topics);
	}
}
