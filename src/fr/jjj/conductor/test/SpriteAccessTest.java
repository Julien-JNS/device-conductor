package fr.jjj.conductor.test;

import fr.jjj.conductor.access.rmi.ConductorAccessRMI;
import fr.jjj.conductor.access.rmi.MediaActivityAccessRMI;
import fr.jjj.conductor.config.ConductorConfig;
import fr.jjj.conductor.config.NetworkConfig;

import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

/**
 * Created by Jaunais on 03/07/2014.
 */
public class SpriteAccessTest {

    public static void main(String[] args)
    {
        try {
            String name = "spriteAccess";
            ConductorConfig config= ConductorConfig.getConfig();
            NetworkConfig nwconfig=config.getNetworkConfig();
            Registry registry = LocateRegistry.getRegistry(nwconfig.getHost(), nwconfig.getPort());

            ConductorAccessRMI spriteComm = (ConductorAccessRMI) registry.lookup(name);

            MediaActivityAccessRMI am=spriteComm.getActivityMedia();
            System.out.println("Media activity:" +am );

            System.out.println("Media sources:"+am.getMediaSourceList());
            System.out.println("Media devices:"+am.getMediaDeviceList());

        } catch (Exception e) {
            System.err.println("ComputePi exception:");
            e.printStackTrace();
        }
    }
}
