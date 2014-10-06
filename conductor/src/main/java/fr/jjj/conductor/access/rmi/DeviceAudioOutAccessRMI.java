package fr.jjj.conductor.access.rmi;

import fr.jjj.conductor.model.DeviceDesc;
import fr.jjj.conductor.model.MediaItemDesc;

import java.rmi.RemoteException;
import java.util.List;

/**
 * Created by Jaunais on 10/08/2014.
 */
public interface DeviceAudioOutAccessRMI extends DeviceAccessRMI {

    List<MediaItemDesc> getQueue() throws RemoteException;

    void addToQueue(MediaItemDesc item) throws RemoteException;

    void play(MediaItemDesc item) throws RemoteException;

    void command(DeviceDesc.Command command) throws RemoteException;







}
