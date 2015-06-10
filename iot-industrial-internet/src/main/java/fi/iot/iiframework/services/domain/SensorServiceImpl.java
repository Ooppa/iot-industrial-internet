/*
 * IoT - Industrial Internet Framework
 * Apache License Version 2.0, January 2004
 * Released as a part of Helsinki University
 * Software Engineering Lab in summer 2015
 */
package fi.iot.iiframework.services.domain;

import fi.iot.iiframework.domain.Sensor;
import fi.iot.iiframework.daos.domain.SensorDAO;
import fi.iot.iiframework.domain.InformationSourceConfiguration;
import fi.iot.iiframework.services.GenericHibernateService;
import java.util.List;
import javax.transaction.Transactional;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Transactional
public class SensorServiceImpl
        extends GenericHibernateService<Sensor, String>
        implements SensorService {

    SensorDAO sensorDAO;

    @Autowired
    public SensorServiceImpl(SensorDAO dao) {
        sensorDAO = dao;
        super.dao = dao;
    }

    @Override
    public List<Sensor> getBy(InformationSourceConfiguration source) {
        return getBy(0, Integer.MAX_VALUE, source);
    }

    @Override
    public List<Sensor> getBy(int from, int to, InformationSourceConfiguration source) {
        return sensorDAO.getBy(from, to, source);
    }

    @Override
    public Long countBy(InformationSourceConfiguration source) {
        return sensorDAO.countByCriteria(
                buildCriterionList(Restrictions.eq("source", source))
        );
    }

}
