package fr.jjj.conductor;


import fr.jjj.conductor.model.Device;

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

    Device getDevice(String deviceLabel);



}
