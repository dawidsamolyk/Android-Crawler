package edu.uz.crawler.config;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;

import java.io.File;
import java.io.IOException;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.mockito.Mockito;

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
	File storageDirectory = mock(File.class);
	Mockito.when(storageDirectory.isDirectory()).thenReturn(true);
	Mockito.when(storageDirectory.exists()).thenReturn(false);

	exception.expect(IOException.class);
	new CrawlingConfiguration(storageDirectory);
    }

    @Test
    public void shouldNotCreatesWithReadOnlyDirectory() throws Exception {
	File storageDirectory = mock(File.class);
	Mockito.when(storageDirectory.isDirectory()).thenReturn(true);
	Mockito.when(storageDirectory.exists()).thenReturn(true);
	Mockito.when(storageDirectory.canRead()).thenReturn(true);
	Mockito.when(storageDirectory.canWrite()).thenReturn(false);

	exception.expect(IOException.class);
	new CrawlingConfiguration(storageDirectory);
    }

    @Test
    public void shouldNotCreatesWithNonReadableDirectory() throws Exception {
	File storageDirectory = mock(File.class);
	Mockito.when(storageDirectory.isDirectory()).thenReturn(true);
	Mockito.when(storageDirectory.exists()).thenReturn(true);
	Mockito.when(storageDirectory.canRead()).thenReturn(false);

	exception.expect(IOException.class);
	new CrawlingConfiguration(storageDirectory);
    }

    @Test
    public void shouldNotCreatesWithFileGivenAsStorageDirectory() throws Exception {
	File file = mock(File.class);
	Mockito.when(file.isDirectory()).thenReturn(false);

	exception.expect(IOException.class);
	new CrawlingConfiguration(file);
    }

    @Test
    public void shouldCreatesWithExistentDirectoryWhichCanBeWrittenAndRead() throws Exception {
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
	File storageDirectory = Mockito.mock(File.class);
	Mockito.when(storageDirectory.isDirectory()).thenReturn(true);
	Mockito.when(storageDirectory.exists()).thenReturn(true);
	Mockito.when(storageDirectory.canRead()).thenReturn(true);
	Mockito.when(storageDirectory.canWrite()).thenReturn(true);
	Mockito.when(storageDirectory.getPath()).thenReturn("tempDir");

	return storageDirectory;
    }

}
