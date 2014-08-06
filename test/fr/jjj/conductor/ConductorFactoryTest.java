package fr.jjj.conductor;

import fr.jjj.conductor.model.Device;
import fr.jjj.conductor.model.DeviceAudioOut;
import fr.jjj.conductor.model.DeviceVideoIn;
import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Iterator;

import static org.junit.Assert.*;

public class ConductorFactoryTest {

    public static String CONDUCTOR_LABEL="RPI-1 (Salon)";

    public static final String[] DEVICE_LABELS = {"enceinte salon","webcam salon"};

    public static final String CONFIG1 = "{'label':'"+CONDUCTOR_LABEL+"','networkConfig': {'host': '127.0.0.1','port': '4056'}," +
            "'devices': " +
            "[{'type': 'audio-out','label':'" + DEVICE_LABELS[0] + "','bridge':'omxplayer'}," +
            "{'type': 'video-in','label':'" + DEVICE_LABELS[1] + "','bridge': 'xxx'}]," +
            "'resources':" +
            "[{'type': 'filesystem','label': 'Musique FREEBOX','default': '/home/pi/freebox/Musiques'}," +
            "{'type': 'googlemusic','label': 'Musique GOOGLE','default': 'Maryse'" +
            "}]}";

    static public void initializeConfig() {
        // create config file
        File configFile = new File("./config.json");
        FileOutputStream fos = null;
        try {
            fos = new FileOutputStream(configFile);

            fos.write(CONFIG1.getBytes());

            fos.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Before
    public void initialize() {
        // create config file
        ConductorFactoryTest.initializeConfig();
    }

    @Test
    public void checkConfigParsing() {

        // MyClass is tested
        Conductor c = new ConductorFactory().getConductor();

        // Tests
        assertEquals("Conductor label", "RPI-1 (Salon)", c.getLabel());

        // Devices
        Iterator<Device> itDevice = c.getDevices().iterator();
        while(itDevice.hasNext()) {
            Device d = itDevice.next();
            if (d.getLabel().equals(ConductorFactoryTest.DEVICE_LABELS[0])) {
                assertTrue("Check type for device 1", d instanceof DeviceAudioOut);
            } else if (d.getLabel().equals(ConductorFactoryTest.DEVICE_LABELS[1])) {
                assertTrue("Check type for device 2", d instanceof DeviceVideoIn);
            }
        }
    }


}