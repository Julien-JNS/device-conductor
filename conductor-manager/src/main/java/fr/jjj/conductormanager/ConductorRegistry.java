package fr.jjj.conductormanager;

import com.google.common.io.Files;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import fr.jjj.conductor.access.rmi.ConductorAccessRMI;
import fr.jjj.conductor.access.rmi.DeviceAccessRMI;
import fr.jjj.conductor.access.rmi.DeviceAudioOutAccessRMI;
import fr.jjj.conductor.config.NetworkConfig;
import fr.jjj.conductor.model.DeviceDesc;
import fr.jjj.conductormanager.model.ConductorDesc;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.charset.Charset;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.*;


public enum ConductorRegistry {

    INSTANCE;

    private static final String CONFIG_FILE="config-manager.json";

    private Log log= LogFactory.getLog(this.getClass());

    Set<ConductorDesc> predefinedConductors;

    Map<String, ConductorDesc> conductorDescriptions;

    Map<String, ConductorAccessRMI> conductorAccesses;

    Map<String, DeviceAccessRMI> deviceAccesses;

    // Map<String, DeviceConductor> deviceConductors;

    Map<String, DeviceDesc> devices;

    private List<RegistryListener> listeners;

    private ConductorRegistry() {
        loadConfig();

        initComm();

        initData();
    }

    private void loadConfig() {
        predefinedConductors=new HashSet<ConductorDesc>();
        conductorDescriptions = new HashMap<String, ConductorDesc>();
        File configFile=new File(CONFIG_FILE);
        try {
            String json= Files.toString(configFile, Charset.forName("UTF-8"));
            List<NetworkConfig> networkConfigs = new Gson().fromJson(json, new TypeToken<List<NetworkConfig>>() {}.getType());
            Iterator<NetworkConfig> it=networkConfigs.iterator();
            while(it.hasNext())
            {
                NetworkConfig nwc=it.next();
                predefinedConductors.add(new ConductorDesc(nwc.getHost(),nwc.getPort()));
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void initComm() {
        System.out.println("INIT COMM...");
        Iterator<ConductorDesc> it = predefinedConductors.iterator();
        conductorAccesses = new HashMap<String, ConductorAccessRMI>();
        deviceAccesses=new HashMap<String, DeviceAccessRMI>();

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
        log.info("Requesting access to audio device "+deviceLabel);
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
        else
        {
            log.info("Access already established.");
        }
        log.info("Returned access to device:"+access);
        return access;
    }

    public ConductorAccessRMI getConductorAccess(String conductorName) {
        return conductorAccesses.get(conductorName);
    }

    public ConductorAccessRMI getConductorAccessForDevice(String deviceLabel) {
        String conductorLabel=devices.get(deviceLabel).getConductor();
        return conductorAccesses.get(conductorLabel);
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
