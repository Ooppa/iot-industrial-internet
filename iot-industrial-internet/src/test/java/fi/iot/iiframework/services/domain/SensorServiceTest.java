/*
 * IoT - Industrial Internet Framework
 * Apache License Version 2.0, January 2004
 * Released as a part of Helsinki University
 * Software Engineering Lab in summer 2015
 */
package fi.iot.iiframework.services.domain;

import fi.iot.iiframework.application.TestConfig;
import fi.iot.iiframework.domain.DataSourceObject;
import fi.iot.iiframework.domain.Device;
import fi.iot.iiframework.domain.Sensor;
import fi.iot.iiframework.services.dataobject.SensorService;
import java.util.List;
import static org.junit.Assert.assertFalse;
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

    Device d1;
    Device d2;
    
    Sensor s1;
    Sensor s2;

    @Autowired
    private SensorService service;

    @Before
    public void setUp() {
        DataSourceObject dso = DataObjectProvider.provideDataObject();

        d1 = DataObjectProvider.provideDevice();
        d2 = DataObjectProvider.provideDevice();
        d1.setSource(dso);
        d2.setSource(dso);

        s1 = DataObjectProvider.provideSensor();
        s2 = DataObjectProvider.provideSensor();
        s1.setDevice(d1);
        s2.setDevice(d2);

        service.save(s1);
        service.save(s2);
    }
    
    @Test
    public void sensorsCanBeFoundByDevice() {
        List<Sensor> sensors = service.getBy(d1);
        assertTrue(sensors.contains(s1));
        assertFalse(sensors.contains(s2));
    }

}