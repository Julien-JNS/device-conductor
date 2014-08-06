package fr.jjj.conductor;


import fr.jjj.conductor.activity.Activity;
import fr.jjj.conductor.activity.media.ActivityMedia;
import fr.jjj.conductor.model.Device;


import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * Created by Jaunais on 26/06/2014.
 */
public class ConductorImpl implements Conductor {

    private String label;

    private Map<ActivityType, Activity> activities;

    private Map<String, Device> devices;

    protected ConductorImpl(String label) {
        this.label = label;
        devices = new HashMap<String, Device>();
    }

    @Override
    public String getLabel() {
        return label;
    }

    @Override
    public Set<Device> getDevices() {
        Set<Device> set=new HashSet<Device>();
        set.addAll(devices.values());
        return set;
    }

    @Override
    public void addDevice(Device device) {
        devices.put(device.getLabel(),device);
    }
}
