/*
 * IoT - Industrial Internet Framework
 * Apache License Version 2.0, January 2004
 * Released as a part of Helsinki University
 * Software Engineering Lab in summer 2015
 */
package fi.iot.iiframework.daos.dataobject;

import fi.iot.iiframework.daos.GenericDAO;
import fi.iot.iiframework.dataobject.Readout;
import fi.iot.iiframework.dataobject.Sensor;
import java.util.List;

/**
 * DAO for Readout
 */
public interface ReadoutDAO extends GenericDAO<Readout, Long> {
    public List<Readout> getBy(Sensor sensor);
    public List<Readout> getBy(int from, int to, Sensor sensor);
    public List<Readout> getBy(int amount, Sensor sensor);
}
