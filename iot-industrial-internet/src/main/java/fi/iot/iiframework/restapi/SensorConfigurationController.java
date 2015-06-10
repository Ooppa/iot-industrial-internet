/*
 * IoT - Industrial Internet Framework
 * Apache License Version 2.0, January 2004
 * Released as a part of Helsinki University
 * Software Engineering Lab in summer 2015
 */
package fi.iot.iiframework.restapi;

import fi.iot.iiframework.application.ApplicationSettings;
import fi.iot.iiframework.domain.SensorConfiguration;
import fi.iot.iiframework.domain.Sensor;
import fi.iot.iiframework.domain.SensorConfiguration;
import fi.iot.iiframework.restapi.exceptions.InvalidObjectException;
import fi.iot.iiframework.restapi.exceptions.InvalidParametersException;
import fi.iot.iiframework.restapi.exceptions.ResourceNotFoundException;
import fi.iot.iiframework.services.domain.SensorConfigurationService;
import fi.iot.iiframework.services.domain.SensorConfigurationService;
import fi.iot.iiframework.services.domain.SensorService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("1.0/configurations/sensors")
public class SensorConfigurationController {
    @Autowired
    private RestAPIHelper helper;

    @Autowired
    private ApplicationSettings settings;
    
    @Autowired
    private SensorConfigurationService sensorConfigurationService;
    
    @Autowired
    private SensorService sensorService;
    
    @RequestMapping(value = "/{sensorid}/view", produces = "application/json")
    @ResponseBody
    public SensorConfiguration getSensorConfiguration(
            @PathVariable String sensorid
    ) throws InvalidParametersException, ResourceNotFoundException {
        Sensor sensor = (Sensor) helper.returnOrException(sensorService.get(sensorid));
        return (SensorConfiguration) helper.returnOrException(sensorConfigurationService.getBy(sensor));
    }
    
    @RequestMapping(
            value = "/{sensorid}/add",
            method = RequestMethod.POST,
            produces = "application/json",
            consumes = "application/json"
    )
    @ResponseBody
    public ResponseEntity<SensorConfiguration> addSensorConfiguration(
            @PathVariable String sensorid,
            @RequestBody SensorConfiguration configuration
    ) throws InvalidParametersException, ResourceNotFoundException, InvalidObjectException {
        Sensor sensor = (Sensor) helper.returnOrException(sensorService.get(sensorid));
        configuration.setSensor(sensor);
        helper.checkIfObjectIsValid(configuration);
        sensorConfigurationService.save(configuration);
        return new ResponseEntity<>(configuration, HttpStatus.CREATED);
    }

    @RequestMapping(
            value = "/{sensorid}/edit",
            method = RequestMethod.POST,
            produces = "application/json",
            consumes = "application/json"
    )
    @ResponseBody
    public ResponseEntity<SensorConfiguration> editSensorConfiguration(
            @PathVariable String sensorid,
            @RequestBody SensorConfiguration configuration
    ) throws InvalidParametersException, ResourceNotFoundException, InvalidObjectException {
        Sensor sensor = (Sensor) helper.returnOrException(sensorService.get(sensorid));
        configuration.setSensor(sensor);
        sensor.setSensorConfiguration(configuration);
        
        sensorConfigurationService.save(configuration);
        sensorService.save(sensor);
        return new ResponseEntity<>(configuration, HttpStatus.CREATED);
    }

    @RequestMapping(
            value = "/{sensorid}/delete",
            method = RequestMethod.DELETE,
            produces = "application/json"
    )
    @ResponseBody
    public ResponseEntity<SensorConfiguration> deleteSensorConfiguration(
            @PathVariable String sensorid
    ) throws InvalidParametersException, ResourceNotFoundException {
        Sensor sensor = (Sensor) helper.returnOrException(sensorService.get(sensorid));
        
        SensorConfiguration configuration = (SensorConfiguration) helper.returnOrException(sensor.getSensorConfiguration());
        
        sensorConfigurationService.delete(sensor.getSensorConfiguration());
        return new ResponseEntity<>(configuration, HttpStatus.CREATED);
    }

    @RequestMapping(value = "/list", produces = "application/json")
    @ResponseBody
    public List<SensorConfiguration> listSensorConfigurationsList(
            
    ) throws InvalidParametersException {
        return sensorConfigurationService.get(0, settings.getDefaultSensorConfigurationsRetrievedFromDatabase());
    }

    @RequestMapping(value = "/list/{amount}", produces = "application/json")
    @ResponseBody
    public List<SensorConfiguration> listSensorConfigurationsListAmount(
            @PathVariable int amount
    ) throws InvalidParametersException {
        helper.exceptionIfWrongLimits(0, amount);
        return sensorConfigurationService.get(0, amount);
    }

    @RequestMapping(value = "/list/{to}/{from}", produces = "application/json")
    @ResponseBody
    public List<SensorConfiguration> listSensorConfigurationsListFromTo(
            @PathVariable int from,
            @PathVariable int to
    ) throws InvalidParametersException {
        helper.exceptionIfWrongLimits(to, from);
        return sensorConfigurationService.get(to, from);
    }
}
