/*
 * IoT - Industrial Internet Framework
 * Apache License Version 2.0, January 2004
 * Released as a part of Helsinki University
 * Software Engineering Lab in summer 2015
 */
package fi.iot.iiframework.restapi.exceptions;

public class InvalidObjectException extends Exception {

    public InvalidObjectException() {
    }

    public InvalidObjectException(String message) {
        super(message);
    }

    public InvalidObjectException(Throwable cause) {
        super(cause);
    }

    public InvalidObjectException(String message, Throwable cause) {
        super(message, cause);
    }

}
