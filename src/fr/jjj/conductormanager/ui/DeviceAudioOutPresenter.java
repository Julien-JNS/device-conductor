package fr.jjj.conductormanager.ui;

import fr.jjj.conductor.access.rmi.DeviceAudioOutAccessRMI;
import fr.jjj.conductor.model.MediaItemDesc;
import fr.jjj.conductormanager.ConductorRegistry;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.rmi.RemoteException;
import java.util.List;

/**
 * Created by Jaunais on 11/08/2014.
 */
public class DeviceAudioOutPresenter {

    private Log logger= LogFactory.getLog(this.getClass());

    public List<MediaItemDesc> getQueue(String deviceLabel)
    {
        logger.info("Queue requested for audio device "+deviceLabel+"...");
        DeviceAudioOutAccessRMI access=ConductorRegistry.INSTANCE.getDeviceAudioOutAccess(deviceLabel);
        logger.info("access to device: "+access);
        List<MediaItemDesc> queue=null;


        try {

            System.out.println("Requesting audio device queue from distant conductor...");
            queue=access.getQueue();
            logger.info("Received audio device queue from distant conductor (size="+queue.size()+").");
        } catch (RemoteException e) {
            e.printStackTrace();
            logger.error("Could not get audio device queue from distant conductor!");
        }

        return queue;
    }
}
