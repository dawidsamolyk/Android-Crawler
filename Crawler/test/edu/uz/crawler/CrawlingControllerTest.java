package edu.uz.crawler;

import java.io.File;
import java.io.IOException;

import org.junit.Test;

public class CrawlingControllerTest {
    
    @Test
    public void test() throws IllegalArgumentException, IOException {
	CrawlingConfiguration config = new CrawlingConfiguration(new File("").toPath());
	CrawlingController controller = new CrawlingController(config);
    }
}
