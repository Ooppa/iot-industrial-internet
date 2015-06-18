/*
 * IoT - Industrial Internet Framework
 * Apache License Version 2.0, January 2004
 * Released as a part of Helsinki University
 * Software Engineering Lab in summer 2015
 */
package fi.iot.iiframework.errors;

public enum ErrorSeverity {

    NONE(0, "NONE"),
    NOTIFICATION(1, "NOTIFICATION"),
    LOW(2, "LOW"),
    MEDIUM(3, "MEDIUM"),
    HIGH(4, "HIGH"),
    FATAL(5, "FATAL");

    private final int severity;
    private final String id;

    ErrorSeverity(int severity, String id) {
        this.severity = severity;
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public int getSeverity() {
        return this.severity;
    }

    /**
     * Returns ErrorSeverity from the given ID.
     *
     * @param typeid Severity identifier
     * @return ErrorSeverity
     */
    public static ErrorSeverity getType(String typeid) {
        ErrorSeverity[] values = ErrorSeverity.values();

        for (ErrorSeverity value : values) {
            if (value.getId().equals(typeid)) {
                return value;
            }
        }

        return ErrorSeverity.NONE;
    }

    /**
     * Compares two severities between their importance.
     *
     * @param first First Severity
     * @param second Second Severity
     * @return -1, 0 or 1
     */
    public static int compare(ErrorSeverity first, ErrorSeverity second) {
        return Integer.compare(first.getSeverity(), second.getSeverity());
    }

}
