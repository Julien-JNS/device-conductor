package fr.jjj.conductormanager.ui;

import fr.jjj.conductor.access.rmi.ConductorAccessRMI;
import fr.jjj.conductor.access.rmi.DeviceAudioOutAccessRMI;
import fr.jjj.conductor.model.MediaItemDesc;
import fr.jjj.conductormanager.ConductorRegistry;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.rmi.RemoteException;
import java.util.List;
import java.util.Set;

/**
 * Created by Jaunais on 11/08/2014.
 */
public class DeviceAudioOutPresenter {

    private Log log = LogFactory.getLog(this.getClass());

    private DeviceAudioOutView view;

    private String deviceLabel,mediaSource;

    public DeviceAudioOutPresenter(DeviceAudioOutView view) {
        this.view = view;
    }

    public void setDevice(String deviceLabel) {
        this.deviceLabel = deviceLabel;
    }

    public List<MediaItemDesc> getQueue() {
        log.info("Queue requested for audio device " + deviceLabel + "...");
        DeviceAudioOutAccessRMI access = ConductorRegistry.INSTANCE.getDeviceAudioOutAccess(deviceLabel);
        log.info("access to device: " + access);
        List<MediaItemDesc> queue = null;

        try {
            System.out.println("Requesting audio device queue from distant conductor...");
            queue = access.getQueue();
            log.info("Received audio device queue from distant conductor (size=" + queue.size() + ").");
        } catch (RemoteException e) {
            e.printStackTrace();
            log.error("Could not get audio device queue from distant conductor!");
        }

        return queue;
    }

    public Set<String> getMediaSources() {
        log.info("Media sources requested for audio device " + deviceLabel + "...");

        ConductorAccessRMI access = ConductorRegistry.INSTANCE.getConductorAccessForDevice(deviceLabel);
        log.info("access to conductor: " + access);
        Set<String> sources = null;

        try {
            System.out.println("Requesting audio device media sources from distant conductor...");
            sources = access.getMediaSources(deviceLabel);
            log.info("Received audio device media sources from distant conductor (size=" + sources.size() + ").");
        } catch (RemoteException e) {
            e.printStackTrace();
            log.error("Could not get audio device media sources from distant conductor!");
        }

        return sources;
    }

    public void setMediaSource(String mediaSource)
    {
        this.mediaSource=mediaSource;
        log.info("Media sources '" + mediaSource + "' selected for audio device " + deviceLabel + "...");

        ConductorAccessRMI access = ConductorRegistry.INSTANCE.getConductorAccessForDevice(deviceLabel);
        log.info("access to conductor: " + access);

        log.info("access to conductor: TEST 1" + access);
        try {
            log.info("access to conductor: TEST 2" + access);
            List<String> items=access.getNavItems(mediaSource, "");
        log.info("Received " + items.size() + " items from conductor");
            view.setNavigation(items);
        } /*catch (RemoteException e) {
            e.printStackTrace();
        } */catch(Exception e)
        {
            log.error("Could not reach conductor!");
            log.error(e.getMessage());
        }
    }

    public void navigationItemSelected(String navItem)
    {

    }

    //public List<String> getNavItems

}
