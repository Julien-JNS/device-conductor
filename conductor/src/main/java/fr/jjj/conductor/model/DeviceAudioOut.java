package fr.jjj.conductor.model;

import fr.jjj.conductor.Conductor;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Created by Jaunais on 05/08/2014.
 */
public class DeviceAudioOut extends Device {

    private Log log= LogFactory.getLog(this.getClass());

    private String bridge;

    private List<MediaItem> queue;

    private MediaItem itemBeingPlayed;

    private PlayerHandler playerHandler;

    public DeviceAudioOut(String label,String bridge)
    {
        super(label,"audio-out");
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
        log.info("Start playing "+item.getDescription().getTitle());
        itemBeingPlayed=item;

        playerHandler.play(item);
    }
}
