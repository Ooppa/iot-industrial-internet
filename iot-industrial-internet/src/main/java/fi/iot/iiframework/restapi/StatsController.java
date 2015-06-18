/*
 * IoT - Industrial Internet Framework
 * Apache License Version 2.0, January 2004
 * Released as a part of Helsinki University
 * Software Engineering Lab in summer 2015
 */
package fi.iot.iiframework.restapi;

import fi.iot.iiframework.errors.ErrorSeverity;
import fi.iot.iiframework.errors.ErrorType;
import fi.iot.iiframework.source.InformationSourceType;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("1.0/stats")
@Secured({"ROLE_VIEWER", "ROLE_MODERATOR"})
public class StatsController {

    @RequestMapping(value = "/informationsource/types", produces = "application/json")
    @ResponseBody
    public InformationSourceType[] listReaderTypes() {
        return InformationSourceType.values();
    }

    @RequestMapping(value = "/error/types", produces = "application/json")
    @ResponseBody
    public ErrorType[] listErrorTypes() {
        return ErrorType.values();
    }

    @RequestMapping(value = "/severity/types", produces = "application/json")
    @ResponseBody
    public ErrorSeverity[] listErrorSeverityTypes() {
        return ErrorSeverity.values();
    }

}
