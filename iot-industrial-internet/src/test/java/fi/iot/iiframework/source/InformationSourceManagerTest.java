/*
 * IoT - Industrial Internet Framework
 * Apache License Version 2.0, January 2004
 * Released as a part of Helsinki University
 * Software Engineering Lab in summer 2015
 */
package fi.iot.iiframework.source;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

public class InformationSourceManagerTest {

    @Test
    public void createdSourceIsAddedToSources() {
        InformationSourceConfiguration config = new InformationSourceConfiguration();
        InformationSourceManager.createSource(config);
        assertEquals(1, InformationSourceManager.getSources().size());
    }
}
