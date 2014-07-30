package fr.jjj.conductor.config;


import fr.jjj.conductor.config.media.MediaDeviceConfig;
import fr.jjj.conductor.config.media.MediaSourceConfig;

import java.util.Collection;

/**
 * Created by Jaunais on 28/06/2014.
 */
public class MediaActivityConfig {

    Collection<MediaSourceConfig> mediaSources;

    Collection<MediaDeviceConfig> mediaDevices;

    public Collection<MediaSourceConfig> getMediaSourceConfigs() {
        return mediaSources;
    }

    public Collection<MediaDeviceConfig> getMediaDeviceConfigs() {
        return mediaDevices;
    }
}
