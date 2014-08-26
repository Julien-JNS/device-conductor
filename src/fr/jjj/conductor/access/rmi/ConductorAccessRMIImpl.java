package fr.jjj.conductor.access.rmi;

import fr.jjj.conductor.ConductorImpl;
import fr.jjj.conductor.access.ConductorAccess;
import fr.jjj.conductor.model.DeviceDesc;
import fr.jjj.conductor.model.Device;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.rmi.AlreadyBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

/**
 * Created by Jaunais on 01/07/2014.
 */
public class ConductorAccessRMIImpl extends ConductorAccess implements ConductorAccessRMI {

    private Log log= LogFactory.getLog(this.getClass());

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
    public Set<DeviceDesc> getDeviceDescriptions() throws RemoteException {

        Set<DeviceDesc> set=new HashSet<DeviceDesc>();
        Iterator<Device> itDevice=conductor.getDevices().iterator();
        while(itDevice.hasNext())
        {
            Device device=itDevice.next();
            DeviceDesc deviceDesc=new DeviceDesc();
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
        return  (DeviceAudioOutAccessRMI) UnicastRemoteObject.exportObject(new DeviceAudioOutAccessRMIImpl(conductor.getDevice(deviceLabel)),port);
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
    public List<String> getNavItems(String mediaSource, String reference) throws RemoteException {
        log.info("Receive RMI request for nav items for media source '"+mediaSource+"' at "+reference);
        return conductor.getNavItems(mediaSource,reference);
    }
}
