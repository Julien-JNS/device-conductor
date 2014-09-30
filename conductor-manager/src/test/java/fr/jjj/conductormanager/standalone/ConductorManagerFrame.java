package fr.jjj.conductormanager.standalone;

import fr.jjj.conductormanager.ui.DeviceAudioOutView;
import fr.jjj.conductormanager.ui.StatusView;

import javax.swing.*;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: jjaunais
 * Date: 30/09/14
 * Time: 18:39
 * To change this template use File | Settings | File Templates.
 */
public class ConductorManagerFrame extends JFrame implements StatusView,DeviceAudioOutView{

    private JLabel status;

    public ConductorManagerFrame()
    {
        status=new JLabel();
        add(status);

    }

    @Override
    public String getDeviceLabel() {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public void setNavigation(List<String> items) {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public void setQueue(List<String> items) {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public void setGlobalStatus(String status) {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public void setDetailedStatus(String status) {
        //To change body of implemented methods use File | Settings | File Templates.
    }
}
