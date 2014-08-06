package fr.jjj.conductor.access.rmi;

import fr.jjj.conductor.ConductorImpl;
import fr.jjj.conductor.access.ConductorAccess;
import fr.jjj.conductor.model.Device;

import java.rmi.AlreadyBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.Set;

/**
 * Created by Jaunais on 01/07/2014.
 */
public class ConductorAccessRMIImpl extends ConductorAccess implements ConductorAccessRMI {


    private int port;

    public ConductorAccessRMIImpl(ConductorImpl sprite, String host, int port) {
        super(sprite);

        this.port=port;

        try {
            System.out.println("Opening conductor access on host "+host+" and port "+port);
            ConductorAccessRMI stub = (ConductorAccessRMI) UnicastRemoteObject.exportObject(this, port);
            Registry registry = LocateRegistry.createRegistry(port);
            System.out.println("Registry "+host+","+port+":"+registry);
            registry.bind("conductorAccess", stub);
            System.out.println("RMI object published");
        } catch (RemoteException e) {
            e.printStackTrace();
        } catch (AlreadyBoundException e) {
            e.printStackTrace();
        }
    }

    @Override
    public String getLabel() throws RemoteException {
        return conductor.getLabel();
    }

    @Override
    public Set<Device> getDevices() throws RemoteException {
        return conductor.getDevices();
    }

//    @Override
//    public MediaActivityAccessRMI getActivityMedia() throws RemoteException {
//       // System.out.println("Activity returned by RMI:"+ conductor.getActivityMedia());
//        return null;//(MediaActivityAccessRMI) UnicastRemoteObject.exportObject(new MediaActivityAccessRMIImpl(conductor.getActivityMedia()),port);
//        //return conductor.getActivityMedia();
//    }
}
