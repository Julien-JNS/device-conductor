package fr.jjj.conductormanager.ui;

import java.util.List;

/**
 * Created by Jaunais on 22/08/2014.
 */
public interface DeviceAudioOutView {

    String getDeviceLabel();
    void setNavigation(List<String> items);

    void setQueue(List<String> items);
}
