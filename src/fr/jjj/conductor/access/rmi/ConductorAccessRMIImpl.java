package fr.jjj.conductor.access.rmi;

import fr.jjj.conductor.Conductor;
import fr.jjj.conductor.access.ConductorAccess;

import java.rmi.AlreadyBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;

/**
 * Created by Jaunais on 01/07/2014.
 */
public class ConductorAccessRMIImpl extends ConductorAccess implements ConductorAccessRMI {


    private int port;

    public ConductorAccessRMIImpl(Conductor sprite, String host, int port) {
        super(sprite);

        this.port=port;

        try {
            System.out.println("Opening sprite access on host "+host+" and port "+port);
            ConductorAccessRMI stub = (ConductorAccessRMI) UnicastRemoteObject.exportObject(this, port);
            Registry registry = LocateRegistry.createRegistry(port);
            registry.bind("spriteAccess", stub);

        } catch (RemoteException e) {
            e.printStackTrace();
        } catch (AlreadyBoundException e) {
            e.printStackTrace();
        }
    }

    @Override
    public MediaActivityAccessRMI getActivityMedia() throws RemoteException {
        System.out.println("Activity returned by RMI:"+sprite.getActivityMedia());
        return (MediaActivityAccessRMI) UnicastRemoteObject.exportObject(new MediaActivityAccessRMIImpl(sprite.getActivityMedia()),port);
        //return conductor.getActivityMedia();
    }
}
