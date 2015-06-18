/*
 * IoT - Industrial Internet Framework
 * Apache License Version 2.0, January 2004
 * Released as a part of Helsinki University
 * Software Engineering Lab in summer 2015
 */
package fi.iot.iiframework.mutator;

import fi.iot.iiframework.domain.InformationSource;
import fi.iot.iiframework.domain.Readout;
import fi.iot.iiframework.domain.ReadoutFlag;
import fi.iot.iiframework.domain.Sensor;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import org.junit.After;
import org.junit.AfterClass;
import static org.junit.Assert.assertTrue;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;

/**
 * NOT DONE YET STOP LOOKING!
 *
 */
public class MutatorTest {

    private Set<Sensor> sensors;
    private Set<Readout> readouts;
    private InformationSource source;
    private Sensor testsensor;

    public MutatorTest() {
    }

    @BeforeClass
    public static void setUpClass() {
    }

    @AfterClass
    public static void tearDownClass() {
    }

    @Before
    public void setUp() {

        source = new InformationSource();
        source.setId((long) 555);
        sensors = new HashSet<>();
        testsensor = new Sensor();

        readouts = new HashSet<>();

        readouts.add(new Readout(9, -50));
        readouts.add(new Readout(10, 0));
        readouts.add(new Readout(11, 10));
        readouts.add(new Readout(12, 50));
        readouts.add(new Readout(13, 100));
        readouts.add(new Readout(14, 1000));

        testsensor.setActive(true);
        testsensor.setThresholdMax(20000);
        testsensor.setThresholdMin(-1000);
        testsensor.setReadouts(readouts);
        testsensor.setSource(source);
        sensors.add(testsensor);

    }

    @After
    public void tearDown() {
    }

    @Ignore
    public void spotsValueThatIsTooHigh() {
        double max = 60;
        testsensor.setThresholdMax(max);
        source.setSensors(sensors);

        new MarkReadoutAsErronousIfValueIs(ValueCondition.HIGHER_THAN).mutateAll(source);
        new MarkReadoutAsErronousIfValueIs(ValueCondition.LOWER_THAN).mutateAll(source);

        sensors = source.getSensors();
        readouts = sensors.iterator().next().getReadouts();

        for (Readout r : readouts) {

            if (r.getFlag() == ReadoutFlag.TOO_HIGH_VALUE) {
                assertTrue("Value " + r.getValue() + " was marked as too high when limit was " + max, r.getValue() > max);
            } else if (r.getFlag() == null) {
                assertTrue("Value " + r.getValue() + " was not marked when limit was " + max, r.getValue() > max);
            } else {
                assertTrue("Value " + r.getValue() + " was not marked as too high when limit was " + max, r.getValue() <= max);
            }
        }
    }

    @Ignore
    public void spotsValueThatIsTooLow() {
        double min = -20;
        testsensor.setThresholdMin(min);
        source.setSensors(sensors);

        new MarkReadoutAsErronousIfValueIs(ValueCondition.HIGHER_THAN).mutateAll(source);
        new MarkReadoutAsErronousIfValueIs(ValueCondition.LOWER_THAN).mutateAll(source);

        for (Readout r : readouts) {
            if (r.getFlag() == ReadoutFlag.TOO_LOW_VALUE) {
                assertTrue("Value " + r.getValue() + " was marked as too low when limit was " + min, r.getValue() > min);
            } else if (r.getFlag() == null) {
                assertTrue("Value " + r.getValue() + " was not marked when limit was " + min, r.getValue() < min);
            } else {
                assertTrue("Value " + r.getValue() + " was not marked as too low when limit was " + min, r.getValue() <= min);
            }
        }
    }
}
