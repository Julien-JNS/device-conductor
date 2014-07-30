package fr.jjj.conductor;


import fr.jjj.conductor.access.AccessFactory;
import fr.jjj.conductor.activity.media.ActivityMedia;
import fr.jjj.conductor.activity.media.MediaDevice;
import fr.jjj.conductor.activity.media.MediaSource;
import fr.jjj.conductor.config.ConductorConfig;
import fr.jjj.conductor.config.MediaActivityConfig;
import fr.jjj.conductor.config.media.MediaDeviceConfig;
import fr.jjj.conductor.config.media.MediaSourceConfig;

import java.util.Iterator;

/**
 * Created by Jaunais on 01/07/2014.
 */
public class ConductorFactory {

    private ConductorConfig config;

    private Conductor uniqueSprite;

    public ConductorFactory() {
        config= ConductorConfig.getConfig();

    }

    public Conductor getSprite() {
        if(uniqueSprite==null) {

            uniqueSprite = new Conductor();


            MediaActivityConfig mediaConfig=config.getMediaActivityConfig();
            if(mediaConfig!=null)
            {

                ActivityMedia mediaActivity=new ActivityMedia();
                Iterator<MediaSourceConfig> itSourceConfigs=mediaConfig.getMediaSourceConfigs().iterator();
                while(itSourceConfigs.hasNext())
                {
                    MediaSourceConfig mediaSourceConfig=itSourceConfigs.next();
                    mediaActivity.addMediaSource(new MediaSource(mediaSourceConfig.getType()));
                }
                Iterator<MediaDeviceConfig> itDeviceConfigs=mediaConfig.getMediaDeviceConfigs().iterator();
                while(itDeviceConfigs.hasNext())
                {
                    MediaDeviceConfig mediaDeviceConfig=itDeviceConfigs.next();
                    mediaActivity.addMediaDevice(new MediaDevice(mediaDeviceConfig.getType()));
                }
                uniqueSprite.setActivity(IConductor.ActivityType.MEDIA,mediaActivity);
            }

            // ACCESS
            new AccessFactory(config.getNetworkConfig()).getSpriteAccess(uniqueSprite);
        }
        return uniqueSprite;
    }
}
