/*
 * IoT - Industrial Internet Framework
 * Apache License Version 2.0, January 2004
 * Released as a part of Helsinki University
 * Software Engineering Lab in summer 2015
 */
package fi.iot.iiframework.services.errors;

import fi.iot.iiframework.errors.SysError;
import fi.iot.iiframework.daos.errors.ErrorDAO;
import fi.iot.iiframework.services.GenericHibernateService;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Transactional
public class ErrorServiceImpl extends GenericHibernateService<SysError, Long> implements ErrorService {

    @Autowired
    public ErrorServiceImpl(ErrorDAO dao) {
        super.dao = dao;
    }

}
