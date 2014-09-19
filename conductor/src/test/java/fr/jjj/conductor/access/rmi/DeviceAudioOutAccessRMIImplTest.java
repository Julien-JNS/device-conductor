package fr.jjj.conductor.access.rmi;

import fr.jjj.conductor.Conductor;
import fr.jjj.conductor.ConductorFactory;
import fr.jjj.conductor.ConductorFactoryTest;
import fr.jjj.conductor.TestUtils;
import fr.jjj.conductor.config.ConductorConfig;
import fr.jjj.conductor.config.NetworkConfig;
import fr.jjj.conductor.model.*;
import org.junit.BeforeClass;
import org.junit.Test;

import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.Iterator;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class DeviceAudioOutAccessRMIImplTest {

    private static ConductorAccessRMI conductorAccess;
    private static DeviceAudioOutAccessRMI testedAccess;

    private static Conductor conductor;

    private static DeviceAudioOutTest device;

    @BeforeClass
    public static void initialize()
    {
        TestUtils.initializeFileSystem();
        TestUtils.initializeConfig();
        try {

            conductor=new ConductorFactory().getConductor();

            String name = "conductorAccess";
            ConductorConfig config= ConductorConfig.getConfig();
            NetworkConfig nwconfig=config.getNetworkConfig();
            Registry registry = LocateRegistry.getRegistry(nwconfig.getHost(), nwconfig.getPort());

            conductorAccess = (ConductorAccessRMI) registry.lookup(name);

            testedAccess=conductorAccess.getDeviceAudioOutAccess(TestUtils.DEVICE_LABELS[0]);

            System.out.println("device label:" + testedAccess.getLabel());
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
        assertEquals("Conductor Label",testedAccess.getLabel(), TestUtils.DEVICE_LABELS[0]);
    }

    @Test
    public void testGetQueue() throws Exception {
        List<MediaItemDesc> testedQueue=testedAccess.getQueue();
        assertEquals("Empty queue",testedQueue.size(),0);


        for(String mediaSource:conductorAccess.getMediaSources(TestUtils.DEVICE_LABELS[0]))
        {
            for(MediaItemDesc mediaItemDesc:conductorAccess.getNavItems(mediaSource,null))
            {
                testedAccess.addToQueue(mediaItemDesc);
            }
        }

        testedQueue=testedAccess.getQueue();
        Iterator<MediaItemDesc> itItem = testedQueue.iterator();

        assertEquals("first item",itItem.next().getTitle(),"test.mp3");

    }
}