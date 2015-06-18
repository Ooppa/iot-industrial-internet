/*
 * IoT - Industrial Internet Framework
 * Apache License Version 2.0, January 2004
 * Released as a part of Helsinki University
 * Software Engineering Lab in summer 2015
 */
package fi.iot.iiframework.controllers;

import com.google.common.base.Throwables;
import fi.iot.iiframework.errors.ErrorLogger;
import fi.iot.iiframework.errors.ErrorSeverity;
import fi.iot.iiframework.errors.ErrorType;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.boot.autoconfigure.web.ErrorController;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
class CustomErrorController implements ErrorController {

    private static final String PATH = "/error";

    @RequestMapping(value = PATH)
    public String customError(HttpServletRequest request, HttpServletResponse response, Model model) {
        Integer statusCode = (Integer) request.getAttribute("javax.servlet.error.status_code");
        Throwable throwable = (Throwable) request.getAttribute("javax.servlet.error.exception");
        String exceptionMessage = getExceptionMessage(throwable, statusCode);

        // Log everything except 404 (not found) and 401 (unauthorized) errors
        logIfNot404or401Error(statusCode, exceptionMessage, throwable, getURI(request));

        model.addAttribute("exceptionMessage", exceptionMessage);
        model.addAttribute("url", getURI(request));
        model.addAttribute("statuscode", statusCode);
        model.addAttribute("throwable", throwable);

        return "errorpage";
    }

    private void logIfNot404or401Error(Integer statusCode, String exceptionMessage, Throwable throwable, String uri) {
        String trace = "No stacktrace.";

        if (throwable != null) {
            trace = throwable.getCause().toString();
        }

        if (statusCode != 404 || statusCode != 401) {
            ErrorLogger.log(
                    ErrorType.HTTP_ERROR,
                    ErrorSeverity.NOTIFICATION,
                    "User experienced an error (" + exceptionMessage + " - " + statusCode + ") :" + trace,
                    "Occured at: " + uri);
        }
    }

    private String getURI(HttpServletRequest request) {
        if (request.getAttribute("javax.servlet.error.request_uri") == null) {
            return "Unknown URI";
        }

        return (String) request.getAttribute("javax.servlet.error.request_uri");
    }

    private String getExceptionMessage(Throwable throwable, Integer statusCode) {
        if (throwable != null) {
            return Throwables.getRootCause(throwable).getMessage();
        }
        HttpStatus httpStatus = HttpStatus.valueOf(statusCode);
        return httpStatus.getReasonPhrase();
    }

    @Override
    public String getErrorPath() {
        return PATH;
    }
}
