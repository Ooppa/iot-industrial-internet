/*
 * IoT - Industrial Internet Framework
 * Apache License Version 2.0, January 2004
 * Released as a part of Helsinki University
 * Software Engineering Lab in summer 2015
 */
package fi.iot.iiframework.services.domain;

import fi.iot.iiframework.application.TestConfig;
import fi.iot.iiframework.domain.InformationSource;
import fi.iot.iiframework.domain.Readout;
import fi.iot.iiframework.domain.Sensor;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Restrictions;
import org.hibernate.exception.ConstraintViolationException;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;

@TransactionConfiguration(defaultRollback = true)
@Transactional
@SpringApplicationConfiguration(classes = {TestConfig.class})
@RunWith(SpringJUnit4ClassRunner.class)
public class ServiceIntegrationTest {

    InformationSource i1;
    InformationSource i2;

    Sensor s1;
    Sensor s2;

    Readout r1;
    Readout r2;
    Readout r3;

    @Autowired
    private InformationSourceService sourceService;

    @Autowired
    private ReadoutService readoutService;

    @Autowired
    private SensorService sensorService;

    @Before
    public void setUp() {
        i1 = new InformationSource();
        i2 = new InformationSource();

        s1 = InformationSourceObjectProvider.provideSensor();
        s2 = InformationSourceObjectProvider.provideSensor();
        s1.setSource(i1);
        s2.setSource(i2);

        r1 = InformationSourceObjectProvider.provideReadout();
        r2 = InformationSourceObjectProvider.provideReadout();
        r3 = InformationSourceObjectProvider.provideReadout();

        r1.setValue(21.0);
        r2.setValue(23.0);
        r3.setValue(22.1);

        r1.setSensor(s1);
        r2.setSensor(s1);
        r3.setSensor(s2);

        sourceService.save(i1);
        sourceService.save(i2);
    }

    @Test
    public void informationSourceIsSaved() {
        assertEquals(2, (long) sourceService.count());
    }

    @Test
    public void sensorsCanBeFoundBySource() {
        sensorService.save(s1);
        sensorService.save(s2);
        List<Sensor> sensors = sensorService.getBy(i1);
        assertEquals(1, sensors.size());
        assertTrue(sensors.contains(s1));
        assertFalse(sensors.contains(s2));
    }

    @Test
    public void anIdIsGeneratedAutomaticallyWhenSaved() {
        sensorService.save(s1);
        assertNotNull(s1.getId());
    }

    @Test
    public void sensorCanBeRetrieved() {
        sensorService.save(s1);
        assertEquals(s1, sensorService.get(s1.getId()));
    }

    @Test
    public void allSensorsCanBeRetrieved() {
        sensorService.save(s1);
        sensorService.save(s2);
        List<Sensor> sensors = sensorService.getAll();

        assertTrue(sensors.contains(s1));
        assertTrue(sensors.contains(s2));
    }

    @Test
    public void sensorsCanBeFoundFromIndexToIndex() {
        sensorService.save(s1);
        sensorService.save(s2);
        List<Sensor> sensors = sensorService.get(0, 0);

        assertEquals(1, sensors.size());
    }

    @Test
    public void sensorsCanBeCounted() {
        sensorService.save(s1);
        sensorService.save(s2);
        assertEquals(2, (long) sensorService.count());
    }

    @Test
    public void sensorsCanBeCountedBySource() {
        sensorService.save(s1);
        sensorService.save(s2);
        assertEquals(1, (long) sensorService.countBy(i1));
    }

    @Test
    public void readoutCanBeSavedAndRetrieved() {
        sensorService.save(s1);
        readoutService.save(r1);
        assertEquals(r1, readoutService.get(r1.getId()));
    }

    @Test
    public void allReadoutsCanBeRetrieved() {
        sensorService.save(s1);
        sensorService.save(s2);
        readoutService.save(r1);
        readoutService.save(r2);
        readoutService.save(r3);
        List<Readout> readouts = readoutService.getAll();

        assertTrue(readouts.contains(r1));
        assertTrue(readouts.contains(r2));
        assertTrue(readouts.contains(r3));
    }

    @Test
    public void readoutsCanBeFoundFromIndexToIndex() {
        sensorService.save(s1);
        sensorService.save(s2);
        readoutService.save(r1);
        readoutService.save(r2);
        readoutService.save(r3);
        List<Readout> readouts = readoutService.get(1, 2);

        assertEquals(2, readouts.size());
    }

    @Test
    public void readoutsCanBeFoundBySensor() {
        sensorService.save(s1);
        sensorService.save(s2);
        readoutService.save(r1);
        readoutService.save(r2);
        readoutService.save(r3);
        List<Readout> readReadouts = readoutService.getBy(s1);

        assertTrue(readReadouts.contains(r1));
        assertTrue(readReadouts.contains(r2));
    }

    @Test
    public void readoutsNotConnectedToSensorNotReturnedWhenSearchingBySensor() {
        sensorService.save(s1);
        sensorService.save(s2);
        readoutService.save(r1);
        readoutService.save(r2);
        readoutService.save(r3);
        List<Readout> readReadouts = readoutService.getBy(s1);
        assertFalse(readReadouts.contains(r3));
    }

    @Test
    public void readoutsCanBeCounted() {
        sensorService.save(s1);
        sensorService.save(s2);
        readoutService.save(r1);
        readoutService.save(r2);
        readoutService.save(r3);
        assertEquals(3, (long) readoutService.count());
    }

    @Test
    public void readoutsCanBeCountedBySensor() {
        sensorService.save(s1);
        sensorService.save(s2);
        readoutService.save(r1);
        readoutService.save(r2);
        readoutService.save(r3);
        assertEquals(2, (long) readoutService.countBy(s1));
    }

    @Test
    public void readoutsCanBeFiltered() {
        sensorService.save(s1);
        sensorService.save(s2);
        readoutService.save(r1);
        readoutService.save(r2);
        readoutService.save(r3);
        List<Criterion> criterions = new ArrayList<>();
        Criterion c1 = Restrictions.ge("value", 22.0);
        criterions.add(c1);
        assertEquals(2, (long) readoutService.getBy(0, 2, criterions).size());
    }

    @Test(expected = ConstraintViolationException.class)
    public void uniqueConstraintExceptionIsThrown() {
        sensorService.save(s1);
        readoutService.save(r1);
        
        Readout r4 = new Readout();
        r4.setTime(r1.getTime());
        r4.setSensor(r1.getSensor());
        r4.setValue(r1.getValue());
        readoutService.save(r4);
    }
    
    
}
