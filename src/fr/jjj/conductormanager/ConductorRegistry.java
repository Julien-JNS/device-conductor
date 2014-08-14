package fr.jjj.conductormanager;

import fr.jjj.conductor.access.rmi.ConductorAccessRMI;
import fr.jjj.conductor.access.rmi.DeviceAccessRMI;
import fr.jjj.conductor.access.rmi.DeviceAudioOutAccessRMI;
import fr.jjj.conductor.model.DeviceDesc;
import fr.jjj.conductormanager.model.ConductorDesc;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.*;


public enum ConductorRegistry {

    INSTANCE;

    private Log log= LogFactory.getLog(this.getClass());

    Set<ConductorDesc> predefinedConductors;

    Map<String, ConductorDesc> conductorDescriptions;

    Map<String, ConductorAccessRMI> conductorAccesses;

    Map<String, DeviceAccessRMI> deviceAccesses;

    // Map<String, DeviceConductor> deviceConductors;

    Map<String, DeviceDesc> devices;

    private List<RegistryListener> listeners;

    private ConductorRegistry() {
        conductorAccesses = new HashMap<String, ConductorAccessRMI>();


        loadConfig();

        initComm();

        initData();

    }

    private void loadConfig() {
        predefinedConductors=new HashSet<ConductorDesc>();
        conductorDescriptions = new HashMap<String, ConductorDesc>();

        ConductorDesc desc=new ConductorDesc("192.168.0.49", 4056);
        predefinedConductors.add(desc);
    }

    private void initComm() {
        System.out.println("INIT COMM...");
        Iterator<ConductorDesc> it = predefinedConductors.iterator();

        while (it.hasNext()) {
            ConductorDesc conductorDesc=it.next();

            System.out.println("conductorDesc=" + conductorDesc);
//	        if (System.getSecurityManager() == null) {
//	            System.setSecurityManager(new SecurityManager());
//	        }
            try {
                String name = "conductorAccess";
                Registry registry = LocateRegistry.getRegistry(conductorDesc.getHost(), conductorDesc.getPort());
                log.info("rmi obj?");
                ConductorAccessRMI conductorAccess = (ConductorAccessRMI) registry.lookup(name);
                String conductorLabel = conductorAccess.getLabel();
                System.out.println("conductor label (receive by RMI):" + conductorLabel);
                conductorDesc.setLabel(conductorLabel);
                conductorDescriptions.put(conductorLabel,conductorDesc);
                conductorAccesses.put(conductorLabel, conductorAccess);
            } catch (Exception e) {
                System.err.println("ComputePi exception:");
                e.printStackTrace();
            }

        }

        fireConductorListChanged();
    }

    private void initData() {
        //deviceConductors = new HashMap<String, DeviceConductor>();
        devices = new HashMap<String, DeviceDesc>();

        Iterator<ConductorAccessRMI> it = conductorAccesses.values().iterator();
        while (it.hasNext()) {
            ConductorAccessRMI access = it.next();
            try {
                String label = access.getLabel();
                System.out.println("access label=" + label);

                Iterator<fr.jjj.conductor.model.DeviceDesc> itDeviceDesc = access.getDeviceDescriptions().iterator();
                while (itDeviceDesc.hasNext()) {
                    fr.jjj.conductor.model.DeviceDesc deviceDesc = itDeviceDesc.next();
                    devices.put(deviceDesc.getLabel(), deviceDesc);
                }

            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }
    }

    public Set<ConductorDesc> getDeviceConductors() {
        Set<ConductorDesc> conductors = new HashSet(conductorDescriptions.values());
        return conductors;
    }

    public Set<DeviceDesc> getDevices() {
        log.info("Device list requested");
        Set<DeviceDesc> deviceDescs = new HashSet<DeviceDesc>();


        Iterator<DeviceDesc> itDeviceDesc = devices.values().iterator();

        while (itDeviceDesc.hasNext()) {
            deviceDescs.add(itDeviceDesc.next());
        }
        log.info("Returned "+deviceDescs.size()+" devices.");
        return deviceDescs;
    }

    public DeviceAudioOutAccessRMI getDeviceAudioOutAccess(String deviceLabel) {
        DeviceAudioOutAccessRMI access = (DeviceAudioOutAccessRMI) deviceAccesses.get(deviceLabel);
        if (access == null) {
            String conductorLabel = devices.get(deviceLabel).getConductor();
            try {
                access = conductorAccesses.get(conductorLabel).getDeviceAudioOutAccess(deviceLabel);
                deviceAccesses.put(deviceLabel, access);
            } catch (RemoteException e) {
                log.error("Could not create access to device!");
                e.printStackTrace();
            }
            log.info("Access to device created:"+access);
        }
        log.info("Returned access to device:"+access);
        return access;
    }

    public ConductorAccessRMI getConductorAccess(String conductorName) {
        return conductorAccesses.get(conductorName);
    }

    /** Listener Management **/
    public void addListener(RegistryListener listener)
    {
        if(listeners==null)
        {
            listeners=new ArrayList<RegistryListener>();
        }
        listeners.add(listener);
    }

    private void fireConductorListChanged()
    {
        if(listeners!=null && !listeners.isEmpty()) {
            Iterator<RegistryListener> it = listeners.iterator();
            while (it.hasNext()) {
                it.next().conductorListChanged();
            }
        }
    }
}
