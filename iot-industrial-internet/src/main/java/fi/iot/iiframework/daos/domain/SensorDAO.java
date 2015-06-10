/*
 * IoT - Industrial Internet Framework
 * Apache License Version 2.0, January 2004
 * Released as a part of Helsinki University
 * Software Engineering Lab in summer 2015
 */
package fi.iot.iiframework.daos.domain;

import fi.iot.iiframework.daos.GenericDAO;
import fi.iot.iiframework.domain.InformationSourceConfiguration;
import fi.iot.iiframework.domain.Sensor;
import java.util.List;

/**
 * DAO for Sensor
 */
public interface SensorDAO extends GenericDAO<Sensor, String> {

    public List<Sensor> getBy(InformationSourceConfiguration source);

    public List<Sensor> getBy(int from, int to, InformationSourceConfiguration source);
}
