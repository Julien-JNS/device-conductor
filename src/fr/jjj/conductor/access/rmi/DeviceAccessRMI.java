package fr.jjj.conductor.access.rmi;

import fr.jjj.conductor.access.DeviceAccess;
import fr.jjj.conductor.model.Device;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.Set;

/**
 * Created by Jaunais on 10/08/2014.
 */
public interface DeviceAccessRMI extends Remote{


    String getLabel() throws RemoteException;

    String getType() throws RemoteException;
}
