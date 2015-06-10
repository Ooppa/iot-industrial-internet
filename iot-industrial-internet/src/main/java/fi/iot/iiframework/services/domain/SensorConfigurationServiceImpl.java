/*
 * IoT - Industrial Internet Framework
 * Apache License Version 2.0, January 2004
 * Released as a part of Helsinki University
 * Software Engineering Lab in summer 2015
 */
package fi.iot.iiframework.services.domain;

import fi.iot.iiframework.daos.domain.SensorConfigurationDAO;
import fi.iot.iiframework.domain.Sensor;
import fi.iot.iiframework.domain.SensorConfiguration;
import fi.iot.iiframework.services.GenericHibernateService;
import java.util.List;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Transactional
public class SensorConfigurationServiceImpl extends GenericHibernateService<SensorConfiguration, String> implements SensorConfigurationService {

    SensorConfigurationDAO configurationDAO;
    
    @Autowired
    public SensorConfigurationServiceImpl(SensorConfigurationDAO dao) {
        configurationDAO = dao;
        super.dao = dao;
    }

    @Override
    public List<SensorConfiguration> getBy(Sensor sensor) {
        return configurationDAO.getBy(sensor);
    }

    @Override
    public List<SensorConfiguration> getBy(int from, int to) {
        return configurationDAO.get(from, to);
    }

    @Override
    public List<SensorConfiguration> getBy(int amount) {
        return configurationDAO.get(0, amount);
    }
}
