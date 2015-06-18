/*
 * IoT - Industrial Internet Framework
 * Apache License Version 2.0, January 2004
 * Released as a part of Helsinki University
 * Software Engineering Lab in summer 2015
 */
package fi.iot.iiframework.readers;

import fi.iot.iiframework.domain.Sensor;
import java.util.List;

/**
 * Defines reader for a InformationSource.
 */
public interface InformationSourceReader {

    /**
     * Reads data from given location.
     *
     * @param location Location of the data as a String
     * @return Correctly mutated InformationSourceObject
     */
    public List<Sensor> read(String location);
}
