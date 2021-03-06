/*
 * IoT - Industrial Internet Framework
 * Apache License Version 2.0, January 2004
 * Released as a part of Helsinki University
 * Software Engineering Lab in summer 2015
 */
package fi.iot.iiframework.application;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan(basePackages = {
    "fi.iot.iiframework.daos",
    "fi.iot.iiframework.services",
    "fi.iot.iiframework.source",
    "fi.iot.iiframework.database",
    "fi.iot.iiframework.errors",
    "fi.iot.iiframework.testclasses",
    "fi.iot.iiframework.errors.service",
    "fi.iot.iiframework.restapi",
    "fi.iot.iiframework.restapi.exceptions",
    "fi.iot.iiframework.restapi.filters",
    "fi.iot.iiframework.readers",
    "fi.iot.iiframework.security"
})
public class TestConfig {
    
}
