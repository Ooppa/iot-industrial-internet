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

@Ignore
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

        testsensor.setActive(true);
        testsensor.setThresholdMax((double) 20000);
        testsensor.setThresholdMin((double) -1000);

        readouts.add(new Readout(9, -500, testsensor));
        readouts.add(new Readout(10, 0, testsensor));
        readouts.add(new Readout(11, 10, testsensor));
        readouts.add(new Readout(12, 50, testsensor));
        readouts.add(new Readout(13, 100, testsensor));
        readouts.add(new Readout(14, 1000, testsensor));
        
        testsensor.setReadouts(readouts);
        testsensor.setSource(source);
        sensors.add(testsensor);

    }

    @After
    public void tearDown() {
    }

    @Test
    public void spotsValueThatIsTooHigh() {
        double max = 50;
        testsensor.setThresholdMax(max);
        source.setSensors(sensors);

        new MarkReadoutAsErronousIfValueIs(ValueCondition.HIGHER_THAN).mutateAll(testsensor);
        new MarkReadoutAsErronousIfValueIs(ValueCondition.LOWER_THAN).mutateAll(testsensor);

        readouts = testsensor.getReadouts();

        for (Readout r : readouts) {
            if (r.getFlag() == ReadoutFlag.TOO_HIGH_VALUE) {
                assertTrue("Value " + r.getValue() + " was marked as too high when limit was " + max, r.getValue() > max);
            } else {
                assertTrue("Value " + r.getValue() + " was not marked as too high when limit was " + max, r.getValue() <= max);
            }
        }
    }

    @Test
    public void spotsValueThatIsTooLow() {
        double min = -60;
        testsensor.setThresholdMin(min);
        source.setSensors(sensors);

        new MarkReadoutAsErronousIfValueIs(ValueCondition.HIGHER_THAN).mutateAll(testsensor);
        new MarkReadoutAsErronousIfValueIs(ValueCondition.LOWER_THAN).mutateAll(testsensor);

        readouts = testsensor.getReadouts();

        for (Readout r : readouts) {
            System.out.println(r.getFlag() + ", " + r.getValue());
            if (r.getFlag() == ReadoutFlag.TOO_LOW_VALUE) {
                assertTrue("Value " + r.getValue() + " was marked as too low when limit was " + min, r.getValue() < min);
            } else {
                assertTrue("Value " + r.getValue() + " was not marked as too low when limit was " + min, r.getValue() > min);
            }
        }
    }
}
