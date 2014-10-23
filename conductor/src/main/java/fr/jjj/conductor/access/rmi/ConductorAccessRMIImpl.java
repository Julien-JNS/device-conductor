package fr.jjj.conductor.access.rmi;

import fr.jjj.conductor.ConductorImpl;
import fr.jjj.conductor.access.ConductorAccess;
import fr.jjj.conductor.model.Device;
import fr.jjj.conductor.model.DeviceDesc;
import fr.jjj.conductor.model.MediaItem;
import fr.jjj.conductor.model.MediaItemDesc;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.net.MalformedURLException;
import java.rmi.AlreadyBoundException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.*;

/**
 * Created by Jaunais on 01/07/2014.
 */
public class ConductorAccessRMIImpl extends ConductorAccess implements ConductorAccessRMI {

    private Log log = LogFactory.getLog(this.getClass());

    private static final String CONDUCTOR_RMI_ID = "conductorAccess";

    private Registry registry;

    private int port;

    public ConductorAccessRMIImpl(ConductorImpl sprite, String host, int port) {
        super(sprite);

        this.port = port;

        try {
            System.out.println("Opening conductor access on host " + host + " and port " + port);
            ConductorAccessRMI stub = (ConductorAccessRMI) UnicastRemoteObject.exportObject(this, port);
            registry = LocateRegistry.createRegistry(port);
            System.out.println("Registry " + host + "," + port + ":" + registry);
            registry.bind(CONDUCTOR_RMI_ID, stub);
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
    public Set<DeviceDesc> getDeviceDescriptions() throws RemoteException {

        Set<DeviceDesc> set = new HashSet<DeviceDesc>();
        Iterator<Device> itDevice = conductor.getDevices().iterator();
        while (itDevice.hasNext()) {
            Device device = itDevice.next();
            DeviceDesc deviceDesc = new DeviceDesc();
            deviceDesc.setLabel(device.getLabel());
            deviceDesc.setConductor(conductor.getLabel());
            deviceDesc.setType(device.getType());
            deviceDesc.setStatus(device.getStatus());
            set.add(deviceDesc);
        }
        return set;
    }

    @Override
    public DeviceAudioOutAccessRMI getDeviceAudioOutAccess(String deviceLabel) throws RemoteException {
        return (DeviceAudioOutAccessRMI) UnicastRemoteObject.exportObject(new DeviceAudioOutAccessRMIImpl(conductor.getDevice(deviceLabel)), port);
    }

    @Override
    public Set<String> getMediaSources(String deviceLabel) throws RemoteException {
        return conductor.getResources(deviceLabel);
    }

    //    @Override
//    public MediaActivityAccessRMI getActivityMedia() throws RemoteException {
//       // System.out.println("Activity returned by RMI:"+ conductor.getActivityMedia());
//        return (MediaActivityAccessRMI) UnicastRemoteObject.exportObject(new MediaActivityAccessRMIImpl(conductor.getActivityMedia()),port);
//        //return conductor.getActivityMedia();
//    }


    @Override
    public List<MediaItemDesc> getNavItems(String mediaSource, MediaItemDesc reference) throws RemoteException {
        List<MediaItemDesc> itemDescriptions = new ArrayList<MediaItemDesc>();
        log.info("Received RMI request for nav items for media source '" + mediaSource + "' at " + reference);
        List<MediaItem> items = conductor.getMediaItems(mediaSource, reference);
        if (items != null) {
            Iterator<MediaItem> it = items.iterator();
            while (it.hasNext()) {
                MediaItem item = it.next();
                itemDescriptions.add(item.getDescription());
            }
            log.info("Returning " + itemDescriptions.size() + ": " + itemDescriptions);
        }
        return itemDescriptions;
    }

    @Override
    public void close() {
        try {
            if (registry == null) {
                registry = LocateRegistry.getRegistry(port);
            }
            registry.lookup(CONDUCTOR_RMI_ID);
            registry.unbind(CONDUCTOR_RMI_ID);

            UnicastRemoteObject.unexportObject(registry, true);

        } catch (RemoteException e) {
            e.printStackTrace();
        } catch (NotBoundException e) {
            e.printStackTrace();
        }
    }
}
