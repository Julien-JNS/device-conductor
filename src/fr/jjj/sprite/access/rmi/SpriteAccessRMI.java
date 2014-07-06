package fr.jjj.sprite.access.rmi;

import fr.jjj.sprite.Sprite;
import fr.jjj.sprite.access.SpriteAccess;

import java.rmi.AlreadyBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.Collection;

/**
 * Created by Jaunais on 01/07/2014.
 */
public class SpriteAccessRMI extends SpriteAccess implements SpriteAccessI {



    public SpriteAccessRMI(Sprite sprite, String host, int port) {
        super(sprite);

        try {
            System.out.println("Opening sprite access on host "+host+" and port "+port);
            SpriteAccessI stub = (SpriteAccessI) UnicastRemoteObject.exportObject(this, port);
            Registry registry = LocateRegistry.createRegistry(port);
            registry.bind("spriteAccess", stub);

        } catch (RemoteException e) {
            e.printStackTrace();
        } catch (AlreadyBoundException e) {
            e.printStackTrace();
        }
    }

    @Override
    public MediaActivityAccessI getActivityMedia() throws RemoteException {
        return new MediaActivityAccess(sprite.getActivityMedia());
    }
}
