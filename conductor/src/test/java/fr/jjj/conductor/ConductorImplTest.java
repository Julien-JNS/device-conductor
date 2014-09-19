package fr.jjj.conductor;

import fr.jjj.conductor.model.MediaItem;
import fr.jjj.conductor.model.Resource;
import fr.jjj.conductor.model.ResourceFilesystem;
import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.util.Iterator;
import java.util.List;

import static org.junit.Assert.*;

public class ConductorImplTest {

    private static ConductorImpl conductor;

    @Before
    public void initialize() {
        // create config file
        TestUtils.initializeConfig();

        conductor = new ConductorFactory().getConductor();

        try {
            new File(TestUtils.FILESYSTEM_START).mkdirs();
            new File(TestUtils.FILESYSTEM_START+File.separator+"test.tst").createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testGetDevice() {

        assertEquals("device 1",conductor.getDevice(TestUtils.DEVICE_LABELS[0]).getLabel(),TestUtils.DEVICE_LABELS[0]);
        assertEquals("device 1",conductor.getDevice(TestUtils.DEVICE_LABELS[1]).getLabel(),TestUtils.DEVICE_LABELS[1]);
    }

    @Test
    public void testGetResources()
    {
        Iterator<Resource> it=conductor.getResources().iterator();
        while(it.hasNext())
        {
            Resource r=it.next();
            if(r.getLabel().equals(TestUtils.RESOURCE_LABELS[0]))
            {
                assertTrue("Check resource "+TestUtils.RESOURCE_LABELS[0],r instanceof ResourceFilesystem);
            }
            else if(r.getLabel().equals(TestUtils.RESOURCE_LABELS[1]))
            {

            }
        }
    }

    @Test
    public void testGetNavItems()
    {
        List<MediaItem> items=conductor.getMediaItems(TestUtils.RESOURCE_LABELS[0], null);

        Iterator<MediaItem> it=items.iterator();
        assertEquals("Check first file in FS start",it.next().getDescription().getTitle(),"test.tst");




    }

}