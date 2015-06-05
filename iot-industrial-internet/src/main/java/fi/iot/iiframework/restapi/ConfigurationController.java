/*
 * IoT - Industrial Internet Framework
 * Apache License Version 2.0, January 2004
 * Released as a part of Helsinki University
 * Software Engineering Lab in summer 2015
 */
package fi.iot.iiframework.restapi;

import fi.iot.iiframework.application.ApplicationSettings;
import fi.iot.iiframework.restapi.exceptions.InvalidObjectException;
import fi.iot.iiframework.restapi.exceptions.InvalidParametersException;
import fi.iot.iiframework.restapi.exceptions.ResourceNotFoundException;
import fi.iot.iiframework.source.InformationSourceConfiguration;
import fi.iot.iiframework.source.InformationSourceManager;
import fi.iot.iiframework.services.source.InformationSourceConfigurationService;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("1.0/configurations")
public class ConfigurationController {

    @Autowired
    private RestAPIHelper helper;

    @Autowired
    private ApplicationSettings settings;

    @Autowired
    private InformationSourceManager informationSourceManager;

    @Autowired
    private InformationSourceConfigurationService informationSourceConfigurationService;

    @RequestMapping(value = "/informationsources/{configid}/view", produces = "application/json")
    @ResponseBody
    public InformationSourceConfiguration getInformationSource(
            @PathVariable String configid,
            @RequestParam(required = false) Map<String, String> params
    ) throws InvalidParametersException, ResourceNotFoundException {
        return (InformationSourceConfiguration) helper.returnOrException(informationSourceConfigurationService.get(configid));
    }

    @RequestMapping(
            value = "/informationsources/add",
            method = RequestMethod.POST,
            produces = "application/json",
            consumes = "application/json"
    )
    @ResponseBody
    public ResponseEntity<InformationSourceConfiguration> addInformationSource(
            @RequestBody InformationSourceConfiguration configuration,
            @RequestParam(required = false) Map<String, String> params
    ) throws InvalidParametersException, ResourceNotFoundException, InvalidObjectException {
        helper.checkIfObjectIsValid(configuration);
        informationSourceManager.createSource(configuration);
        return new ResponseEntity<>(configuration, HttpStatus.CREATED);
    }

    @RequestMapping(
            value = "/informationsources/edit",
            method = RequestMethod.POST,
            produces = "application/json",
            consumes = "application/json"
    )
    @ResponseBody
    public ResponseEntity<InformationSourceConfiguration> editInformationSource(
            @RequestBody InformationSourceConfiguration configuration,
            @RequestParam(required = false) Map<String, String> params
    ) throws InvalidParametersException, ResourceNotFoundException, InvalidObjectException {
        helper.checkIfObjectIsValid(configuration);
        informationSourceManager.updateSource(configuration);
        return new ResponseEntity<>(configuration, HttpStatus.CREATED);
    }

    @RequestMapping(
            value = "/informationsources/{configid}/delete",
            method = RequestMethod.DELETE,
            produces = "application/json"
    )
    @ResponseBody
    public ResponseEntity<InformationSourceConfiguration> deleteInformationSource(
            @PathVariable String configid,
            @RequestParam(required = false) Map<String, String> params
    ) throws InvalidParametersException, ResourceNotFoundException {
        InformationSourceConfiguration configuration
                = (InformationSourceConfiguration) helper.returnOrException(informationSourceConfigurationService.get(configid));
        informationSourceManager.removeSource(configuration.getId());
        return new ResponseEntity<>(configuration, HttpStatus.OK);
    }

    @RequestMapping(value = "/informationsources/list", produces = "application/json")
    @ResponseBody
    public List<InformationSourceConfiguration> listInformationSourcesList(
            @RequestParam(required = false) Map<String, String> params
    ) throws InvalidParametersException {
        return informationSourceConfigurationService.get(0, settings.getDefaultInformationSourcesRetrievedFromDatabase());
    }

    @RequestMapping(value = "/informationsources/list/{amount}", produces = "application/json")
    @ResponseBody
    public List<InformationSourceConfiguration> listInformationSourcesListAmount(
            @PathVariable int amount,
            @RequestParam(required = false) Map<String, String> params
    ) throws InvalidParametersException {
        helper.exceptionIfWrongLimits(0, amount);
        return informationSourceConfigurationService.get(0, amount);
    }

    @RequestMapping(value = "/informationsources/list/{to}/{from}", produces = "application/json")
    @ResponseBody
    public List<InformationSourceConfiguration> listInformationSourcesListFromTo(
            @PathVariable int from,
            @PathVariable int to,
            @RequestParam(required = false) Map<String, String> params
    ) throws InvalidParametersException {
        helper.exceptionIfWrongLimits(to, from);
        return informationSourceConfigurationService.get(to, from);
    }
}
