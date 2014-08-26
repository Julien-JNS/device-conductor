package fr.jjj.conductor;


import fr.jjj.conductor.model.Device;
import fr.jjj.conductor.model.Resource;

import java.util.List;
import java.util.Set;

/**
 * Created by Jaunais on 01/07/2014.
 */
public interface Conductor {

    enum ActivityType{
        MEDIA,CAM;
    }

    String getLabel();

    Set<Device> getDevices();

    void addDevice(Device device);

    void addResource(Resource resource);

    Device getDevice(String deviceLabel);

    List<String> getNavItems(String mediaSource, String reference);



}
