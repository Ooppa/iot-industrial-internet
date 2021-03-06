/*
 * IoT - Industrial Internet Framework
 * Apache License Version 2.0, January 2004
 * Released as a part of Helsinki University
 * Software Engineering Lab in summer 2015
 */
package fi.iot.iiframework.services.domain;

import fi.iot.iiframework.domain.Readout;
import fi.iot.iiframework.daos.domain.ReadoutDAO;
import fi.iot.iiframework.domain.Sensor;
import fi.iot.iiframework.mutator.MarkReadoutAsErronousIfValueIs;
import fi.iot.iiframework.mutator.ValueCondition;
import fi.iot.iiframework.services.GenericHibernateService;
import java.util.Collection;
import java.util.List;
import javax.transaction.Transactional;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Transactional
public class ReadoutServiceImpl
        extends GenericHibernateService<Readout, Long>
        implements ReadoutService {

    private final ReadoutDAO readoutDAO;

    @Autowired
    public ReadoutServiceImpl(ReadoutDAO dao) {
        readoutDAO = dao;
        super.dao = dao;
    }

    @Override
    public List<Readout> getBy(int from, int to, Sensor sensor) {
        return readoutDAO.getBy(from, to, sensor);
    }

    @Override
    public List<Readout> getBy(Sensor sensor) {
        return readoutDAO.getBy(sensor);
    }

    @Override
    public Long countBy(Sensor sensor) {
        return countByCriteria(
                buildCriterionList(Restrictions.eq("sensor", sensor))
        );
    }

    @Override
    public void save(Collection<Readout> readouts) {
        int i = 0;
        for (Readout readout : readouts) {
            if (!readoutDAO.isUnique(readout)) {
                continue;
            }
            dao.save(readout);
            mutateReadout(readout);
            if (i % BATCHSIZE == 0) {
                dao.flush();
                dao.clear();
            }
        }
    }

    /**
     * Based on sensor-configuration, mark erronous readouts.
     *
     * @param sensor
     */
    private void mutateReadout(Readout readout) {
        new MarkReadoutAsErronousIfValueIs(ValueCondition.HIGHER_THAN)
                .mutateReadout(readout);
        new MarkReadoutAsErronousIfValueIs(ValueCondition.LOWER_THAN)
                .mutateReadout(readout);
    }
}
