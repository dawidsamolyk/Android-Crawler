package edu.uz;

import org.junit.runner.JUnitCore;
import org.junit.runner.Result;
import org.junit.runner.RunWith;
import org.junit.runner.notification.Failure;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

import edu.uz.crawler.CrawlerContentFilterTest;
import edu.uz.crawler.CrawlerVisitsTest;
import edu.uz.crawler.config.CrawlingConfigurationTest;
import edu.uz.crawler.config.CrawlingMonitorTest;
import edu.uz.crawler.config.CrawlingSettingsTest;
import edu.uz.crawler.controller.CrawlingControllerTest;

@RunWith(Suite.class)
//@formatter:off
@SuiteClasses({ 
    CrawlingConfigurationTest.class, 
    CrawlerContentFilterTest.class, 
    CrawlingSettingsTest.class, 
    CrawlingControllerTest.class,
    CrawlerVisitsTest.class,
    CrawlingMonitorTest.class
})
//@formatter:on
public class AllTests {
	public static void main(String[] args) {
		Result result = JUnitCore.runClasses(AllTests.class);
		for (Failure fail : result.getFailures()) {
			System.out.println(fail.toString());
		}
		if (result.wasSuccessful()) {
			System.out.println("All tests finished successfully...");
		}
		System.exit(0);
	}
}
