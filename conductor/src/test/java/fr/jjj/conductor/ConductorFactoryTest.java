package fr.jjj.conductor;

import fr.jjj.conductor.model.Device;
import fr.jjj.conductor.model.DeviceAudioOut;
import fr.jjj.conductor.model.DeviceAudioOutTest;
import fr.jjj.conductor.model.DeviceVideoIn;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Iterator;

import static org.junit.Assert.*;

public class ConductorFactoryTest {

    private Conductor c;

    @Before
    public void initialize() {
        System.out.println("Before");
        // create config file
        TestUtils.initializeConfig();
    }


    @Test
    public void checkConfigParsing() {

        // MyClass is tested
        c = ConductorFactory.getInstance().getConductor();

        // Tests
        assertEquals("Conductor label", "RPI-1 (Salon)", c.getLabel());

        // Devices
        Iterator<Device> itDevice = c.getDevices().iterator();
        while(itDevice.hasNext()) {
            Device d = itDevice.next();
            if (d.getLabel().equals(TestUtils.DEVICE_LABELS[0])) {
                assertTrue("Check type for device 1", d instanceof DeviceAudioOut);
            } else if (d.getLabel().equals(TestUtils.DEVICE_LABELS[1])) {
                assertTrue("Check type for device 2", d instanceof DeviceVideoIn);
            }
        }
    }



    @After
    public void clean() {
        System.out.println("After");
        c.stop();
    }
}