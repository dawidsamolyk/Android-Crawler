package edu.uz;

import org.junit.runner.JUnitCore;
import org.junit.runner.Result;
import org.junit.runner.RunWith;
import org.junit.runner.notification.Failure;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

import edu.uz.crawler.CrawlingConfigurationTest;
import edu.uz.crawler.MyCrawlerTest;
import edu.uz.validators.WebpageValidatorTest;

@RunWith(Suite.class)
@SuiteClasses({ CrawlingConfigurationTest.class, WebpageValidatorTest.class, MyCrawlerTest.class })
public class AllTests {
    public static void main(String[] args) {
	Result result = JUnitCore.runClasses(AllTests.class);
	for (Failure fail : result.getFailures()) {
	    System.out.println(fail.toString());
	}
	if (result.wasSuccessful()) {
	    System.out.println("All tests finished successfully...");
	}
    }
}
