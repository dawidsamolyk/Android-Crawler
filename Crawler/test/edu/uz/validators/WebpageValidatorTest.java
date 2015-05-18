package edu.uz.validators;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

public class WebpageValidatorTest {
    @Rule
    public ExpectedException exception = ExpectedException.none();

    @Test
    public void shouldPassValidHttpAddress() {
	WebpageValidator.checkUrl("http://www.wp.pl");
    }

    @Test
    public void shouldNotPassInvalidHttpAddress() {
	exception.expect(IllegalArgumentException.class);
	WebpageValidator.checkUrl("http://www.wp");
    }

    @Test
    public void shouldPassValidHttpsAddress() {
	WebpageValidator.checkUrl("https://www.wp.pl");
    }

    @Test
    public void shouldNotPassInvalidHttpsAddress() {
	exception.expect(IllegalArgumentException.class);
	WebpageValidator.checkUrl("http://www.wp");
    }

    @Test
    public void shouldNotPassFtpAddress() {
	exception.expect(IllegalArgumentException.class);
	WebpageValidator.checkUrl("ftp://example.pl");
    }

    @Test
    public void shouldNotPassInvalidAddress() {
	exception.expect(IllegalArgumentException.class);
	WebpageValidator.checkUrl("agadfasj213125fas.sad");
    }
}
