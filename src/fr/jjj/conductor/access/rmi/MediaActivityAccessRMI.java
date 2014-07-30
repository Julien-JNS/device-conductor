package fr.jjj.conductor.access.rmi;


import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.Collection;

/**
 * Created by Jaunais on 26/06/2014.
 */
public interface MediaActivityAccessRMI extends Remote{

   Collection<String> getMediaSourceList() throws RemoteException;

   Collection<String> getMediaDeviceList() throws RemoteException;
}
