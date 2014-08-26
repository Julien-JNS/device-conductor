package fr.jjj.conductor.model;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Jaunais on 05/08/2014.
 */
public class DeviceAudioOut extends Device {

    private String bridge;

    private List<MediaItemDesc> queue;



    public DeviceAudioOut(String label,String bridge)
    {
        super(label,"audio-out");
        queue=new ArrayList<MediaItemDesc>();
    }

    public String getBridge() {
        return bridge;
    }

    public void addToQueue(MediaItemDesc item)
    {
        queue.add(item);
        System.out.println("added in queue:"+item.getTitle());
        System.out.println("queue size:"+queue.size());
    }

    public List<MediaItemDesc> getQueue()
    {
        System.out.println("get gueue queue size:"+queue.size());
        //TODO send a copy?
        return queue;
    }

    public void play(MediaItemDesc item)
    {
        //TODO
    }
}
