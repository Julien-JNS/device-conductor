package fr.jjj.conductor.model;

import org.junit.Before;
import org.junit.Test;

import java.util.Iterator;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class DeviceAudioOutTest {

    private static DeviceAudioOut device;

    private static Resource resource;


    @Before
    public void initialize() {
        device = new DeviceAudioOut("deviceAudioOut","OMX");
        resource=new ResourceFilesystem("freebox","");
    }

    @Test
    public void testGetQueue() throws Exception {
        List<MediaItem> testedQueue=device.getQueue();
        assertEquals("Empty queue",testedQueue.size(),0);

        device.addToQueue(new MediaItem(new MediaItemDesc("1","Chanson 1"),resource,""));
        device.addToQueue(new MediaItem(new MediaItemDesc("2","Chanson 2"),resource,""));

        Iterator<MediaItem> itItem = testedQueue.iterator();

        assertEquals("first item",itItem.next().getDescription().getTitle(),"Chanson 1");
        assertEquals("second item",itItem.next().getDescription().getTitle(),"Chanson 2");

    }


}