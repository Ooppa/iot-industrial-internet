/*
 * IoT - Industrial Internet Framework
 * Apache License Version 2.0, January 2004
 * Released as a part of Helsinki University
 * Software Engineering Lab in summer 2015
 */
package fi.iot.iiframework.services.domain;

import fi.iot.iiframework.domain.InformationSource;
import fi.iot.iiframework.domain.Sensor;
import fi.iot.iiframework.services.GenericService;
import java.util.List;

public interface SensorService extends GenericService<Sensor, Long> {

    /**
     * Gets all sensors associated with the given configuration.
     *
     * @param source
     * @return
     */
    public List<Sensor> getBy(InformationSource source);

    /**
     * Gets sensors associated with the given device from the given range.
     *
     * @param from
     * @param to
     * @param source
     * @return
     */
    public List<Sensor> getBy(int from, int to, InformationSource source);

    /**
     * Returns number of sensors associated with the given device
     *
     * @param source
     * @return
     */
    public Long countBy(InformationSource source);
}
