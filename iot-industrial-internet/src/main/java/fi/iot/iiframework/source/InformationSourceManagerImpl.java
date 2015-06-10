/*
 * IoT - Industrial Internet Framework
 * Apache License Version 2.0, January 2004
 * Released as a part of Helsinki University
 * Software Engineering Lab in summer 2015
 */
package fi.iot.iiframework.source;

import fi.iot.iiframework.domain.InformationSourceConfiguration;
import fi.iot.iiframework.services.domain.InformationSourceConfigurationService;
import fi.iot.iiframework.services.domain.SensorService;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 *
 * Creates and manages objects that represent external data sources
 */
@Component
public class InformationSourceManagerImpl implements InformationSourceManager {

    private final Map<String, InformationSource> sources;
    
    @Autowired
    private SensorService sensorService;
    @Autowired
    private InformationSourceConfigurationService configService;

    public InformationSourceManagerImpl() {
        this.sources = new HashMap<>();
    }

    @PostConstruct
    public void loadConfigFromDB() {
        List<InformationSourceConfiguration> configs = configService.getAll();
        configs.forEach(c -> createSource(c));
    }

    @Override
    public void createSource(InformationSourceConfiguration config) {
        InformationSource source = new InformationSourceImpl(config, sensorService);
        configService.save(config);
        sources.put(config.getId(), source);
    }

    @Override
    public void removeSource(String id) {
        configService.delete(sources.get(id).getConfig());
        sources.get(id).close();
        sources.remove(id);
    }

    @Override
    public void updateSource(InformationSourceConfiguration config) {
        sources.get(config.getId()).setConfig(config);
        configService.save(config);
    }
    
    @Override
    public boolean readSource(String id) {
        InformationSource source = sources.get(id);
        return source.readAndWrite();
    }

    public Map<String, InformationSource> getSources() {
        return sources;
    }

    public void setSensorService(SensorService sensorService) {
        this.sensorService = sensorService;
    }

    public void setConfigService(InformationSourceConfigurationService configService) {
        this.configService = configService;
    }

}
