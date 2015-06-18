/*
 * IoT - Industrial Internet Framework
 * Apache License Version 2.0, January 2004
 * Released as a part of Helsinki University
 * Software Engineering Lab in summer 2015
 */
package fi.iot.iiframework.services.domain;

import fi.iot.iiframework.services.GenericHibernateService;
import fi.iot.iiframework.domain.InformationSource;
import fi.iot.iiframework.daos.domain.InformationSourceDAO;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Transactional
public class InformationSourceServiceImpl
        extends GenericHibernateService<InformationSource, Long>
        implements InformationSourceService {

    InformationSourceDAO informationSourceDAO;
    
    @Autowired
    public InformationSourceServiceImpl(InformationSourceDAO dao) {
        super.dao = dao;
        informationSourceDAO = dao;
    }
    
    /**
     *
     * @return
     */
    @Override
    public InformationSource getWithSensors(Long id) {
        return informationSourceDAO.getWithSensors(id);
    }
}
