package fr.jjj.conductor.model;

import fr.jjj.conductor.Conductor;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import javax.print.attribute.standard.Media;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Created by Jaunais on 05/08/2014.
 */
public class DeviceAudioOut extends Device {

    private Log log = LogFactory.getLog(this.getClass());

    private String bridge;

    private List<MediaItem> queue;

    private MediaItem itemBeingPlayed;

    private PlayerHandler playerHandler;

    private PlayingThread playingThread;

    private boolean keepPlaying = false;

    private DeviceAudioOutListener listener;

    public DeviceAudioOut(String label, String bridge) {
        super(label, "audio-out");
        queue = new ArrayList<MediaItem>();

        if (bridge.equals("omxplayer")) {
            log.info("Init omx player handler");
            playerHandler = new OmxHandler();
        } else {
            log.info("Init test player handler");
            playerHandler = new TestHandler();
        }

    }

    public void setListener(DeviceAudioOutListener listener) {
        this.listener = listener;
    }

    public String getBridge() {
        return bridge;
    }

    public void addToQueue(MediaItem item) {
        Resource resource = item.getMediaSource();
        log.info("Resource: " + resource);
        List<MediaItem> items = resource.getMediaItems(item);
        Iterator<MediaItem> it = items.iterator();
        while (it.hasNext()) {
            MediaItem newItem = it.next();
            queue.add(newItem);
            System.out.println("added in queue:" + newItem.getDescription().getTitle());
        }
        System.out.println("queue size:" + queue.size());
    }

    public List<MediaItem> getQueue() {
        System.out.println("get gueue queue size:" + queue.size());
        //TODO send a copy?
        return queue;
    }

    public void play(MediaItem item) {
        log.info("Start playing " + item.getDescription().getTitle());

        if (playingThread == null) {
            log.info("Init playing thread");
            keepPlaying = true;
            playingThread = new PlayingThread();
            playingThread.setItemToPlay(item);
            playingThread.start();
        } else {
            playingThread.jump(item);
        }
    }

    public void command(DeviceDesc.Command command) {
        log.info("Sending command " + command);
        log.info("player handler= " + playerHandler);
        switch (command) {
            case NEXT:
                playingThread.jump(1);
                break;
            case PREV:
                playingThread.jump(-1);
            case STOP:
                itemBeingPlayed = null;
                keepPlaying = false;
                playerHandler.command(DeviceDesc.Command.STOP);
                break;
            default:
                playerHandler.command(command);
                break;
        }

    }

    private void updateStatus(MediaItem item) {
        itemBeingPlayed = item;
        if(listener!=null) {
            listener.nowPlaying(item);
        }
    }

    private class PlayingThread extends Thread {
        private MediaItem itemToPlay;

        private boolean ignoreShift = false;

        @Override
        public void run() {
            while (keepPlaying) {
                log.info("Loop iteration, item=" + itemToPlay.getDescription().getTitle());
                updateStatus(itemToPlay);
                playerHandler.play(itemToPlay);
                if (!ignoreShift) {
                    log.info("Do shift");
                    setItemToPlay(getNextItem(1));
                } else {
                    log.info("No shift");
                    ignoreShift = false;
                }
            }
            log.info("Loop exit");
        }

        public void setItemToPlay(MediaItem itemToPlay) {
            this.itemToPlay = itemToPlay;
        }

        public void jump(MediaItem item) {
            setItemToPlay(item);
            ignoreShift = true;
            playerHandler.command(DeviceDesc.Command.STOP);
        }

        public void jump(int indexShift) {
            jump(getNextItem(indexShift));
        }

        private MediaItem getNextItem(int indexShift) {
            MediaItem nextItem = null;
            int currentIndex = 0;
            if (itemToPlay != null) {
                currentIndex = getQueue().indexOf(itemToPlay);
            }
            int nextIndex = (currentIndex + indexShift) % getQueue().size();
            if(nextIndex<0)// Modulus can give negative result
            {
                nextIndex+=getQueue().size();
            }
            nextItem = getQueue().get(nextIndex);
            return nextItem;
        }
    }
}
