package edu.uz.crawler;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import edu.uci.ics.crawler4j.crawler.Page;
import edu.uci.ics.crawler4j.parser.BinaryParseData;
import edu.uz.crawler.config.CrawlingSettings;

public class CrawlerContentFilterTest {
    @Rule
    public ExpectedException exception = ExpectedException.none();

    @Before
    public void setUp() throws Exception {
	Crawler.PAGES_TO_SAVE.clear();
    }

    @After
    public void tearDown() throws Exception {
	Crawler.SETTINGS = null;
    }

    @Test
    public void shouldThrowErrorWhenSettingsAreNotSetted() {
	exception.expect(IllegalStateException.class);
	testHtmlPage("", "", null);
    }

    @Test
    public void shouldNotDownladPageIfItIsNotHtmlOrText() {
	String[] topics = Fixtures.getFixtureTopics("Dog", "Cat");
	CrawlingSettings settings = new CrawlingSettings(Fixtures.getFixtureWebpageUrl(), topics);
	Page fixturePage = new Page(Fixtures.getFixtureWebpageUrl());
	fixturePage.setParseData(new BinaryParseData());
	Crawler fixture = new Crawler();
	Crawler.SETTINGS = settings;

	fixture.visit(fixturePage);

	assertFalse("Strona nie powinna byæ dodana do kolejki pobierania!",
		isNewPageAddedToDownload());
    }

    @Test
    public void shouldDownloadTextPageIfContainsAnyTopic() {
	String[] topics = Fixtures.getFixtureTopics("Dog", "Cat");
	CrawlingSettings settings = new CrawlingSettings(Fixtures.getFixtureWebpageUrl(), topics);
	settings.contentSearch = true;
	settings.requireAllTopicsOnOnePage = false;
	String content = "My dog is my best friend...";

	testTextPage(content, settings);

	assertTrue("Nie dodano strony do kolejki pobierania!", isNewPageAddedToDownload());
    }
    
    @Test
    public void shouldNotDownloadTextPageIfNotContainsAnyTopic() {
	String[] topics = Fixtures.getFixtureTopics("Dog", "Cat");
	CrawlingSettings settings = new CrawlingSettings(Fixtures.getFixtureWebpageUrl(), topics);
	settings.contentSearch = false;
	settings.requireAllTopicsOnOnePage = false;
	String content = "Best friend for the programmer is a frog, because...";

	testTextPage(content, settings);

	assertFalse("Strona nie powinna byæ dodana do kolejki pobierania!",
		isNewPageAddedToDownload());
    }

    @Test
    public void shouldDownloadHtmlPageIfContainsAnyTopicInTitle() {
	String[] topics = Fixtures.getFixtureTopics("Dog", "Cat");
	CrawlingSettings settings = new CrawlingSettings(Fixtures.getFixtureWebpageUrl(), topics);
	settings.contentSearch = false;
	settings.requireAllTopicsOnOnePage = false;
	String title = "I love my dog!";
	String content = "My dog is my best friend...";

	testHtmlPage(title, content, settings);

	assertTrue("Nie dodano strony do kolejki pobierania!", isNewPageAddedToDownload());
    }

    @Test
    public void shouldDownloadHtmlPageIfContainsAnyTopicInContent() {
	String[] topics = Fixtures.getFixtureTopics("Dog", "Cat");
	CrawlingSettings settings = new CrawlingSettings(Fixtures.getFixtureWebpageUrl(), topics);
	settings.contentSearch = true;
	settings.requireAllTopicsOnOnePage = false;
	String title = "I love my dog!";
	String content = "My dog is my best friend...";

	testHtmlPage(title, content, settings);

	assertTrue("Nie dodano strony do kolejki pobierania!", isNewPageAddedToDownload());
    }

    @Test
    public void shouldDownloadHtmlPageIfContainsAnyTopicInTopicButNotInTheContent() {
	String[] topics = Fixtures.getFixtureTopics("Dog", "Cat");
	CrawlingSettings settings = new CrawlingSettings(Fixtures.getFixtureWebpageUrl(), topics);
	settings.contentSearch = true;
	settings.requireAllTopicsOnOnePage = false;
	String title = "I love my cat!";
	String content = "My frog is my best friend...";

	testHtmlPage(title, content, settings);

	assertTrue("Nie dodano strony do kolejki pobierania!", isNewPageAddedToDownload());
    }

    @Test
    public void shouldNotDownloadHtmlPageIfNotContainsAnyTopicInTitle() {
	String[] topics = Fixtures.getFixtureTopics("Dog", "Cat");
	CrawlingSettings settings = new CrawlingSettings(Fixtures.getFixtureWebpageUrl(), topics);
	settings.contentSearch = false;
	settings.requireAllTopicsOnOnePage = false;
	String title = "The best pets for programmers";
	String content = "Best friend for the programmer is a dog, because...";

	testHtmlPage(title, content, settings);

	assertFalse("Strona nie powinna byæ dodana do kolejki pobierania!",
		isNewPageAddedToDownload());
    }

    @Test
    public void shouldNotDownloadHtmlPageIfNotContainsAnyTopicInContent() {
	String[] topics = Fixtures.getFixtureTopics("Dog", "Cat");
	CrawlingSettings settings = new CrawlingSettings(Fixtures.getFixtureWebpageUrl(), topics);
	settings.contentSearch = true;
	settings.requireAllTopicsOnOnePage = false;
	String title = "The best pets for programmers";
	String content = "Best friend for the programmer is a frog, because...";

	testHtmlPage(title, content, settings);

	assertFalse("Strona nie powinna byæ dodana do kolejki pobierania!",
		isNewPageAddedToDownload());
    }

    @Test
    public void shouldDownloadHtmlPageIfContainsAllTopicsInTitle() {
	String[] topics = Fixtures.getFixtureTopics("Dog", "Cat");
	CrawlingSettings settings = new CrawlingSettings(Fixtures.getFixtureWebpageUrl(), topics);
	settings.contentSearch = false;
	settings.requireAllTopicsOnOnePage = true;
	String title = "I love my dog and cat!";
	String content = "My dog is my best friend, but I like cats too...";

	testHtmlPage(title, content, settings);

	assertTrue("Nie dodano strony do kolejki pobierania!", isNewPageAddedToDownload());
    }

    @Test
    public void shouldNotDownloadHtmlPageIfNotContainsExcatlyAllTopicsInTitle() {
	String[] topics = Fixtures.getFixtureTopics("Dog", "Cat");
	CrawlingSettings settings = new CrawlingSettings(Fixtures.getFixtureWebpageUrl(), topics);
	settings.contentSearch = false;
	settings.requireAllTopicsOnOnePage = true;
	String title = "I love my dog!";
	String content = "My dog is my best friend...";

	testHtmlPage(title, content, settings);

	assertFalse("Strona nie powinna byæ dodana do kolejki pobierania!",
		isNewPageAddedToDownload());
    }

    public static final boolean isNewPageAddedToDownload() {
	return Crawler.PAGES_TO_SAVE.size() == 1;
    }

    public void testHtmlPage(String title, String content, CrawlingSettings settings) {
	Crawler.SETTINGS = settings;
	Crawler fixture = new Crawler();
	Page pageFixture = Fixtures.getHtmlPageFixtureWith(title, content);

	fixture.visit(pageFixture);
    }
    
    public void testTextPage(String content, CrawlingSettings settings) {
	Crawler.SETTINGS = settings;
	Crawler fixture = new Crawler();
	Page pageFixture = Fixtures.getTextPageFixtureWith(content);

	fixture.visit(pageFixture);
    }
}
