/*
 * IoT - Industrial Internet Framework
 * Apache License Version 2.0, January 2004
 * Released as a part of Helsinki University
 * Software Engineering Lab in summer 2015
 */
package fi.iot.iiframework.security;

import org.junit.Test;
import static org.junit.Assert.*;

public class SanitizationHelperTest {

    private static final String alwaysAcceptedInputs[] = {
        "Default String with no HTML or CSS",
        "String with . / % and even ()",
        "             Alot of whitespaces                "
    };

    private static final String sometimesAcceptedInputs[] = {
        "<b>Bold text</b>",
        "<i>Italic text</i>",
        "<a href=\"#\">link text</a>",};

    private static final String testNotAcceptedInputs[] = {
        "<a>link text</a>",
        "<a style=\"display: none;\">link text</a>",
        "<div>link text</div>",
        "<SCRIPT type=\"text/javascript\">Default Script-tag String</SCRIPT>",
        "<body onload=alert('Noob attack')>",
        "<b onmouseover=alert('Wufff!')>More complex JS Attack</b>",
        "<IMG SRC=j&#X41vascript:alert('Attack using encoded URI schemes')>",};

    public SanitizationHelperTest() {
    }

    @Test
    public void testSanitizeStrictWithAcceptedInputs() {
        for (int i = 0; i < alwaysAcceptedInputs.length; i++) {
            assertEquals(alwaysAcceptedInputs[i], SanitizationHelper.sanitizeStrict(alwaysAcceptedInputs[i]));
        }
    }

    @Test
    public void testSanitizeStrictWithNotAcceptedInputs() {
        for (int i = 0; i < testNotAcceptedInputs.length; i++) {
            assertNotEquals(testNotAcceptedInputs[i], SanitizationHelper.sanitizeStrict(testNotAcceptedInputs[i]));
        }
    }

    @Test
    public void testSanitizeLooseWithAcceptedInputs() {
        for (int i = 0; i < alwaysAcceptedInputs.length; i++) {
            assertEquals(alwaysAcceptedInputs[i], SanitizationHelper.sanitizeLoose(alwaysAcceptedInputs[i]));
        }

        for (int i = 0; i < sometimesAcceptedInputs.length; i++) {
            assertEquals(sometimesAcceptedInputs[i], SanitizationHelper.sanitizeLoose(sometimesAcceptedInputs[i]));
        }
    }

    @Test
    public void testSanitizeLooseWithNotAcceptedInputs() {
        for (int i = 0; i < testNotAcceptedInputs.length; i++) {
            assertNotEquals(testNotAcceptedInputs[i], SanitizationHelper.sanitizeLoose(testNotAcceptedInputs[i]));
        }
    }

}
