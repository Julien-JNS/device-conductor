package fr.jjj.conductor.model;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class DeviceAudioOutTest {

    private static DeviceAudioOut device;

    private static Resource resource;

    private static List<MediaItem> location=new ArrayList<MediaItem>();

    @Mock
    DeviceAudioOutListener listener;


    @Before
    public void initialize() {
        device = new DeviceAudioOut("deviceAudioOut","OMX");
        resource=new ResourceFilesystem("freebox","");

        MockitoAnnotations.initMocks(this);

        device.setListener(listener);
    }

    @Test
    public void testGetQueue() throws Exception {
        List<MediaItem> testedQueue=device.getQueue();
        assertEquals("Empty queue",testedQueue.size(),0);

        device.addToQueue(new MediaItem(new MediaItemDesc("1","Chanson 1"),resource,location));
        device.addToQueue(new MediaItem(new MediaItemDesc("2","Chanson 2"),resource,location));

        Iterator<MediaItem> itItem = testedQueue.iterator();

        assertEquals("first item",itItem.next().getDescription().getTitle(),"Chanson 1");
        assertEquals("second item",itItem.next().getDescription().getTitle(),"Chanson 2");

    }

    @Test
    public void testPlay() throws Exception {
        MediaItem item1 = new MediaItem(new MediaItemDesc("1", "Chanson 1"), resource, location);
        MediaItem item2 = new MediaItem(new MediaItemDesc("2", "Chanson 2"), resource, location);

        device.addToQueue(item1);
        device.addToQueue(item2);

        device.play(item1);

        Thread.sleep(2000);

        Mockito.verify(listener,Mockito.times(1)).nowPlaying(item1);
        Thread.sleep(10000);
        Mockito.verify(listener,Mockito.times(1)).nowPlaying(item2);

    }

    @Test
    public void testNext() throws Exception {
        MediaItem item1 = new MediaItem(new MediaItemDesc("1", "Chanson 1"), resource, location);
        MediaItem item2 = new MediaItem(new MediaItemDesc("2", "Chanson 2"), resource, location);

        device.addToQueue(item1);
        device.addToQueue(item2);

        device.play(item1);

        Thread.sleep(1000);

        Mockito.verify(listener,Mockito.times(1)).nowPlaying(item1);

        device.command(DeviceDesc.Command.NEXT);

        Thread.sleep(1000);
        Mockito.verify(listener,Mockito.times(1)).nowPlaying(item2);

    }

    @Test
    public void testPrevious() throws Exception {
        MediaItem item1 = new MediaItem(new MediaItemDesc("1", "Chanson 1"), resource, location);
        MediaItem item2 = new MediaItem(new MediaItemDesc("2", "Chanson 2"), resource, location);
        MediaItem item3 = new MediaItem(new MediaItemDesc("3", "Chanson 3"), resource, location);

        device.addToQueue(item1);
        device.addToQueue(item2);
        device.addToQueue(item3);

        device.play(item1);

        Thread.sleep(1000);

        Mockito.verify(listener,Mockito.times(1)).nowPlaying(item1);

        device.command(DeviceDesc.Command.PREV);

        Thread.sleep(1000);
        Mockito.verify(listener,Mockito.times(1)).nowPlaying(item3);

    }
}