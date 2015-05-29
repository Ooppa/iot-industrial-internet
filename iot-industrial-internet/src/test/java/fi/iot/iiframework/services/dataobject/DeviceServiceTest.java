/*
 * IoT - Industrial Internet Framework
 * Apache License Version 2.0, January 2004
 * Released as a part of Helsinki University
 * Software Engineering Lab in summer 2015
 */
package fi.iot.iiframework.services.dataobject;

import fi.iot.iiframework.application.TestConfig;
import fi.iot.iiframework.dataobject.DataSourceObject;
import fi.iot.iiframework.dataobject.Device;
import fi.iot.iiframework.services.dataobject.DeviceService;
import java.util.List;
import java.util.UUID;
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
public class DeviceServiceTest {

    DataSourceObject dso1;
    DataSourceObject dso2;

    Device d1;
    Device d2;

    @Autowired
    private DeviceService service;

    @Before
    public void setUp() {
        dso1 = DataObjectProvider.provideDataObject();
        dso2 = DataObjectProvider.provideDataObject();

        d1 = DataObjectProvider.provideDevice();
        d2 = DataObjectProvider.provideDevice();
        d1.setSource(dso1);
        d2.setSource(dso2);
        
        service.save(d1);
        service.save(d2);
    }

    @Test
    public void devicesCanBeFoundByDataSourceObject() {
        List<Device> devices = service.getBy(dso1);
        assertTrue(devices.contains(d1));
        assertFalse(devices.contains(d2));
    }
}