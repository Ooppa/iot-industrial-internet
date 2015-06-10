/*
 * IoT - Industrial Internet Framework
 * Apache License Version 2.0, January 2004
 * Released as a part of Helsinki University
 * Software Engineering Lab in summer 2015
 */
package fi.iot.iiframework.daos.domain;

import fi.iot.iiframework.daos.GenericHibernateDAO;
import fi.iot.iiframework.domain.Sensor;
import fi.iot.iiframework.domain.SensorConfiguration;
import java.util.ArrayList;
import java.util.List;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;


@Repository
public class SensorConfigurationDAOImpl extends GenericHibernateDAO<SensorConfiguration, String> implements SensorConfigurationDAO {

    @Override
    public List<SensorConfiguration> getBy(Sensor sensor) {
        return getBy(0, Integer.MAX_VALUE, sensor);
    }

    @Override
    public List<SensorConfiguration> getBy(int from, int to, Sensor sensor) {
        List<Criterion> criterion = new ArrayList<>();
        criterion.add(Restrictions.eq("sensor", sensor));
        return findByCriteriaFromTo(from, to, criterion);
    }

    @Override
    public List<SensorConfiguration> getBy(int amount, Sensor sensor) {
        return getBy(0, amount-1, sensor);
    }

}