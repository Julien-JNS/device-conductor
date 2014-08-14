package fr.jjj.conductor;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class ConductorTest {

    private static Conductor conductor;


    @Before
    public void initialize() {
        // create config file
        ConductorFactoryTest.initializeConfig();

        conductor = new ConductorFactory().getConductor();
    }

    @Test
    public void testGetDevice() {

        assertEquals("device 1",conductor.getDevice(ConductorFactoryTest.DEVICE_LABELS[0]).getLabel(),ConductorFactoryTest.DEVICE_LABELS[0]);
        assertEquals("device 1",conductor.getDevice(ConductorFactoryTest.DEVICE_LABELS[1]).getLabel(),ConductorFactoryTest.DEVICE_LABELS[1]);
    }


}