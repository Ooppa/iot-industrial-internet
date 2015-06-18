/*
 * IoT - Industrial Internet Framework
 * Apache License Version 2.0, January 2004
 * Released as a part of Helsinki University
 * Software Engineering Lab in summer 2015
 */
package fi.iot.iiframework.restapi.filters;

import fi.iot.iiframework.application.Application;
import fi.iot.iiframework.errors.ErrorLogger;
import fi.iot.iiframework.errors.ErrorSeverity;
import fi.iot.iiframework.errors.ErrorType;
import java.util.logging.Level;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Restrictions;

public class MoreThan extends GeneralFilter {

    public MoreThan(String field) {
        super(field);
    }

    @Override
    public Criterion createCriterion(String... filters) throws ArrayIndexOutOfBoundsException {
        Double bound = 0.0;
        try {
            bound = Double.parseDouble(filters[0]);
        } catch (NumberFormatException ex) {
            ErrorLogger.log(ErrorType.PARSE_ERROR, ErrorSeverity.LOW, "Could not parse double given as a parameter in filter.");
        }

        return Restrictions.gt(getField(), bound);
    }
}
