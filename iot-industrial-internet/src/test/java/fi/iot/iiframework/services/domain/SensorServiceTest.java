/*
 * IoT - Industrial Internet Framework
 * Apache License Version 2.0, January 2004
 * Released as a part of Helsinki University
 * Software Engineering Lab in summer 2015
 */
package fi.iot.iiframework.services.domain;

import fi.iot.iiframework.application.TestConfig;
import fi.iot.iiframework.domain.InformationSourceConfiguration;
import fi.iot.iiframework.domain.Sensor;
import java.util.HashSet;
import java.util.List;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import org.junit.Before;
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
public class SensorServiceTest {

    InformationSourceConfiguration i1;
    InformationSourceConfiguration i2;

    Sensor s1;
    Sensor s2;

    @Autowired
    private SensorService service;

    @Autowired
    private InformationSourceConfigurationService sourceService;

    @Before
    public void setUp() {
        i1 = new InformationSourceConfiguration();
        i1.setSensors(new HashSet<>());
        i2 = new InformationSourceConfiguration();
        i2.setSensors(new HashSet<>());

        s1 = InformationSourceObjectProvider.provideSensor();
        s2 = InformationSourceObjectProvider.provideSensor();
        s1.setSource(i1);
        s2.setSource(i2);
        
        i1.getSensors().add(s1);
        i2.getSensors().add(s2);
    }

    @Test
    public void sensorsCanBeFoundBySource() {
        sourceService.save(i1);
        sourceService.save(i2);

        List<Sensor> sensors = service.getBy(i1);
        assertEquals(1, sensors.size());
        assertTrue(sensors.contains(s1));
        assertFalse(sensors.contains(s2));
    }

    @Test
    public void anIdIsGeneratedAutomaticallyWhenSaved() {
        service.save(s1);
        assertNotNull(s1.getId());
    }

    @Test
    public void sensorCanBeSavedAndRetrieved() {
        service.save(s1);
        assertEquals(s1, service.get(s1.getId()));
    }

    @Test
    public void allSensorsCanBeRetrieved() {
        service.save(s1);
        service.save(s2);
        
        List<Sensor> sensors = service.getAll();

        assertTrue(sensors.contains(s1));
        assertTrue(sensors.contains(s2));
    }

    @Test
    public void sensorsCanBeFoundFromIndexToIndex() {
        service.save(s1);
        service.save(s2);
        List<Sensor> sensors = service.get(0, 0);

        assertEquals(1, sensors.size());
    }

    @Test
    public void sensorsCanBeCounted() {
        service.save(s1);
        service.save(s2);
        assertEquals(2, (long) service.count());
    }

    @Test
    public void sensorsCanBeCountedBySource() {
        sourceService.save(i1);
        sourceService.save(i2);
        assertEquals(1, (long) service.countBy(i1));
    }
}
