/*
 * IoT - Industrial Internet Framework
 * Apache License Version 2.0, January 2004
 * Released as a part of Helsinki University
 * Software Engineering Lab in summer 2015
 */
package fi.iot.iiframework.source;

import fi.iot.iiframework.domain.InformationSourceConfiguration;
import fi.iot.iiframework.application.TestConfig;
import fi.iot.iiframework.services.domain.InformationSourceConfigurationService;
import fi.iot.iiframework.services.domain.SensorService;
import java.util.Map;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import static org.mockito.MockitoAnnotations.initMocks;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = {TestConfig.class})
public class InformationSourceManagerTest {

    @Autowired
    private InformationSourceManagerImpl manager;

    @Mock
    private InformationSourceConfigurationService mockConfigService;

    @Mock
    private SensorService mockService;

    private InformationSourceConfiguration config;

    @Before
    public void setUp() {
        initMocks(this);
        manager.setConfigService(mockConfigService);
        manager.setSensorService(mockService);

        config = new InformationSourceConfiguration();
        config.setId("1");
        config.setName("Example");
        config.setUrl("http://axwikstr.users.cs.helsinki.fi/data.xml");
        config.setType(InformationSourceType.XML);
        config.setActive(false);
        config.setReadFrequency(0);
        manager.createSource(config);
    }

    @Test
    public void aNewInformationSourceIsCreatedSuccesfully() {
        Map<String, InformationSource> sources = manager.getSources();
        assertEquals(config, sources.get("1").getConfig());
    }

    @Test
    public void whenCreatedTheConfigurationIsSavedInDB() {
        Mockito.verify(mockConfigService).save(config);
    }

    @Test
    public void updateUpdatesTheConfigurationInDatabase() {
        config.setName("Another example");
        manager.updateSource(config);
        Mockito.verify(mockConfigService, Mockito.times(2)).save(config);
    }
    
    @Test
    public void removeRemovesTheConfiguration() {
        manager.removeSource(config.getId());
        assertTrue(manager.getSources().isEmpty());
        Mockito.verify(mockConfigService, Mockito.times(1)).delete(config);
    }

}
