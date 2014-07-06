package fr.jjj.sprite.test;

import fr.jjj.sprite.access.rmi.MediaActivityAccessI;
import fr.jjj.sprite.access.rmi.SpriteAccessI;
import fr.jjj.sprite.comm.ActivityMedia;
import fr.jjj.sprite.config.NetworkConfig;
import fr.jjj.sprite.config.SpriteConfig;

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
            SpriteConfig config=SpriteConfig.getConfig();
            NetworkConfig nwconfig=config.getNetworkConfig();
            Registry registry = LocateRegistry.getRegistry(nwconfig.getHost(), nwconfig.getPort());

            SpriteAccessI spriteComm = (SpriteAccessI) registry.lookup(name);

            MediaActivityAccessI am=spriteComm.getActivityMedia();
            System.out.println("Media activity:" +am );

            System.out.println("Media sources:"+am.getMediaSourceList());


        } catch (Exception e) {
            System.err.println("ComputePi exception:");
            e.printStackTrace();
        }
    }
}
