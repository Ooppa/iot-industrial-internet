/*
 * IoT - Industrial Internet Framework
 * Apache License Version 2.0, January 2004
 * Released as a part of Helsinki University
 * Software Engineering Lab in summer 2015
 */
package fi.iot.iiframework.source;

import fi.iot.iiframework.domain.InformationSourceConfiguration;
import fi.iot.iiframework.domain.Sensor;
import fi.iot.iiframework.readers.InformationSourceReader;
import java.net.MalformedURLException;
import java.util.List;
import javax.xml.bind.JAXBException;

/**
 *
 * An object representing an external data source
 */
public interface InformationSource {

    /**
     * Read a source from the reader
     *
     * @return InformationSourceObject read from URL and parsed
     */
    public List<Sensor> read();

    /**
     * Reads an InformationSourceObject and writes it to database.
     *
     * @return true, if successful, false if unsuccessful
     */
    public boolean readAndWrite();

    /**
     * Set configuration for this instance and update the readers and
     * schedulers, if they have changed.
     *
     * @param config
     */
    public void setConfig(InformationSourceConfiguration config);

    /**
     * Get the configuration.
     * @return 
     */
    public InformationSourceConfiguration getConfig();
    
    /**
     * Stop all read-operations.
     */
    public void close();

    /**
     * Manually set 
     * @param mockReader 
     */
    public void setReader(InformationSourceReader mockReader);
    
    public void setScheduler(ReadScheduler scheduler);
}
