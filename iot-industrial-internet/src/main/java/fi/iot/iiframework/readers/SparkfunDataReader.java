/*
 * IoT - Industrial Internet Framework
 * Apache License Version 2.0, January 2004
 * Released as a part of Helsinki University
 * Software Engineering Lab in summer 2015
 */
package fi.iot.iiframework.readers;

import fi.iot.iiframework.domain.Sensor;
import fi.iot.iiframework.parsers.SparkfunDataParser;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

public class SparkfunDataReader implements InformationSourceReader {

    @Override
    public List<Sensor> read(String location) {
        Collection<Sensor> sensorsCol = SparkfunDataParser.parse(location);
        List<Sensor> sensors = Arrays.asList(sensorsCol.toArray(new Sensor[0]));

        return sensors;
    }

}
