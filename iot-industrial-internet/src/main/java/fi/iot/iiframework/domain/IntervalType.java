/*
 * IoT - Industrial Internet Framework
 * Apache License Version 2.0, January 2004
 * Released as a part of Helsinki University
 * Software Engineering Lab in summer 2015
 */
package fi.iot.iiframework.domain;

/**
 * Defines a type of interval in which a source is read.
 */
public enum IntervalType {

    NEVER,
    HOURLY,
    DAILY,
    WEEKLY,
    MONTHLY,
    OTHER;
}
