/*
 * IoT - Industrial Internet Framework
 * Apache License Version 2.0, January 2004
 * Released as a part of Helsinki University
 * Software Engineering Lab in summer 2015
 */
package fi.iot.iiframework.security;

import org.owasp.html.HtmlPolicyBuilder;
import org.owasp.html.PolicyFactory;
import org.springframework.stereotype.Component;

@Component
public class SanitizationHelper {

    private static final PolicyFactory noHTML = new HtmlPolicyBuilder().toFactory();

    private static final PolicyFactory strict = new HtmlPolicyBuilder()
            .allowElements("strong", "b", "i", "u")
            .toFactory();

    private static final PolicyFactory loose = new HtmlPolicyBuilder()
            .allowElements("a")
            .disallowWithoutAttributes("a")
            .allowAttributes("href").onElements("a")
            .allowAttributes("title").onElements("a")
            .allowElements("h1", "h2", "h3", "h4", "h5", "h6")
            .allowElements("b", "strong", "u", "i", "p")
            .toFactory();

    /**
     * Sanitizes the input using a Strict policy, only following HTML tags:
     * strong, b, i and u.
     *
     * @param input Input String
     * @return Sanitized String
     */
    public static String sanitizeStrict(String input) {
        return sanitize(input, strict);
    }

    /**
     * Sanitizes the input using a Loose policy, only following HTML tags: a
     * (with attributes href and title), h1-h6, b, strong, u, i and p.
     *
     * @param input Input String
     * @return Sanitized String
     */
    public static String sanitizeLoose(String input) {
        return sanitize(input, loose);
    }

    /**
     * Sanitizes the input using a No HTML policy.
     *
     * @param input Input String
     * @return Sanitized String
     */
    public static String sanitizeNoHTML(String input) {
        return sanitize(input, noHTML);
    }

    /*
     * Sanitizes a String using given policy.
     */
    private static String sanitize(String input, PolicyFactory policy) {
        return policy.sanitize(input);
    }

}
