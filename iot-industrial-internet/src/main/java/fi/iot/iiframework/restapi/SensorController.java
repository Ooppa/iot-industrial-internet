/*
 * IoT - Industrial Internet Framework
 * Apache License Version 2.0, January 2004
 * Released as a part of Helsinki University
 * Software Engineering Lab in summer 2015
 */
package fi.iot.iiframework.restapi;

import fi.iot.iiframework.domain.InformationSource;
import fi.iot.iiframework.domain.Sensor;
import fi.iot.iiframework.restapi.exceptions.InvalidObjectException;
import fi.iot.iiframework.restapi.exceptions.InvalidParametersException;
import fi.iot.iiframework.restapi.exceptions.ResourceNotFoundException;
import fi.iot.iiframework.services.domain.InformationSourceService;
import fi.iot.iiframework.services.domain.SensorService;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("1.0/sensors")
public class SensorController {

    @Autowired
    private InformationSourceService sourceService;

    @Autowired
    private SensorService sensorService;

    @Autowired
    private RestAPIHelper helper;

    @Secured({"ROLE_VIEWER", "ROLE_MODERATOR"})
    @RequestMapping(value = "/{sensorid}/view", produces = "application/json")
    @ResponseBody
    public Sensor getSensor(
            @PathVariable long sensorid,
            @RequestParam(required = false) Map<String, String> params
    ) throws ResourceNotFoundException {
        return (Sensor) helper.returnOrException(sensorService.get(sensorid));
    }

    @Secured("ROLE_MODERATOR")
    @RequestMapping(
            value = "/{sensorid}/edit",
            method = RequestMethod.POST,
            produces = "application/json",
            consumes = "application/json"
    )
    @ResponseBody
    public ResponseEntity<Sensor> editSensor(
            @PathVariable long sensorid,
            @RequestBody Sensor sensor
    ) throws InvalidParametersException, ResourceNotFoundException, InvalidObjectException {
        helper.returnOrException(sensorService.get(sensorid));
        sensorService.update(sensor);
        return new ResponseEntity<>(sensor, HttpStatus.CREATED);
    }

    @Secured({"ROLE_VIEWER", "ROLE_MODERATOR"})
    @RequestMapping(value = "/{sourceid}/list", produces = "application/json")
    @ResponseBody
    public List<Sensor> listSensors(
            @PathVariable long sourceid,
            @RequestParam(required = false) Map<String, String> params
    ) throws ResourceNotFoundException {
        InformationSource source = (InformationSource) helper.returnOrException(sourceService.get(sourceid));
        return sensorService.getBy(source);
    }

    @Secured({"ROLE_VIEWER", "ROLE_MODERATOR"})
    @RequestMapping(value = "/{sourceid}/list/{amount}", produces = "application/json")
    @ResponseBody
    public List<Sensor> listSensorsAmount(
            @PathVariable long sourceid,
            @PathVariable int amount,
            @RequestParam(required = false) Map<String, String> params
    ) throws ResourceNotFoundException, InvalidParametersException {
        InformationSource source
                = (InformationSource) helper.returnOrException(sourceService.get(sourceid));
        helper.exceptionIfWrongLimits(0, amount);
        return sensorService.getBy(0, amount, source);
    }

    @Secured({"ROLE_VIEWER", "ROLE_MODERATOR"})
    @RequestMapping(value = "/{sourceid}/list/{from}/{to}", produces = "application/json")
    @ResponseBody
    public List<Sensor> listSensorsFromTo(
            @PathVariable long sourceid,
            @PathVariable int from,
            @PathVariable int to,
            @RequestParam(required = false) Map<String, String> params
    ) throws InvalidParametersException, ResourceNotFoundException {
        InformationSource source
                = (InformationSource) helper.returnOrException(sourceService.get(sourceid));
        helper.exceptionIfWrongLimits(to, from);
        return sensorService.getBy(from, to, source);
    }
}
