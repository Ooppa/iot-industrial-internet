/*
 * IoT - Industrial Internet Framework
 * Apache License Version 2.0, January 2004
 * Released as a part of Helsinki University
 * Software Engineering Lab in summer 2015
 */
package fi.iot.iiframework.restapi;

import lombok.Data;

@Data
public class StatObject {

    private final String name;
    private final String description;
    private final Object value;

    public StatObject(String name, String description, Object value) {
        this.name = name;
        this.description = description;
        this.value = value;
    }

}
