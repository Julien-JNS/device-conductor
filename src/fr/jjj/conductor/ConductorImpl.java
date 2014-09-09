package fr.jjj.conductor;


import fr.jjj.conductor.activity.Activity;
import fr.jjj.conductor.model.Device;
import fr.jjj.conductor.model.MediaItem;
import fr.jjj.conductor.model.MediaItemDesc;
import fr.jjj.conductor.model.Resource;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;


import java.util.*;

/**
 * Created by Jaunais on 26/06/2014.
 */
public class ConductorImpl implements Conductor {

    private Log log= LogFactory.getLog(this.getClass());

    private String label;

    private Map<ActivityType, Activity> activities;

    private Map<String, Device> devices;

    private Map<String, Resource> resources;

    protected ConductorImpl(String label) {
        this.label = label;
        devices = new HashMap<String, Device>();
        resources = new HashMap<String, Resource>();
        log.info("Conductor initialized");
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

    @Override
    public Device getDevice(String deviceLabel) {
        return devices.get(deviceLabel);
    }

    public Set<String> getResources(String deviceLabel)
    {
        log.info("Checking media source for device " + deviceLabel);
        //TODO filter audio/video/resolution
        Set<String> sourceLabels=new HashSet<String>();
        Iterator<Resource> it=resources.values().iterator();
        while(it.hasNext())
        {
            sourceLabels.add(it.next().getLabel());
        }
        return sourceLabels;
    }

    public Set<Resource> getResources() {
        Set<Resource> set=new HashSet<Resource>();
        set.addAll(resources.values());
        return set;
    }

    @Override
    public void addResource(Resource resource) {

        if(resource!=null)
        {
            resources.put(resource.getLabel(),resource);
        }
    }


    @Override
    public List<MediaItem> getMediaItems(String mediaSource, String reference) {
        Resource resource=resources.get(mediaSource);
        log.info("Requesting nav items for resource "+resource.getLabel()+" (reference="+reference+")");
        return resource.getMediaItems(reference);
    }

    @Override
    public Resource getResource(String label) {
        return resources.get(label);
    }
}
