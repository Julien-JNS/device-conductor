package fr.jjj.conductor.config;


import java.util.Collection;

/**
 * Created by Jaunais on 28/06/2014.
 */
public class MediaActivityConfig {

    Collection<ResourceConfig> mediaSources;

    Collection<DeviceConfig> mediaDevices;

    public Collection<ResourceConfig> getMediaSourceConfigs() {
        return mediaSources;
    }

    public Collection<DeviceConfig> getMediaDeviceConfigs() {
        return mediaDevices;
    }
}
