package edu.uz.validators;

import org.apache.commons.validator.routines.UrlValidator;

public class WebpageValidator {
	private final static String[] schemes = { "http", "https" };
	private final static UrlValidator urlValidator = new UrlValidator(schemes);

	public static void checkUrl(final String pageUrl) throws IllegalArgumentException {
		if (!urlValidator.isValid(pageUrl)) {
			throw new IllegalArgumentException("Niepoprawny format adresu strony WWW!");
		}
	}
}
