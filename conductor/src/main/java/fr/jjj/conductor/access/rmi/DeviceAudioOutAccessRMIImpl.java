package fr.jjj.conductor.access.rmi;

import fr.jjj.conductor.model.*;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.Iterator;
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
        List<MediaItemDesc> itemDescriptions=new ArrayList<MediaItemDesc>();
        List<MediaItem> items=((DeviceAudioOut)device).getQueue();
        Iterator<MediaItem> it=items.iterator();
        while(it.hasNext())
        {
            itemDescriptions.add(it.next().getDescription());
        }
        log.info("Queue requested for device: "+device.getLabel());
        log.info("Queue size: "+itemDescriptions.size ());
        return  itemDescriptions;
    }

    @Override
    public void addToQueue(MediaItemDesc itemDesc) {
        log.info("Received request to add "+itemDesc.getTitle()+" to queue.");
        ((DeviceAudioOut)device).addToQueue(MediaItem.getMediaItem(itemDesc));
    }

    @Override
    public void play(MediaItemDesc itemDesc) {
        log.info("Received request to play "+itemDesc.getTitle());
        log.info("Device="+device);
        log.info("Device(cast)="+(DeviceAudioOut)device);
        MediaItem mediaItem = MediaItem.getMediaItem(itemDesc);
        log.info("Media item ="+mediaItem);
        ((DeviceAudioOut)device).play(mediaItem);
    }

    @Override
    public void command(DeviceDesc.Command command) throws RemoteException {
        log.info("Received command request "+command);
        log.info("Device="+device);
        log.info("Device(cast)="+(DeviceAudioOut)device);
        ((DeviceAudioOut)device).command(command);
    }
}
