package fr.jjj.conductor.access.rmi;

import fr.jjj.conductor.model.DeviceDesc;
import fr.jjj.conductor.model.MediaItemDesc;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;
import java.util.Set;

/**
 * Created by Jaunais on 26/06/2014.
 */
public interface ConductorAccessRMI extends Remote{

    String getLabel() throws RemoteException;

    Set<DeviceDesc> getDeviceDescriptions() throws RemoteException;

    Set<String> getMediaSources(String deviceLabel) throws RemoteException;

    DeviceAudioOutAccessRMI getDeviceAudioOutAccess(String deviceLabel) throws RemoteException;

    List<MediaItemDesc> getNavItems(String mediaSource, MediaItemDesc reference) throws RemoteException;
}
