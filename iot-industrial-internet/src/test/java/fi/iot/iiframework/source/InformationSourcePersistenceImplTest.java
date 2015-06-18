/*
 * IoT - Industrial Internet Framework
 * Apache License Version 2.0, January 2004
 * Released as a part of Helsinki University
 * Software Engineering Lab in summer 2015
 */
package fi.iot.iiframework.source;

import fi.iot.iiframework.domain.InformationSource;
import fi.iot.iiframework.domain.Sensor;
import fi.iot.iiframework.services.domain.InformationSourceObjectProvider;
import fi.iot.iiframework.services.domain.InformationSourceService;
import fi.iot.iiframework.services.domain.ReadoutService;
import fi.iot.iiframework.services.domain.SensorService;
import java.util.List;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import org.mockito.Mock;
import static org.mockito.Mockito.*;
import static org.mockito.MockitoAnnotations.initMocks;

/**
 *
 * @author atte
 */
public class InformationSourcePersistenceImplTest {

    InformationSourcePersistence persistence;

    @Mock
    InformationSourceService mockSourceService;

    @Mock
    SensorService mockSensorService;

    @Mock
    ReadoutService mockReadoutService;

    InformationSource mockSource;

    @Before
    public void setUp() {
        initMocks(this);
        persistence = new InformationSourcePersistenceImpl(mockSourceService, mockReadoutService);

        mockSource = new InformationSource();
        InformationSourceObjectProvider.provideSensorsWithChildren(mockSource);
    }

    @Test
    public void sourcesAreSavedOnAddingSource() {
        persistence.addSource(mockSource);
        verify(mockSourceService).save(mockSource);
    }

    @Test
    public void sourcesAreUpdatedOnUpdatingSource() {
        persistence.updateSource(mockSource);
        verify(mockSourceService).save(mockSource);
    }

    @Test
    public void sourcesAreDeletedOnDeletingSource() {
        persistence.deleteSource(mockSource);
        verify(mockSourceService).delete(mockSource);
    }

}
