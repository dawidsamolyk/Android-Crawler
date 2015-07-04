package edu.uz.crawler;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import edu.uci.ics.crawler4j.crawler.Page;
import edu.uci.ics.crawler4j.url.WebURL;
import edu.uz.crawler.config.CrawlingSettingsTest;

public class CrawlerVisitsTest {
    @Rule
    public ExpectedException exception = ExpectedException.none();

    @Test
    public void shouldNotVisitAnyPageIfSettingsAreNotSetted() {
	Crawler fixture = new Crawler();
	Crawler.SETTINGS = null;

	exception.expect(IllegalStateException.class);
	fixture.shouldVisit(new WebURL());
    }

    @Test
    public void shouldNotVisitPageWhichIsNotInSameDomainAsCrawlingPage() {
	String webpageAddress = "http://www.wp.pl";
	configureSettings(webpageAddress);

	String actualCrawlingWebpage = "http://www.onet.pl/index.html";
	boolean shouldVisit = new Crawler().shouldVisit( getWebURL(actualCrawlingWebpage));

	assertFalse("Strona b�d�ca w innej domenie nie powinna by� odwiedzona przez Crawler!",
		shouldVisit);
    }

    @Test
    public void shouldNotVisitPageWhichIsNotContainContent() {
	String webpageAddress = "http://www.wp.pl";
	configureSettings(webpageAddress);

	String actualCrawlingWebpage = "http://www.wp.pl/file.zip";
	boolean shouldVisit = new Crawler().shouldVisit( getWebURL(actualCrawlingWebpage));

	assertFalse("Adres, kt�ry nie prowadzi do strony z tre�ci� nie powinien by� odwiedzony!",
		shouldVisit);
    }

    @Test
    public void shouldVisitPageWhichIsInTheSameDomainButOnSampleSubpage() {
	String webpageAddress = "http://www.wp.pl";
	configureSettings(webpageAddress);

	String actualCrawlingWebpage = "http://www.wp.pl/topic/subpage.html";
	boolean shouldVisit = new Crawler().shouldVisit( getWebURL(actualCrawlingWebpage));

	assertTrue("Strona powinna by� odwiedzona, poniewa� ma tylko inny protok�!", shouldVisit);
    }

    @Test
    public void shouldVisitPageWhichIsInTheSameDomainButWithAnotherProtocol() {
	String webpageAddress = "http://www.wp.pl";
	configureSettings(webpageAddress);

	String actualCrawlingWebpage = "https://www.wp.pl/topic/subpage.html";
	boolean shouldVisit = new Crawler().shouldVisit( getWebURL(actualCrawlingWebpage));

	assertTrue("Strona powinna by� odwiedzona, poniewa� ma tylko inny protok�!", shouldVisit);
    }

    @Test
    public void shouldVisitPageWhichIsInTheSameDomainButWithShortNotation() {
	String webpageAddress = "http://www.wp.pl";
	configureSettings(webpageAddress);

	String actualCrawlingWebpage = "http://wp.pl/topic/subpage.html";
	boolean shouldVisit = new Crawler().shouldVisit( getWebURL(actualCrawlingWebpage));

	assertTrue(
		"Strona powinna by� odwiedzona, poniewa� ma tylko kr�tszy zapis (bez przedrostka www)!",
		shouldVisit);
    }

    @Test
    public void shouldVisitMobileWebpageVersion() {
	String webpageAddress = "http://www.wp.pl";
	configureSettings(webpageAddress);

	String actualCrawlingWebpage = "http://m.wp.pl/topic/subpage.html";
	boolean shouldVisit = new Crawler().shouldVisit( getWebURL(actualCrawlingWebpage));

	assertTrue("Strona powinna by� odwiedzona, poniewa� jest jedynie wersj� mobiln�!",
		shouldVisit);
    }

    public void configureSettings(String webpageAddress) {
	Crawler.SETTINGS = CrawlingSettingsTest
		.getFixture(webpageAddress, new String[] { "Topic" });
    }

    public static WebURL getWebURL(final String webpageAddress) {
	WebURL url = new WebURL();
	url.setURL(webpageAddress);
	return url;
    }
}
