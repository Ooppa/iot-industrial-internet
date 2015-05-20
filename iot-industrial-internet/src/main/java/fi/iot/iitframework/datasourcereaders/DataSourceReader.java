/*
 * IoT - Industrial Internet Framework
 * Apache License Version 2.0, January 2004
 * Released as a part of Helsinki University
 * Software Engineering Lab in summer 2015
 */
package fi.iot.iitframework.datasourcereaders;

import fi.iot.iitframework.dataobject.DataObject;

/**
 * Defines reader for a DataSource.
 * 
 */
public interface DataSourceReader {
    public DataObject read();
}
