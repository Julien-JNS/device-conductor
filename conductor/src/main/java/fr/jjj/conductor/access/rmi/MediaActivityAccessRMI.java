package fr.jjj.conductor.access.rmi;


import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.Set;

/**
 * Created by Jaunais on 26/06/2014.
 */
public interface MediaActivityAccessRMI extends Remote{

   Set<String> getMediaSourceList() throws RemoteException;

   Set<String> getMediaDeviceList() throws RemoteException;
}
