/*
 * IoT - Industrial Internet Framework
 * Apache License Version 2.0, January 2004
 * Released as a part of Helsinki University
 * Software Engineering Lab in summer 2015
 */
package fi.iot.iiframework.source;

import fi.iot.iiframework.domain.InformationSource;
import fi.iot.iiframework.domain.Sensor;
import fi.iot.iiframework.readers.InformationSourceReader;
import java.util.List;

/**
 *
 * An object representing an external data source
 */
public interface InformationSourceHandler {

    /**
     * Read a source from the reader.
     *
     * @return InformationSourceObject read from URL and parsed
     */
    public List<Sensor> read();

    /**
     * Reads an InformationSourceObject and writes it to database.
     *
     */
    public void readAndWrite();

    /**
     * Set configuration for this instance and update the readers and
     * schedulers, if they have changed.
     *
     * @param config
     */
    public void setSource(InformationSource config);

    /**
     * Get the configuration.
     *
     * @return
     */
    public InformationSource getSource();

    /**
     * Stop all read-operations.
     */
    public void close();

    /**
     * Manually set.
     *
     * @param mockReader
     */
    public void setReader(InformationSourceReader mockReader);

    public void setScheduler(ReadScheduler scheduler);
}
