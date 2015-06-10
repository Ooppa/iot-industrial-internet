/*
 * IoT - Industrial Internet Framework
 * Apache License Version 2.0, January 2004
 * Released as a part of Helsinki University
 * Software Engineering Lab in summer 2015
 */
package fi.iot.iiframework.mutator;

import fi.iot.iiframework.domain.InformationSourceObject;
import fi.iot.iiframework.errors.ErrorLogger;
import fi.iot.iiframework.errors.ErrorSeverity;
import fi.iot.iiframework.errors.ErrorType;

public abstract class Mutator {

    public static void mutate(InformationSourceObject informationSourceObject){
        ErrorLogger.log(ErrorType.UNKNOWN_ERROR, ErrorSeverity.NOTIFICATION, "Tried to call abstract class Mutator and not extended classes.");
    };
}
