package fr.jjj.conductor.access.rmi;

import fr.jjj.conductor.model.Device;
import fr.jjj.conductor.model.DeviceAudioOut;
import fr.jjj.conductor.model.MediaItemDesc;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.rmi.RemoteException;
import java.util.List;

/**
 * Created by Jaunais on 10/08/2014.
 */
public class DeviceAudioOutAccessRMIImpl extends DeviceAccessRMIImpl implements DeviceAudioOutAccessRMI {

    private Log log= LogFactory.getLog(this.getClass());

    public DeviceAudioOutAccessRMIImpl(Device device) {
        super(device);
    }

    @Override
    public List<MediaItemDesc> getQueue() throws RemoteException {
        List<MediaItemDesc> queue=((DeviceAudioOut)device).getQueue();
        log.info("Queue requested for device: "+device.getLabel());
        log.info("Queue size: "+queue.size ());
        return  queue;
    }

    @Override
    public void addToQueue(MediaItemDesc item) {
        ((DeviceAudioOut)device).addToQueue(item);

    }

    @Override
    public void play(MediaItemDesc item) {

    }


}
