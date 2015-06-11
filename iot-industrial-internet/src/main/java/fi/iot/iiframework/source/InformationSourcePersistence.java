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
 *
 * @author atte
 */
public interface InformationSourcePersistence {

    List<InformationSource> loadSourcesFromDB();

    void updateSourceWithSensors(InformationSource source, List<Sensor> sensors);
    
    void updateSource(InformationSource source);
    
    void addSource(InformationSource source);
    
    void deleteSource(InformationSource source);
}