package fr.jjj.conductormanager.ui;

import fr.jjj.conductor.ConductorFactory;
import fr.jjj.conductor.ConductorFactoryTest;
import fr.jjj.conductor.ConductorImpl;
import org.junit.Before;
import org.junit.Test;

import java.util.Iterator;
import java.util.List;

import static org.junit.Assert.*;
import static org.junit.Assert.assertEquals;

public class DeviceAudioOutPresenterTest implements DeviceAudioOutView{


    private DeviceAudioOutPresenter presenter;

    private static ConductorImpl conductor;

    @Before
    public void initialize() {
        // create config file
        ConductorFactoryTest.initializeConfig();

        conductor = new ConductorFactory().getConductor();
        presenter=new DeviceAudioOutPresenter(this);
    }

    @Test
    public void testGetQueue() throws Exception {

    }

    @Test
    public void testSetMediaSource()
    {
        presenter.setDevice(ConductorFactoryTest.DEVICE_LABELS[0]);
        presenter.setMediaSource(ConductorFactoryTest.RESOURCE_LABELS[0]);
    }


    @Override
    public String getDeviceLabel() {
        return null;
    }

    @Override
    public void setNavigation(List<String> items) {

        Iterator<String> it=items.iterator();
        assertEquals("Check first file in FS start",it.next(),"test.tst");
    }
}