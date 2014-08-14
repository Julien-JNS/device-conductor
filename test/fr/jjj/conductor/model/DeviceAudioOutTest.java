package fr.jjj.conductor.model;

import org.junit.Before;
import org.junit.Test;

import java.util.Iterator;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class DeviceAudioOutTest {

    private static DeviceAudioOut device;


    @Before
    public void initialize() {
        device = new DeviceAudioOut("deviceAudioOut","OMX");
    }

    @Test
    public void testGetQueue() throws Exception {
        List<MediaItemDesc> testedQueue=device.getQueue();
        assertEquals("Empty queue",testedQueue.size(),0);

        device.addToQueue(new MediaItemDesc("Chanson 1"));
        device.addToQueue(new MediaItemDesc("Chanson 2"));

        Iterator<MediaItemDesc> itItem = testedQueue.iterator();

        assertEquals("first item",itItem.next().getTitle(),"Chanson 1");
        assertEquals("second item",itItem.next().getTitle(),"Chanson 2");

    }


}