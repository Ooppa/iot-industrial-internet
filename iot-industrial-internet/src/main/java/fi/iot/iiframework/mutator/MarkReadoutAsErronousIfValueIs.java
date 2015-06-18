/*
 * IoT - Industrial Internet Framework
 * Apache License Version 2.0, January 2004
 * Released as a part of Helsinki University
 * Software Engineering Lab in summer 2015
 */
package fi.iot.iiframework.mutator;

import fi.iot.iiframework.application.Application;
import fi.iot.iiframework.domain.InformationSource;
import fi.iot.iiframework.domain.Readout;
import fi.iot.iiframework.domain.ReadoutFlag;
import fi.iot.iiframework.domain.Sensor;
import fi.iot.iiframework.errors.ErrorLogger;
import fi.iot.iiframework.errors.ErrorSeverity;
import fi.iot.iiframework.errors.ErrorType;
import fi.iot.iiframework.errors.SysError;
import java.util.logging.Level;

public class MarkReadoutAsErronousIfValueIs implements Mutator {

    private ValueCondition condition;

    public MarkReadoutAsErronousIfValueIs(ValueCondition condition) {
        this.condition = condition;
    }

    @Override
    public void mutateAll(Sensor sensor) {
        for (Readout readout : sensor.getReadouts()) {
            try {
                if (condition == ValueCondition.HIGHER_THAN) {
                    mutateOneReadout(readout, condition, sensor.getThresholdMax());
                } else if (condition == ValueCondition.LOWER_THAN) {
                    mutateOneReadout(readout, condition, sensor.getThresholdMin());
                }
            } catch (NullPointerException npe) {
                Application.logger.log(Level.INFO, "Catched a NullPointerException while mutating one sensor.");
            }
        }
    }

    /**
     * Mutates one readout based on the threshold. Warning: Assumes that the
     * given readout has a sensor!
     *
     * @param readout Readout to mutate
     * @param condition Condition to mutate the Readout by
     * @param threshold Threshold for the mutation
     */
    public void mutateOneReadout(Readout readout, ValueCondition condition, Double threshold) {
        if (threshold == null) {
            return;
        }
        if (isNotDefaultThreshold(threshold)) {
            if (ValueCondition.compare(condition, readout.getValue(), threshold)) {
                addError(readout.getValue(), threshold, condition, readout.getSensor());
                readout.setFlag(ReadoutFlag.getFlagFromCondition(condition));
            }
        }
    }

    private void addError(double readout, double threshold, ValueCondition condition, Sensor sensor) {
        SysError error = new SysError(ErrorType.ERRONOUS_DATA,
                ErrorSeverity.NOTIFICATION,
                "Erronous data was detected because the threshold value "
                + threshold + " was " + condition.getLiteral() + " value " + readout + " found in the sensor.",
                "This error was caused in sensor [id: " + sensor.getId() + "]");
        ErrorLogger.log(error);
    }

    private boolean isNotDefaultThreshold(double threshold) {
        return threshold != Integer.MIN_VALUE || threshold != Integer.MAX_VALUE;
    }


}
