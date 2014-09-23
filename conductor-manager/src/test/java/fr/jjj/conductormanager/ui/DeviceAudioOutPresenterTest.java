package fr.jjj.conductormanager.ui;

import fr.jjj.conductor.ConductorFactory;
import fr.jjj.conductor.ConductorImpl;
import fr.jjj.conductormanager.TestUtils;
import org.junit.Before;
import org.junit.Test;

import java.util.Iterator;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class DeviceAudioOutPresenterTest implements DeviceAudioOutView{


    private DeviceAudioOutPresenter presenter;

    private static ConductorImpl conductor;

    @Before
    public void initialize() {
        // create config files
        TestUtils.initializeConfig();
        TestUtils.initializeManagerConfig();

        conductor = ConductorFactory.getInstance().getConductor();
        presenter=new DeviceAudioOutPresenter(this);
    }

    @Test
    public void testGetQueue() throws Exception {

    }

    @Test
    public void testSetMediaSource()
    {
        presenter.setDevice(TestUtils.DEVICE_LABELS[0]);
        presenter.setMediaSource(TestUtils.RESOURCE_LABELS[0]);
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

    @Override
    public void setQueue(List<String> items) {
        //To change body of implemented methods use File | Settings | File Templates.
    }
}