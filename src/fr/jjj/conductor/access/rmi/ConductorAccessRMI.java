package fr.jjj.conductor.access.rmi;

import java.rmi.Remote;
import java.rmi.RemoteException;

/**
 * Created by Jaunais on 26/06/2014.
 */
public interface ConductorAccessRMI extends Remote{



    MediaActivityAccessRMI getActivityMedia()throws RemoteException;
}
