/*
 * IoT - Industrial Internet Framework
 * Apache License Version 2.0, January 2004
 * Released as a part of Helsinki University
 * Software Engineering Lab in summer 2015
 */
package fi.iot.iiframework.daos.domain;

import fi.iot.iiframework.daos.GenericDAO;
import fi.iot.iiframework.domain.Sensor;
import fi.iot.iiframework.domain.SensorConfiguration;
import java.util.List;

public interface SensorConfigurationDAO extends GenericDAO<SensorConfiguration, String> {

    public List<SensorConfiguration> getBy(Sensor sensor);

    public List<SensorConfiguration> getBy(int from, int to, Sensor sensor);

    public List<SensorConfiguration> getBy(int amount, Sensor sensor);
}
