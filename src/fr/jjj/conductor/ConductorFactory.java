package fr.jjj.conductor;


import fr.jjj.conductor.access.AccessFactory;
import fr.jjj.conductor.access.ConductorAccess;
import fr.jjj.conductor.activity.media.ActivityMedia;
import fr.jjj.conductor.activity.media.MediaDevice;
import fr.jjj.conductor.activity.media.MediaSource;
import fr.jjj.conductor.config.ConductorConfig;
import fr.jjj.conductor.config.MediaActivityConfig;
import fr.jjj.conductor.config.DeviceConfig;
import fr.jjj.conductor.config.ResourceConfig;
import fr.jjj.conductor.model.Device;
import fr.jjj.conductor.model.DeviceAudioOut;
import fr.jjj.conductor.model.DeviceVideoIn;

import java.util.Iterator;
import java.util.Set;

/**
 * Created by Jaunais on 01/07/2014.
 */
public class ConductorFactory {

    private ConductorConfig config;

    private static ConductorImpl uniqueConductor;

    private static ConductorAccess uniqueAccess;

    public ConductorFactory() {
        config = ConductorConfig.getConfig();

    }

    public ConductorImpl getConductor() {
        if (uniqueConductor == null) {

            uniqueConductor = new ConductorImpl(config.getLabel());

            Set<DeviceConfig> deviceConfigs = config.getDeviceConfigs();
            Iterator<DeviceConfig> itDeviceConfig = deviceConfigs.iterator();
            while (itDeviceConfig.hasNext()) {
                DeviceConfig deviceConf = itDeviceConfig.next();
                Device device = null;

                String type = deviceConf.getType();
                if (type.equals("audio-out")) {
                    device = new DeviceAudioOut(deviceConf.getLabel(), deviceConf.getBridge());
                } else if (type.equals("video-in")) {
                    device = new DeviceVideoIn(deviceConf.getLabel());
                }
                uniqueConductor.addDevice(device);
            }


//            MediaActivityConfig mediaConfig=config.getMediaActivityConfig();
//            if(mediaConfig!=null)
//            {
//
//                ActivityMedia mediaActivity=new ActivityMedia();
//                Iterator<ResourceConfig> itSourceConfigs=mediaConfig.getMediaSourceConfigs().iterator();
//                while(itSourceConfigs.hasNext())
//                {
//                    ResourceConfig mediaSourceConfig=itSourceConfigs.next();
//                    mediaActivity.addMediaSource(new MediaSource(mediaSourceConfig.getType()));
//                }
//                Iterator<DeviceConfig> itDeviceConfigs=mediaConfig.getMediaDeviceConfigs().iterator();
//                while(itDeviceConfigs.hasNext())
//                {
//                    DeviceConfig mediaDeviceConfig=itDeviceConfigs.next();
//                    mediaActivity.addMediaDevice(new MediaDevice(mediaDeviceConfig.getType()));
//                }
//                uniqueConductor.setActivity(Conductor.ActivityType.MEDIA,mediaActivity);
//            }

            // ACCESS
            if (uniqueAccess == null) {
                System.out.println("creating access...");
                uniqueAccess = new AccessFactory(config.getNetworkConfig()).getSpriteAccess(uniqueConductor);
            }
        }
        return uniqueConductor;
    }

}