package fr.jjj.conductormanager.standalone;

import fr.jjj.conductor.Conductor;
import fr.jjj.conductor.ConductorFactory;
import fr.jjj.conductormanager.TestUtils;
import fr.jjj.conductormanager.ui.StatusPresenter;
import fr.jjj.conductormanager.ui.StatusView;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Matchers;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

/**
 * Created with IntelliJ IDEA.
 * User: jjaunais
 * Date: 02/10/14
 * Time: 14:00
 * To change this template use File | Settings | File Templates.
 */
public class TestStandalone {

    @Mock
    StatusView statusView;

    private  StatusPresenter statusPresenter;

    @Before
    public void initialize() {
        TestUtils.initializeConfig();
        TestUtils.initializeFileSystem();
        TestUtils.initializeManagerConfig();
        Conductor conductor=ConductorFactory.getInstance().getConductor();

        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testStatus()
    {
        
        statusPresenter=new StatusPresenter(statusView);
        Mockito.verify(statusView).setDetailedStatus(Matchers.anyString());
        Mockito.verify(statusView).setGlobalStatus(Matchers.anyString());

    }

    @Test
    public void testDeviceList()
    {

        statusPresenter=new StatusPresenter(statusView);
        Mockito.verify(statusView).setDetailedStatus(Matchers.anyString());
        Mockito.verify(statusView).setGlobalStatus(Matchers.anyString());

    }
}
