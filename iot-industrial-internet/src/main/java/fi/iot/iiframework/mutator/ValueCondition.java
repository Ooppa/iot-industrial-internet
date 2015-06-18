/*
 * IoT - Industrial Internet Framework
 * Apache License Version 2.0, January 2004
 * Released as a part of Helsinki University
 * Software Engineering Lab in summer 2015
 */
package fi.iot.iiframework.mutator;

public enum ValueCondition {

    HIGHER_THAN("higher than"),
    LOWER_THAN("lower than"),
    HIGHER_THAN_OR_EQUALS("higher than or equals"),
    LOWER_THAN_OR_EQUALS("lower than or equals"),
    EQUALS("equal to"),
    NOT_EQUALS("not equal to");

    private final String literal;

    private ValueCondition(String literal) {
        this.literal = literal;
    }

    public String getLiteral() {
        return literal;
    }

    /**
     * Compares two double values between each other based on given condition.
     *
     * @param condition Condition for the comparison
     * @param first First value as double
     * @param second Second value as double
     * @return Result of the comparison as a boolean
     */
    public static boolean compare(ValueCondition condition, double first, double second) {
        if (condition == ValueCondition.EQUALS) {
            return first == second;
        }

        if (condition == ValueCondition.NOT_EQUALS) {
            return first != second;
        }

        if (condition == ValueCondition.HIGHER_THAN) {
            return first > second;
        }

        if (condition == ValueCondition.LOWER_THAN) {
            return first < second;
        }

        if (condition == ValueCondition.HIGHER_THAN_OR_EQUALS) {
            return first >= second;
        }

        if (condition == ValueCondition.LOWER_THAN_OR_EQUALS) {
            return first == second;
        }

        return false;
    }

    /**
     * Compares two integer values between each other based on given condition.
     *
     * @param condition Condition for the comparison
     * @param first First value as double
     * @param second Second value as double
     * @return Result of the comparison as a boolean
     */
    public static boolean compare(ValueCondition condition, int first, int second) {
        return compare(condition, (double) first, (double) second);
    }
}
