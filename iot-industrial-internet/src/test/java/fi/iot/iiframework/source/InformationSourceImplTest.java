/*
 * IoT - Industrial Internet Framework
 * Apache License Version 2.0, January 2004
 * Released as a part of Helsinki University
 * Software Engineering Lab in summer 2015
 */
package fi.iot.iiframework.source;

import fi.iot.iiframework.domain.InformationSourceConfiguration;
import fi.iot.iiframework.domain.Sensor;
import fi.iot.iiframework.readers.InformationSourceReader;
import fi.iot.iiframework.services.domain.InformationSourceObjectProvider;
import fi.iot.iiframework.services.domain.SensorService;
import java.io.IOException;
import java.util.List;
import javax.xml.bind.JAXBException;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;
import org.mockito.Matchers;
import org.mockito.Mock;
import static org.mockito.Mockito.*;
import org.mockito.MockitoAnnotations;

public class InformationSourceImplTest {

    private InformationSource source;
    private InformationSourceConfiguration config;
    @Mock
    private SensorService mockService;
    @Mock
    private ReadScheduler mockScheduler;
    @Mock
    private InformationSourceReader mockReader;
    
    private List<Sensor> examples;

    @Before
    public void setUp() throws JAXBException, IOException {
        MockitoAnnotations.initMocks(this);
        config = new InformationSourceConfiguration();
        config.setReadFrequency(0);
        config.setActive(false);
        config.setReadFrequency(11);
        config.setName("test");
        config.setType(InformationSourceType.XML);
        config.setUrl("http://t-teesalmi.users.cs.helsinki.fi/MafiaTools/source.xml");

        source = new InformationSourceImpl(config, mockService);
        source.setReader(mockReader);
        source.setScheduler(mockScheduler);
        
        examples = InformationSourceObjectProvider.provideSensorsWithChildren();
        
        when(mockReader.read(Matchers.anyString())).thenReturn(examples);
    }

    @Test
    public void readReadsSuccesfullyFromReader() throws JAXBException, IOException {
        examples.forEach(s ->{
            assertTrue(source.read().contains(s));
        });
    }
    
    @Test
    public void readAndWriteWritesTheReadObjectToDatabase() {
        source.readAndWrite();
        verify(mockService).save(examples);
    }
}
