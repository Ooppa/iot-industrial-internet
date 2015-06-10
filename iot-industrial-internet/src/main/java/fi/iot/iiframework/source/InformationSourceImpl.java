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
import fi.iot.iiframework.readers.XMLReader;
import fi.iot.iiframework.services.domain.SensorService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;

public final class InformationSourceImpl implements InformationSource {

    /**
     * Configuration for this source.
     */
    private InformationSourceConfiguration config;
    /**
     * Reader used to read the server information.
     */
    private InformationSourceReader reader;
    /**
     * Scheduler that schedules the read operation based on config.
     */
    private ReadScheduler scheduler;
    /**
     * Service for database transactions.
     */
    @Autowired
    private final SensorService service;

    public InformationSourceImpl(InformationSourceConfiguration config, SensorService service) {
        this.config = config;
        this.service = service;
        this.scheduler = new ReadSchedulerImpl();
        initReader();
        schedule();
    }

    /**
     * Initialize the type of reader this class will use.
     */
    private void initReader() {
        switch (config.getType()) {
            case XML:
                this.reader = new XMLReader();
                break;
            default:
                throw new AssertionError(config.getType().name());
        }

    }

    /**
     * Initialize scheduler.
     */
    private void schedule() {
        scheduler.cancel();
        if (config.isActive() && config.getReadFrequency() > 0) {
            scheduler.schedule(config.getReadFrequency(), this::readAndWrite);
        }
    }

    /**
     * Ensure that scheduler and reader match the configuration.
     */
    private void update() {
        initReader();
        schedule();
    }

    @Override
    public boolean readAndWrite() {
        List<Sensor> sensors = read();
        if (sensors == null){
            return false;
        }
        sensors.forEach(s -> s.setSource(config));
        service.save(sensors);
        return true;
    }

    @Override
    public List<Sensor> read() {
        List<Sensor> isobj = reader.read(config.getUrl());
        return isobj;
    }

    @Override
    public InformationSourceConfiguration getConfig() {
        return config;
    }

    @Override
    public void setConfig(InformationSourceConfiguration config) {
        this.config = config;
        update();
    }

    @Override
    public void close() {
        scheduler.cancel();
    }

    @Override
    public void setReader(InformationSourceReader reader) {
        this.reader = reader;
    }
    
    @Override
    public void setScheduler(ReadScheduler scheduler) {
        this.scheduler = scheduler;
    }

}
