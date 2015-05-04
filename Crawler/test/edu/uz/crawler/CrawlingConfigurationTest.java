package edu.uz.crawler;

import java.io.File;
import java.io.IOException;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

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
	new CrawlingConfiguration(file.toPath());
    }

    @Test
    public void shouldNotCreatesWithReadOnlyDirectory() throws Exception {
	File file = File.createTempFile("testTempFile", "");
	file.setReadOnly();
	file.deleteOnExit();

	exception.expect(IOException.class);
	new CrawlingConfiguration(file.toPath());
    }

    @Test
    public void shouldNotCreatesWithNotReadableDirectory() throws Exception {
	File file = File.createTempFile("testTempFile", "");
	file.setReadable(false);
	file.deleteOnExit();

	exception.expect(IOException.class);
	new CrawlingConfiguration(file.toPath());
    }

}
