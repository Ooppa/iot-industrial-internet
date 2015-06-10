/*
 * IoT - Industrial Internet Framework
 * Apache License Version 2.0, January 2004
 * Released as a part of Helsinki University
 * Software Engineering Lab in summer 2015
 */
package fi.iot.iiframework.readers;

import fi.iot.iiframework.domain.Sensor;
import fi.iot.iiframework.errors.ErrorLogger;
import fi.iot.iiframework.errors.ErrorSeverity;
import fi.iot.iiframework.errors.ErrorType;
import fi.iot.iiframework.mutator.SensorDisablingMutator;
import fi.iot.iiframework.parsers.XmlParser;
import java.util.List;

public class XMLReader implements InformationSourceReader {

    private XmlParser parser;

    @Override
    public List<Sensor> read(String location) {
        List<Sensor> object = parser.parse(location);
        
        if(object!=null){
            SensorDisablingMutator.mutate(object);
        } else {
            ErrorLogger.log(ErrorType.PARSE_ERROR, ErrorSeverity.LOW, "Attempted to mutate object that was null.");
        }
        
        return object;
    }

}
