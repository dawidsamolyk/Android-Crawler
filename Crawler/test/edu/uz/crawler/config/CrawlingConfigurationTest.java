package edu.uz.crawler.config;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.io.File;
import java.io.IOException;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import com.google.common.io.Files;

public class CrawlingConfigurationTest {
    @Rule
    public ExpectedException exception = ExpectedException.none();

    @Test
    public void shouldNotCreatesWithEmptyStorageDirectoryPath() throws Exception {
	exception.expect(IllegalArgumentException.class);
	new CrawlingConfiguration(null);
    }
    
    @Test
    public void shouldNotCreatesWithNotExistentDirectory() throws Exception {
	File file = new File("testDir");

	exception.expect(IOException.class);
	new CrawlingConfiguration(file);
    }

    @Test
    public void shouldNotCreatesWithFileGivenAsStorageDirectory() throws Exception {
	File file = File.createTempFile("testTempFile", "");
	file.deleteOnExit();

	exception.expect(IOException.class);
	new CrawlingConfiguration(file);
    }

    @Test
    public void shouldCreatesWithDirectory() throws Exception {
	File storageDirectory = getFixtureDirectory();

	CrawlingConfiguration fixture = new CrawlingConfiguration(storageDirectory);

	assertEquals("Niepoprawna œcie¿ka do utworzonoego katalogu!", storageDirectory.getPath(),
		fixture.getCrawlStorageFolder());
    }
    
    @Test
    public void shouldBeEnabledToSetSearchAlsoInSubpages() throws Exception {
	File storageDirectory = getFixtureDirectory();
	CrawlingConfiguration fixture = new CrawlingConfiguration(storageDirectory);

	fixture.searchAlsoInSubpages();
	
	assertTrue(fixture.getMaxDepthOfCrawling() > 1);
    }
    
    @Test
    public void shouldBeEnabledToSetSearchOnlyInSelectedPage() throws Exception {
	File storageDirectory = getFixtureDirectory();
	CrawlingConfiguration fixture = new CrawlingConfiguration(storageDirectory);

	fixture.searchOnlyInSelectedPage();
	
	assertTrue(fixture.getMaxDepthOfCrawling() == 1);
    }

    public static File getFixtureDirectory() {
	File storageDirectory = Files.createTempDir();
	storageDirectory.deleteOnExit();
	return storageDirectory;
    }

}
