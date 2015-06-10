/*
 * IoT - Industrial Internet Framework
 * Apache License Version 2.0, January 2004
 * Released as a part of Helsinki University
 * Software Engineering Lab in summer 2015
 */
package fi.iot.iiframework.services.domain;

import fi.iot.iiframework.application.TestConfig;
import fi.iot.iiframework.domain.Readout;
import fi.iot.iiframework.domain.Sensor;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Restrictions;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.Ignore;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;

@Ignore
@TransactionConfiguration(defaultRollback = true)
@Transactional
@SpringApplicationConfiguration(classes = {TestConfig.class})
@RunWith(SpringJUnit4ClassRunner.class)
public class ReadoutServiceTest {

    @Autowired
    private ReadoutService service;

    Readout r1;
    Readout r2;
    Readout r3;

    Sensor s1;
    Sensor s2;

    @Before
    public void setUp() {
        s1 = InformationSourceObjectProvider.provideSensor();
        s2 = InformationSourceObjectProvider.provideSensor();

        r1 = InformationSourceObjectProvider.provideReadout();
        r2 = InformationSourceObjectProvider.provideReadout();
        r3 = InformationSourceObjectProvider.provideReadout();
        r1.setSensor(s1);
        r2.setSensor(s1);
        r3.setSensor(s2);

        r1.setValue(21.0);
        r2.setValue(23.0);
        r3.setValue(22.1);

        service.save(r1);
        service.save(r2);
        service.save(r3);
    }

    @Test
    public void anIdIsGeneratedAutomaticallyWhenSaved() {
        service.save(r1);
        assertNotNull(r1.getId());
    }

    @Test
    public void readoutCanBeSavedAndRetrieved() {
        service.save(r1);
        assertEquals(r1, service.get(r1.getId()));
    }

    @Test
    public void allReadoutsCanBeRetrieved() {
        List<Readout> readouts = service.getAll();

        assertTrue(readouts.contains(r1));
        assertTrue(readouts.contains(r2));
        assertTrue(readouts.contains(r3));
    }

    @Test
    public void readoutsCanBeFoundFromIndexToIndex() {
        List<Readout> readouts = service.get(1, 2);

        assertEquals(2, readouts.size());
    }

    @Test
    public void readoutsCanBeFoundBySensor() {
        List<Readout> readReadouts = service.getBy(s1);

        System.out.println(Arrays.toString(readReadouts.toArray()));
        System.out.println(r1);
        System.out.println(r2);
        assertTrue(readReadouts.contains(r1));
        assertTrue(readReadouts.contains(r2));
    }

    @Test
    public void readoutsNotConnectedToSensorNotReturnedWhenSearchingBySensor() {
        List<Readout> readReadouts = service.getBy(s1);
        assertFalse(readReadouts.contains(r3));
    }

    @Test
    public void readoutsCanBeCounted() {
        assertEquals(3, (long) service.count());
    }

    @Test
    public void readoutsCanBeCountedBySensor() {
        assertEquals(2, (long) service.countBy(s1));
    }

    @Test
    public void readoutsCanBeFiltered() {
        List<Criterion> criterions = new ArrayList<>();
        Criterion c1 = Restrictions.ge("value", 22.0);
        criterions.add(c1);
        assertEquals(2, (long) service.getBy(0, 2, criterions).size());
    }

    @Test
    public void objectsThatAreEqualShouldBeOverwrittenInTheDatabase() {
        Readout r4 = r1;
        r4.setUnit(r1.getUnit());
        r4.setTime(r1.getTime());
        r4.setQuantity(r1.getQuantity());
        r4.setSensor(r1.getSensor());
        r4.setValue(r1.getValue());
        service.save(r4);
        assertEquals(3, (long) service.count());
    }
}
