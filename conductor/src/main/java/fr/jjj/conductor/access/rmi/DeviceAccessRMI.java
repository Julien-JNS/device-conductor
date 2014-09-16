package fr.jjj.conductor.access.rmi;

import java.rmi.Remote;
import java.rmi.RemoteException;

/**
 * Created by Jaunais on 10/08/2014.
 */
public interface DeviceAccessRMI extends Remote {


    String getLabel() throws RemoteException;

    String getType() throws RemoteException;
}
