/*
 * IoT - Industrial Internet Framework
 * Apache License Version 2.0, January 2004
 * Released as a part of Helsinki University
 * Software Engineering Lab in summer 2015
 */
package fi.iot.iiframework.restapi;

import lombok.Data;

@Data
public class SuccessObject {
    String message;

    public SuccessObject(String message) {
        this.message = message;
    }
    
    
}
