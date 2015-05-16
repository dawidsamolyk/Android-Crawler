package edu.uz.crawler.config;

import static org.junit.Assert.*;

import java.io.File;
import java.io.IOException;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import com.google.common.io.Files;

import edu.uz.crawler.config.CrawlingConfiguration;

public class CrawlingConfigurationTest {
    @Rule
    public ExpectedException exception = ExpectedException.none();

    @Test
    public void shouldNotCreatesWithEmptyStorageDirectoryPath() throws Exception {
	exception.expect(IllegalArgumentException.class);
	new CrawlingConfiguration(null);
    }

    @Test
    public void shouldNotCreatesWithFileGivenAsStorageDirectory() throws Exception {
	File file = File.createTempFile("testTempFile", "");
	file.deleteOnExit();

	exception.expect(IOException.class);
	new CrawlingConfiguration(file);
    }

    @Test
    public void shouldNotCreatesWithReadOnlyDirectory() throws Exception {
	File file = Files.createTempDir();
	file.setReadOnly();
	file.deleteOnExit();

	exception.expect(IOException.class);
	new CrawlingConfiguration(file);
    }

    @Test
    public void shouldNotCreatesWithNotReadableDirectory() throws Exception {
	File file = Files.createTempDir();
	file.setReadable(false);
	file.deleteOnExit();

	exception.expect(IOException.class);
	new CrawlingConfiguration(file);
    }

    @Test
    public void shouldCreatesWithDirectory() throws Exception {
	File file = Files.createTempDir();
	file.deleteOnExit();

	CrawlingConfiguration fixture = new CrawlingConfiguration(file);

	assertEquals("Niepoprawna œcie¿ka do utworzonoego katalogu!", file.getPath(),
		fixture.getCrawlStorageFolder());
    }

}
