/*
 * IoT - Industrial Internet Framework
 * Apache License Version 2.0, January 2004
 * Released as a part of Helsinki University
 * Software Engineering Lab in summer 2015
 */
package fi.iot.iiframework.domain;

import fi.iot.iiframework.mutator.ValueCondition;

public enum ReadoutFlag {

    ERRONOUS,
    POSSIBLE_ERRONOUS,
    TOO_HIGH_VALUE,
    TOO_LOW_VALUE,
    EMPTY;

    /**
     * Returns a Flag representation from a ValueCondition
     *
     * @param condition Condition to evaluate
     * @return Flag that corresponds the ValueCondition given
     */
    public static ReadoutFlag getFlagFromCondition(ValueCondition condition) {
        if (atleastOneIs(condition, ValueCondition.HIGHER_THAN, ValueCondition.HIGHER_THAN_OR_EQUALS)) {
            return TOO_HIGH_VALUE;
        }

        if (atleastOneIs(condition, ValueCondition.LOWER_THAN, ValueCondition.LOWER_THAN_OR_EQUALS)) {
            return TOO_LOW_VALUE;
        }

        return EMPTY;
    }

    private static boolean atleastOneIs(ValueCondition condition, ValueCondition... compared) {
        boolean atleastOne = false;

        for (ValueCondition compare : compared) {
            if (compare == condition) {
                atleastOne = true;
            }
        }

        return atleastOne;
    }
}
