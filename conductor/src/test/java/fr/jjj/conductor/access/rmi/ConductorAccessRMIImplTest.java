package fr.jjj.conductor.access.rmi;

import fr.jjj.conductor.Conductor;
import fr.jjj.conductor.ConductorFactory;
import fr.jjj.conductor.TestUtils;
import fr.jjj.conductor.TestUtils;
import fr.jjj.conductor.model.DeviceDesc;
import fr.jjj.conductor.config.ConductorConfig;
import fr.jjj.conductor.config.NetworkConfig;
import fr.jjj.conductor.model.MediaItemDesc;
import org.junit.BeforeClass;
import org.junit.Test;

import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.Iterator;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class ConductorAccessRMIImplTest {

    private static ConductorAccessRMI testedAccess;

    private static Conductor conductor;

    @BeforeClass
    public static void initialize()
    {
        TestUtils.initializeConfig();
        try {

            conductor=ConductorFactory.getInstance().getConductor();

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
        assertEquals("Conductor Label",testedAccess.getLabel(), TestUtils.CONDUCTOR_LABEL);
    }

    @Test
    public void testGetDeviceDescriptions() throws Exception {
        // Devices
        Iterator<DeviceDesc> itDeviceDesc = testedAccess.getDeviceDescriptions().iterator();
        while(itDeviceDesc.hasNext()) {
            DeviceDesc dd = itDeviceDesc.next();
            if (dd.getLabel().equals(TestUtils.DEVICE_LABELS[0])) {
                assertEquals("Check type for device 1", dd.getType(),"audio-out");
            } else if (dd.getLabel().equals(TestUtils.DEVICE_LABELS[1])) {
                assertEquals("Check type for device 2", dd.getType(), "video-in");
            }
        }

    }

    @Test
    public void testGetNavItems()
    {
        List<MediaItemDesc> items= null;
        try {
            items = testedAccess.getNavItems(TestUtils.RESOURCE_LABELS[0],null);
        } catch (RemoteException e) {
            e.printStackTrace();
        }

        Iterator<MediaItemDesc> it=items.iterator();
        for(String expectedItem:TestUtils.getExpectedItems()) {
            assertEquals("Check item in FS start", expectedItem, it.next().getTitle());
        }
    }
}