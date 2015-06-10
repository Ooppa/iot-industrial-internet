/*
 * IoT - Industrial Internet Framework
 * Apache License Version 2.0, January 2004
 * Released as a part of Helsinki University
 * Software Engineering Lab in summer 2015
 */
package fi.iot.iiframework.restapi;

import java.util.List;
import java.util.Map;
import fi.iot.iiframework.application.ApplicationSettings;
import fi.iot.iiframework.domain.InformationSourceConfiguration;
import fi.iot.iiframework.domain.Sensor;
import fi.iot.iiframework.restapi.exceptions.InvalidParametersException;
import fi.iot.iiframework.restapi.exceptions.ResourceNotFoundException;
import fi.iot.iiframework.services.domain.InformationSourceConfigurationService;
import fi.iot.iiframework.services.domain.SensorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("1.0/sensors")
public class SensorController {
    
    @Autowired
    private InformationSourceConfigurationService sourceService;
    
    @Autowired
    private SensorService sensorservice;
    
    @Autowired
    private RestAPIHelper helper;
    
    @Autowired
    private ApplicationSettings settings;
    
    @RequestMapping(value = "/{sensorid}/view", produces = "application/json")
    @ResponseBody
    public Sensor getSensor(
            @PathVariable String sensorid,
            @RequestParam(required = false) Map<String, String> params
    ) throws ResourceNotFoundException {
        return (Sensor) helper.returnOrException(sensorservice.get(sensorid));
    }

    @RequestMapping(value = "/{sourceid}/list", produces = "application/json")
    @ResponseBody
    public List<Sensor> listSensors(
            @PathVariable String sourceid,
            @RequestParam(required = false) Map<String, String> params
    ) throws ResourceNotFoundException {
        InformationSourceConfiguration source = 
                (InformationSourceConfiguration) helper.returnOrException(sourceService.get(sourceid));
        return sensorservice.getBy(0, settings.getDefaultAmountOfSensorsRetrievedFromDatabase(), source);
    }

    @RequestMapping(value = "/{sourceid}/list/{amount}", produces = "application/json")
    @ResponseBody
    public List<Sensor> listSensorsAmount(
            @PathVariable String sourceid,
            @PathVariable int amount,
            @RequestParam(required = false) Map<String, String> params
    ) throws ResourceNotFoundException, InvalidParametersException {
        InformationSourceConfiguration source = 
                (InformationSourceConfiguration) helper.returnOrException(sourceService.get(sourceid));
        helper.exceptionIfWrongLimits(0, amount);
        return sensorservice.getBy(0, amount, source);
    }

    @RequestMapping(value = "/{sourceid}/list/{from}/{to}", produces = "application/json")
    @ResponseBody
    public List<Sensor> listSensorsFromTo(
            @PathVariable String sourceid,
            @PathVariable int from,
            @PathVariable int to,
            @RequestParam(required = false) Map<String, String> params
    ) throws InvalidParametersException, ResourceNotFoundException {
        InformationSourceConfiguration source = 
                (InformationSourceConfiguration) helper.returnOrException(sourceService.get(sourceid));
        helper.exceptionIfWrongLimits(to, from);
        return sensorservice.getBy(from, to, source);
    }
}
