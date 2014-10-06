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

    private PlayingThread playingThread;

    private boolean keepPlaying=false;

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

        if(playingThread==null)
        {
            log.info("Init playing thread");
            keepPlaying=true;
            playingThread=new PlayingThread();
            playingThread.setItemToPlay(itemBeingPlayed);
            playingThread.start();
        }else
        {
            playingThread.restart();
        }
    }

    public void command(DeviceDesc.Command command)
    {
        log.info("Sending command "+command);
        log.info("player handler= "+playerHandler);
        switch(command)
        {
            case NEXT:
                shiftPlayedIndex(1);
                break;
            case PREV:
                shiftPlayedIndex(-1);
            case STOP:
                itemBeingPlayed=null;
                keepPlaying=false;
                playerHandler.command(DeviceDesc.Command.STOP);
                break;
            default:
                playerHandler.command(command);
                break;
        }

    }

    private void shiftPlayedIndex(int indexShift) {
        if(itemBeingPlayed!=null) {
            int currentIndex = getQueue().indexOf(itemBeingPlayed);
            log.info("currently playing "+itemBeingPlayed.getDescription().getTitle()+" (index "+currentIndex+")");
            int nextIndex = currentIndex + indexShift % getQueue().size();
            MediaItem nextItem=getQueue().get(nextIndex);
            itemBeingPlayed=nextItem;
            log.info("now playing "+itemBeingPlayed.getDescription().getTitle()+" (index "+nextIndex+")");
            playingThread.setItemToPlay(itemBeingPlayed);
        }
    }

    private class PlayingThread extends Thread
    {
        private MediaItem itemToPlay;

        private boolean ignoreShift=false;

        @Override
        public void run() {
            while(keepPlaying)
            {
                log.info("Loop iteration, item="+itemToPlay);
                playerHandler.play(itemToPlay);
                if(!ignoreShift) {
                    log.info("Do shift");
                    shiftPlayedIndex(1);
                }
                else
                {
                    log.info("No shift");
                    ignoreShift=false;
                }
            }
            log.info("Loop exit");
        }

        public void setItemToPlay(MediaItem itemToPlay) {
            this.itemToPlay = itemToPlay;
        }

        public void restart()
        {
            ignoreShift=true;
            playerHandler.command(DeviceDesc.Command.STOP);
        }
    }
}
