/*
 * IoT - Industrial Internet Framework
 * Apache License Version 2.0, January 2004
 * Released as a part of Helsinki University
 * Software Engineering Lab in summer 2015
 */
package fi.iot.iiframework.services.domain;

import fi.iot.iiframework.domain.Sensor;
import fi.iot.iiframework.domain.SensorConfiguration;
import fi.iot.iiframework.services.GenericService;
import java.util.List;

public interface SensorConfigurationService extends GenericService<SensorConfiguration, String> {

    List<SensorConfiguration> getBy(Sensor sensor);

    List<SensorConfiguration> getBy(int from, int to);

    List<SensorConfiguration> getBy(int amount);
}
