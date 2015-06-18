/*
 * IoT - Industrial Internet Framework
 * Apache License Version 2.0, January 2004
 * Released as a part of Helsinki University
 * Software Engineering Lab in summer 2015
 */
package fi.iot.iiframework.restapi;

import fi.iot.iiframework.domain.InformationSource;
import fi.iot.iiframework.errors.ErrorLogger;
import fi.iot.iiframework.errors.ErrorSeverity;
import fi.iot.iiframework.errors.ErrorType;
import fi.iot.iiframework.restapi.exceptions.InvalidObjectException;
import fi.iot.iiframework.restapi.exceptions.InvalidParametersException;
import fi.iot.iiframework.restapi.exceptions.ResourceNotFoundException;
import fi.iot.iiframework.restapi.exceptions.RestAPIExceptionObject;
import fi.iot.iiframework.restapi.exceptions.TooManyRequestsException;
import fi.iot.iiframework.services.domain.InformationSourceService;
import fi.iot.iiframework.source.InformationSourceManager;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("1.0/sources")
public class InformationSourceController {

    /**
     * Keeps track of when a source was last read.
     */
    private Map<Long, Long> lastRequests = new HashMap<>();

    @Autowired
    private RestAPIHelper helper;

    @Autowired
    private InformationSourceManager informationSourceManager;

    @Autowired
    private InformationSourceService informationSourceService;

    @Secured({"ROLE_VIEWER", "ROLE_MODERATOR"})
    @RequestMapping(value = "/{sourceid}/view", produces = "application/json")
    @ResponseBody
    public InformationSource getInformationSource(
            @PathVariable long sourceid,
            @RequestParam(required = false) Map<String, String> params
    ) throws InvalidParametersException, ResourceNotFoundException {
        return (InformationSource) helper.returnOrException(informationSourceService.get(sourceid));
    }

    @Secured("ROLE_MODERATOR")
    @RequestMapping(
            value = "/add",
            method = RequestMethod.POST,
            produces = "application/json",
            consumes = "application/json"
    )
    @ResponseBody
    public ResponseEntity<InformationSource> addInformationSource(
            @RequestBody InformationSource configuration,
            @RequestParam(required = false) Map<String, String> params
    ) throws InvalidParametersException, ResourceNotFoundException, InvalidObjectException {
        helper.checkIfObjectIsValid(configuration);
        informationSourceManager.createSource(configuration);
        return new ResponseEntity<>(configuration, HttpStatus.CREATED);
    }

    @Secured("ROLE_MODERATOR")
    @RequestMapping(
            value = "/edit",
            method = RequestMethod.POST,
            produces = "application/json",
            consumes = "application/json"
    )
    @ResponseBody
    public ResponseEntity<InformationSource> editInformationSource(
            @RequestBody InformationSource configuration,
            @RequestParam(required = false) Map<String, String> params
    ) throws InvalidParametersException, ResourceNotFoundException, InvalidObjectException {
        helper.checkIfObjectIsValid(configuration);
        informationSourceManager.updateSource(configuration);
        return new ResponseEntity<>(configuration, HttpStatus.CREATED);
    }

    @Secured("ROLE_MODERATOR")
    @RequestMapping(
            value = "/{sourceid}/delete",
            method = RequestMethod.DELETE,
            produces = "application/json"
    )
    @ResponseBody
    public ResponseEntity<InformationSource> deleteInformationSource(
            @PathVariable long sourceid,
            @RequestParam(required = false) Map<String, String> params
    ) throws InvalidParametersException, ResourceNotFoundException {

        InformationSource configuration
                = (InformationSource) helper.returnOrException(informationSourceService.get(sourceid));

        informationSourceManager.removeSource(sourceid);
        return new ResponseEntity<>(configuration, HttpStatus.OK);
    }

    @Secured({"ROLE_VIEWER", "ROLE_MODERATOR"})
    @RequestMapping(value = "/list", produces = "application/json")
    @ResponseBody
    public List<InformationSource> listInformationSourcesList(
            @RequestParam(required = false) Map<String, String> params
    ) throws InvalidParametersException {
        return informationSourceService.get(0, 10);
    }

    @Secured({"ROLE_VIEWER", "ROLE_MODERATOR"})
    @RequestMapping(value = "/list/{amount}", produces = "application/json")
    @ResponseBody
    public List<InformationSource> listInformationSourcesListAmount(
            @PathVariable int amount,
            @RequestParam(required = false) Map<String, String> params
    ) throws InvalidParametersException {
        helper.exceptionIfWrongLimits(0, amount);
        return informationSourceService.get(0, amount);
    }

    @Secured({"ROLE_VIEWER", "ROLE_MODERATOR"})
    @RequestMapping(value = "/list/{to}/{from}", produces = "application/json")
    @ResponseBody
    public List<InformationSource> listInformationSourcesListFromTo(
            @PathVariable int from,
            @PathVariable int to,
            @RequestParam(required = false) Map<String, String> params
    ) throws InvalidParametersException {
        helper.exceptionIfWrongLimits(to, from);
        return informationSourceService.get(to, from);
    }

    @Secured({"ROLE_MODERATOR"})
    @RequestMapping(value = "/{sourceid}/read", produces = "application/json")
    @ResponseBody
    public ResponseEntity<SuccessObject> readInformationSource(
            @PathVariable long sourceid
    ) throws TooManyRequestsException {
        if (lastRequests.containsKey(sourceid) && lastRequestTooClose(sourceid)) {
            ErrorLogger.log(
                    ErrorType.NOT_ACCEPTED,
                    ErrorSeverity.LOW,
                    "Too many forced requests for a single source.",
                    "Read request can only be issued every 10 seconds for a single source"
            );
            throw new TooManyRequestsException();
        }

        informationSourceManager.readSource(sourceid);
        lastRequests.put(sourceid, System.currentTimeMillis());
        return new ResponseEntity<>(new SuccessObject("Source [" + sourceid + "] was read successfully."), HttpStatus.OK);
    }

    private boolean lastRequestTooClose(long sourceid) {
        return System.currentTimeMillis() - lastRequests.get(sourceid) < 10000;
    }

    public void setInformationSourceService(InformationSourceService informationSourceService) {
        this.informationSourceService = informationSourceService;
    }

    public void setInformationSourceManager(InformationSourceManager informationSourceManager) {
        this.informationSourceManager = informationSourceManager;
    }

    public void setRestAPIHelper(RestAPIHelper helper) {
        this.helper = helper;
    }
}
