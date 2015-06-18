/*
 * IoT - Industrial Internet Framework
 * Apache License Version 2.0, January 2004
 * Released as a part of Helsinki University
 * Software Engineering Lab in summer 2015
 */
package fi.iot.iiframework.source;

import fi.iot.iiframework.domain.InformationSource;
import fi.iot.iiframework.domain.Sensor;
import java.util.List;

/**
 * Interface for loading and updating source from and in the database, and
 * updating their information.
 */
public interface InformationSourcePersistence {

    /**
     * Loads all InformationSources from the database.
     *
     * @return List of InformationSources
     */
    public List<InformationSource> loadSourcesFromDB();

    /**
     * Update InformationSources with read sensors and their readouts.
     *
     * @param source
     * @param sensors
     * @return
     */
    public InformationSource writeReadoutsToSource(InformationSource source, List<Sensor> sensors);

    /**
     * Updates an InformationSources fields in the database.
     *
     * @param source
     * @return
     */
    public InformationSource updateSource(InformationSource source);

    /**
     * Adds a new InformationSource to the database
     *
     * @param source
     * @return
     */
    public InformationSource addSource(InformationSource source);

    /**
     * Deletes a source from the database.
     *
     * @param source
     */
    public void deleteSource(InformationSource source);
}
