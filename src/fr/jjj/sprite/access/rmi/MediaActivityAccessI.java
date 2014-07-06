package fr.jjj.sprite.access.rmi;


import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.Collection;

/**
 * Created by Jaunais on 26/06/2014.
 */
public interface MediaActivityAccessI extends Remote{

   Collection<String> getMediaSourceList() throws RemoteException;

   Collection<String> getMediaDeviceList() throws RemoteException;
}
