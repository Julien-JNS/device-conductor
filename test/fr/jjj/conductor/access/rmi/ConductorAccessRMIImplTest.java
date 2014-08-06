package fr.jjj.conductor.access.rmi;

import fr.jjj.conductor.Conductor;
import fr.jjj.conductor.ConductorFactory;
import fr.jjj.conductor.ConductorFactoryTest;
import fr.jjj.conductor.config.ConductorConfig;
import fr.jjj.conductor.config.NetworkConfig;
import fr.jjj.conductor.model.Device;
import fr.jjj.conductor.model.DeviceAudioOut;
import fr.jjj.conductor.model.DeviceVideoIn;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.Iterator;

import static org.junit.Assert.*;
import static org.junit.Assert.assertEquals;

public class ConductorAccessRMIImplTest {

    private static ConductorAccessRMI testedAccess;

    private static Conductor conductor;

    @BeforeClass
    public static void initialize()
    {
        ConductorFactoryTest.initializeConfig();
        try {

            conductor=new ConductorFactory().getConductor();

            String name = "conductorAccess";
            ConductorConfig config= ConductorConfig.getConfig();
            NetworkConfig nwconfig=config.getNetworkConfig();
            Registry registry = LocateRegistry.getRegistry(nwconfig.getHost(), nwconfig.getPort());

            testedAccess = (ConductorAccessRMI) registry.lookup(name);

            System.out.println("label:" + testedAccess.getLabel());
            // MediaActivityAccessRMI am=spriteComm.getActivityMedia();
//            System.out.println("Media activity:" +am );
//
//            System.out.println("Media sources:"+am.getMediaSourceList());
//            System.out.println("Media devices:"+am.getMediaDeviceList());

        } catch (Exception e) {
            System.err.println("RMI exception:");
            e.printStackTrace();
        }
    }

    @Test
    public void testGetLabel() throws Exception {
        assertEquals("Conductor Label",testedAccess.getLabel(), ConductorFactoryTest.CONDUCTOR_LABEL);
    }

    @Test
    public void testGetDevices() throws Exception {
        // Devices
        Iterator<Device> itDevice = testedAccess.getDevices().iterator();
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