/*
 * IoT - Industrial Internet Framework
 * Apache License Version 2.0, January 2004
 * Released as a part of Helsinki University
 * Software Engineering Lab in summer 2015
 */
package fi.iot.iiframework.parsers;

import fi.iot.iiframework.domain.InformationSourceObject;
import fi.iot.iiframework.errors.ErrorLogger;
import fi.iot.iiframework.errors.ErrorSeverity;
import fi.iot.iiframework.errors.ErrorType;

public abstract class Parser {

    /**
     * Attempts to parse data from given location to a InformationSourceObject.
     *
     * @param location Location of the data
     * @return InformationSourceObject
     */
    public static InformationSourceObject parse(String location){
        ErrorLogger.log(ErrorType.UNKNOWN_ERROR, ErrorSeverity.NOTIFICATION, "Tried to call abstract class Parser and not extended classes.");
        return null;
    };
}
        