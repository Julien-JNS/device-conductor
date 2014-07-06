package fr.jjj.sprite.config;


import fr.jjj.sprite.config.media.MediaDeviceConfig;

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
