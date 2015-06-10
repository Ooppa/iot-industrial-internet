/*
 * IoT - Industrial Internet Framework
 * Apache License Version 2.0, January 2004
 * Released as a part of Helsinki University
 * Software Engineering Lab in summer 2015
 */
package fi.iot.iiframework.daos.domain;

import fi.iot.iiframework.daos.GenericHibernateDAO;
import fi.iot.iiframework.domain.InformationSourceConfiguration;
import fi.iot.iiframework.domain.Sensor;
import java.util.ArrayList;
import java.util.List;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

@Repository
public class SensorDAOImpl
        extends GenericHibernateDAO<Sensor, String>
        implements SensorDAO {

    public SensorDAOImpl() {
        super();
        defaultOrder.add(Order.asc("source"));
        defaultOrder.add(Order.asc("sensorId"));
    }

    @Override
    public List<Sensor> getBy(InformationSourceConfiguration source) {
        return getBy(0, Integer.MAX_VALUE, source);
    }

    @Override
    public List<Sensor> getBy(int from, int to, InformationSourceConfiguration source) {
        List<Criterion> criterion = new ArrayList<>();
        criterion.add(Restrictions.eq("source", source));

        return findByCriteriaFromTo(from, to, criterion);
    }
}
