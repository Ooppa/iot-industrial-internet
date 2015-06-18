/*
 * IoT - Industrial Internet Framework
 * Apache License Version 2.0, January 2004
 * Released as a part of Helsinki University
 * Software Engineering Lab in summer 2015
 */
package fi.iot.iiframework.services.errors;

import fi.iot.iiframework.errors.SysError;
import fi.iot.iiframework.services.GenericService;

/**
 * Service for manipulating errors in database.
 */
public interface ErrorService extends GenericService<SysError, Long> {

}
