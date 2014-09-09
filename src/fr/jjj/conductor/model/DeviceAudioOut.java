package fr.jjj.conductor.model;

import fr.jjj.conductor.Conductor;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.util.*;

/**
 * Created by Jaunais on 05/08/2014.
 */
public class DeviceAudioOut extends Device {

    private Log log= LogFactory.getLog(this.getClass());

    private String bridge;

    private List<MediaItem> queue;

    private MediaItem itemBeingPlayed;

    private PlayerHandler playerHandler;

    public DeviceAudioOut(Conductor conductor, String label,String bridge)
    {
        super(conductor, label,"audio-out");
        queue=new ArrayList<MediaItem>();

        if(bridge.equals("omxplayer"))
        {
            log.info("Init omx player handler");
            playerHandler =new OmxHandler();
        }
    }

    public String getBridge() {
        return bridge;
    }

    public void addToQueue(MediaItem item)
    {
        Resource resource=item.getMediaSource();
        log.info("Resource: "+resource);
        List<MediaItem> items=resource.getMediaItems(item);
        Iterator<MediaItem> it=items.iterator();
        while(it.hasNext())
        {
            MediaItem newItem=it.next();
            queue.add(newItem);
            System.out.println("added in queue:" + newItem.getDescription().getTitle());
        }
        System.out.println("queue size:"+queue.size());
    }

    public List<MediaItem> getQueue()
    {
        System.out.println("get gueue queue size:"+queue.size());
        //TODO send a copy?
        return queue;
    }

    public void play(MediaItem item)
    {
        itemBeingPlayed=item;

        playerHandler.play(item);
    }
}
