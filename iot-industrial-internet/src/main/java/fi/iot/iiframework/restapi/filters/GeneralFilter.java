/*
 * IoT - Industrial Internet Framework
 * Apache License Version 2.0, January 2004
 * Released as a part of Helsinki University
 * Software Engineering Lab in summer 2015
 */
package fi.iot.iiframework.restapi.filters;

import org.hibernate.criterion.Criterion;

public abstract class GeneralFilter {

    private final String field;

    /**
     * Creates a new RreadoutFilter and attaches it to a field.
     *
     * @param field Name of the field this filter is attached to
     */
    public GeneralFilter(String field) {
        this.field = field;
    }

    /**
     * Returns the name of the field this Filter is attached to.
     *
     * @return Field
     */
    public String getField() {
        return field;
    }

    /**
     * Creates a Criterion object used to change the query to the database.
     *
     * @param filters Filter parameters
     *
     * @return Criterion
     *
     * @throws ArrayIndexOutOfBoundsException
     */
    public Criterion createCriterion(String... filters) throws ArrayIndexOutOfBoundsException {
        return null; // on default doesn't return anything
    };

}
