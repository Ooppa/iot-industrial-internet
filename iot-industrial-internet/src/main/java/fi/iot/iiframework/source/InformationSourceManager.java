/*
 * IoT - Industrial Internet Framework
 * Apache License Version 2.0, January 2004
 * Released as a part of Helsinki University
 * Software Engineering Lab in summer 2015
 */
package fi.iot.iiframework.source;

/**
 *
 * @author atte
 */
public interface InformationSourceManager {

    /**
     *
     * Creates an object that represents an external data source
     *
     * @param config the new configuration for this data source
     */
    public void createSource(InformationSourceConfiguration config);

    /**
     * Deletes the object that represents an external data source defined by the
     * id.
     *
     * @param id id of source to be deleted
     */
    public void removeSource(String id);

    /**
     *
     * Updates the configuration information of an object that represents an
     * external data source
     *
     * @param config the new configuration that will replace the previous one
     */
    public void updateSource(InformationSourceConfiguration config);

    public boolean readSource(String id);
}
