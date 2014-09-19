package fr.jjj.conductormanager.ui;

import fr.jjj.conductor.access.rmi.ConductorAccessRMI;
import fr.jjj.conductor.access.rmi.DeviceAudioOutAccessRMI;
import fr.jjj.conductor.model.MediaItemDesc;
import fr.jjj.conductormanager.ConductorRegistry;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.rmi.RemoteException;
import java.util.*;

/**
 * Created by Jaunais on 11/08/2014.
 */
public class DeviceAudioOutPresenter {

    private Log log = LogFactory.getLog(this.getClass());

    private DeviceAudioOutView view;

    private String deviceLabel,mediaSource;

    private Map<String, MediaItemDesc> currentMediaItems=new HashMap<String, MediaItemDesc>();

    public DeviceAudioOutPresenter(DeviceAudioOutView view) {
        this.view = view;
    }

    public void setDevice(String deviceLabel) {
        this.deviceLabel = deviceLabel;
    }

    public List<MediaItemDesc> getQueue() {
        log.info("Queue requested for audio device " + deviceLabel + "...");
        DeviceAudioOutAccessRMI access = getDeviceAccess();

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

        ConductorAccessRMI access = getConductorAccess();
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

        updateNavItems("");
    }

    public void navigationItemSelected(String refItem)
    {
        log.info("Selected nav items '" + refItem+"'");
        updateNavItems(refItem);
    }

    private void updateNavItems(String refItem)
    {
        log.info("Updating nav items to '" + refItem+"'");
        ConductorAccessRMI access = getConductorAccess();

        try {
            List<MediaItemDesc> items=access.getNavItems(mediaSource, refItem);
            log.info("Received " + items.size() + " items from conductor");
            List<String> titles=new ArrayList<String>();
            Iterator<MediaItemDesc> it=items.iterator();
            currentMediaItems.clear();
            while(it.hasNext())
            {
                MediaItemDesc item=it.next();
                String title=item.getTitle();
                currentMediaItems.put(title,item);
                titles.add(title);
            }
            view.setNavigation(titles);
        }catch (RemoteException e) {
            e.printStackTrace();
        }catch(Exception e)
        {
            log.error("Could not reach conductor!\n"+e.fillInStackTrace());
            log.error(e.getMessage());
        }
    }

    public void addToQueue(String refItem)
    {
        log.info("Sending request to add "+refItem+" to queue...");
        DeviceAudioOutAccessRMI access=getDeviceAccess();

        try {
            access.addToQueue(currentMediaItems.get(refItem));
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    public void play(String item)
    {
        log.info("Received request to play "+item);
        try {
            getDeviceAccess().play(new MediaItemDesc(mediaSource,item));
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    private ConductorAccessRMI getConductorAccess()
    {
        ConductorAccessRMI access = ConductorRegistry.INSTANCE.getConductorAccessForDevice(deviceLabel);
        log.info("access to conductor: " + access);
        return access;
    }

    private DeviceAudioOutAccessRMI getDeviceAccess()
    {
        DeviceAudioOutAccessRMI access = ConductorRegistry.INSTANCE.getDeviceAudioOutAccess(deviceLabel);
        log.info("access to device: " + access);
        return access;
    }
}