/*
 * IoT - Industrial Internet Framework
 * Apache License Version 2.0, January 2004
 * Released as a part of Helsinki University
 * Software Engineering Lab in summer 2015
 */
package fi.iot.iiframework.errors;

import fi.iot.iiframework.services.errors.ErrorService;
import java.util.List;
import javax.annotation.PostConstruct;
import org.apache.commons.lang3.StringEscapeUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Create and save new errors to database.
 *
 */
@Component
public class ErrorLogger {

    private static ErrorService gService;

    @Autowired
    private ErrorService gServiceAW;

    /**
     * Class needed to allow using Autowired -annotation in a static class
     */
    @PostConstruct
    public void ErrorLogger() {
        gService = this.gServiceAW;
    }

    /**
     * Creates a new error and calls newError to save it to database
     *
     * @param type ErrorType of error
     * @param severity ErrorSeverity of error
     * @param desc Description of error
     * @param location Location of the error
     */
    public static void log(ErrorType type, ErrorSeverity severity, String desc, String location) {
        SysError error = new SysError(type, severity, sanitizeString(desc), sanitizeString(location));
        saveError(error);
    }

    /**
     * Creates a new error and calls newError to save it to database with
     * location obtained from the stack trace.
     *
     * @param type ErrorType of error
     * @param severity ErrorSeverity of error
     *
     * @param desc Optional description
     */
    public static void log(ErrorType type, ErrorSeverity severity, String desc) {
        SysError error = new SysError(type, severity, sanitizeString(desc));

        StackTraceElement[] stackTraceElements = Thread.currentThread().getStackTrace();
        error.setLocation(stackTraceElements[2].toString());

        saveError(error);
    }


    /**
     * Saves a predefined SysError to the database
     *
     * @param error
     */
    public static void log(SysError error) {
        StackTraceElement[] stackTraceElements = Thread.currentThread().getStackTrace();
        error.setLocation(stackTraceElements[2].toString());
        error.setDescription(sanitizeString(error.getDescription()));
        
        saveError(error);
    }

    /**
     * Uses ErrorService to save SysError to database
     *
     * @param error SysError to be saved
     */
    private static void saveError(SysError error) {
        gService.save(error);
        System.out.println("Error:\t --- "+ error);
    }

    public static List<SysError> getAllErrors() {
        return gService.getAll();
    }
    
    private static String sanitizeString(String string){
        if(string==null||string.length()<2){
            string = "NaN";
        }
        
        return StringEscapeUtils.escapeHtml4(string);
    }

}
