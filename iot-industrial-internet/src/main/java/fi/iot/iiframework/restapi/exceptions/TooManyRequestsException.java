/*
 * IoT - Industrial Internet Framework
 * Apache License Version 2.0, January 2004
 * Released as a part of Helsinki University
 * Software Engineering Lab in summer 2015
 */
package fi.iot.iiframework.restapi.exceptions;

public class TooManyRequestsException extends Exception {

    public TooManyRequestsException() {
    }

    public TooManyRequestsException(String message) {
        super(message);
    }

    public TooManyRequestsException(Throwable cause) {
        super(cause);
    }

    public TooManyRequestsException(String message, Throwable cause) {
        super(message, cause);
    }

}
