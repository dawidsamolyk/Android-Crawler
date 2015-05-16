package edu.uz.crawler.utils;

import edu.uci.ics.crawler4j.parser.HtmlParseData;
import edu.uci.ics.crawler4j.parser.TextParseData;
import edu.uz.crawler.Crawler;

public class ContentFilter {

    private final boolean containsAllTopics(final String text) {
	String textToAnalyze = text.toLowerCase();

	for (String eachTopic : Crawler.SETTINGS.getTopics()) {
	    String topicToCheck = eachTopic.toLowerCase();

	    if (!textToAnalyze.contains(topicToCheck)) {
		return false;
	    }
	}
	return true;
    }

    private final boolean containsAnyTopic(final String text) {
	String textToAnalyze = text.toLowerCase();

	for (String eachTopic : Crawler.SETTINGS.getTopics()) {
	    String topicToCheck = eachTopic.toLowerCase();

	    if (textToAnalyze.contains(topicToCheck)) {
		return true;
	    }
	}
	return false;
    }

    private boolean containsSpecifiedTopics(String contentToAnalyze) {
	if (Crawler.SETTINGS.requireAllTopicsOnOnePage) {
	    return containsAllTopics(contentToAnalyze);
	}
	else {
	    return containsAnyTopic(contentToAnalyze);
	}
    }

    public final boolean containsTopicsWithSettings(final HtmlParseData htmlParseData) {
	String contentToAnalyze = htmlParseData.getTitle();

	if (Crawler.SETTINGS.contentSearch) {
	    contentToAnalyze += htmlParseData.getText();
	}

	return containsSpecifiedTopics(contentToAnalyze);
    }

    public boolean containsTopicsWithSettings(TextParseData textParseData) {
	String contentToAnalyze = textParseData.getTextContent();

	return containsSpecifiedTopics(contentToAnalyze);
    }

}
