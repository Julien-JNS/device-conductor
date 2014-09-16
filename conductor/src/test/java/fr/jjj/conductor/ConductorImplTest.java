package fr.jjj.conductor;

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
        ConductorFactoryTest.initializeConfig();

        conductor = new ConductorFactory().getConductor();

        try {
            new File(ConductorFactoryTest.FILESYSTEM_START).mkdirs();
            new File(ConductorFactoryTest.FILESYSTEM_START+File.separator+"test.tst").createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testGetDevice() {

        assertEquals("device 1",conductor.getDevice(ConductorFactoryTest.DEVICE_LABELS[0]).getLabel(),ConductorFactoryTest.DEVICE_LABELS[0]);
        assertEquals("device 1",conductor.getDevice(ConductorFactoryTest.DEVICE_LABELS[1]).getLabel(),ConductorFactoryTest.DEVICE_LABELS[1]);
    }

    @Test
    public void testGetResources()
    {
        Iterator<Resource> it=conductor.getResources().iterator();
        while(it.hasNext())
        {
            Resource r=it.next();
            if(r.getLabel().equals(ConductorFactoryTest.RESOURCE_LABELS[0]))
            {
                assertTrue("Check resource "+ConductorFactoryTest.RESOURCE_LABELS[0],r instanceof ResourceFilesystem);
            }
            else if(r.getLabel().equals(ConductorFactoryTest.RESOURCE_LABELS[1]))
            {

            }
        }
    }

    @Test
    public void testGetNavItems()
    {
        List<String> items=conductor.getMediaItems(ConductorFactoryTest.RESOURCE_LABELS[0], null);

        Iterator<String> it=items.iterator();
        assertEquals("Check first file in FS start",it.next(),"test.tst");




    }

}