package fr.jjj.conductormanager.ui;

import fr.jjj.conductor.access.rmi.ConductorAccessRMI;
import fr.jjj.conductor.access.rmi.DeviceAudioOutAccessRMI;
import fr.jjj.conductor.model.DeviceDesc;
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

    public enum Command
    {
        PLAY,PREV,NEXT,PAUSE,STOP,VOLUP,VOLDOWN;
    }

    private Log log = LogFactory.getLog(this.getClass());

    private DeviceAudioOutView view;

    private String deviceLabel,mediaSource;

    private Map<String, MediaItemDesc> currentMediaItems=new HashMap<String, MediaItemDesc>();

    private Map<String, MediaItemDesc> currentMediaItemsInQueue=new HashMap<String, MediaItemDesc>();

    private List<MediaItemDesc> currentMediaPath=new ArrayList<MediaItemDesc>();

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
            currentMediaItemsInQueue.clear();
            for(MediaItemDesc mediaItemDesc:queue)
            {
                currentMediaItemsInQueue.put(mediaItemDesc.getTitle(),mediaItemDesc);
            }

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
        log.info("Media source '" + mediaSource + "' selected for audio device " + deviceLabel + "...");

        updateNavItems(null);
    }

    public void navigationItemSelected(String refItem)
    {
        log.info("Selected nav items '" + refItem+"'");
        MediaItemDesc refItemDesc=currentMediaItems.get(refItem);

        updateNavItems(refItemDesc);
        currentMediaPath.add(refItemDesc);
    }

    public void moveBackToParent()
    {
        log.info("Move back to parent...");
        MediaItemDesc parentDesc=null;
        if(currentMediaPath.size()>1) {
            currentMediaPath.remove(currentMediaPath.size() - 1);
            parentDesc=currentMediaPath.get(currentMediaPath.size() - 1);
        }
        updateNavItems(parentDesc);
    }

    private void updateNavItems(MediaItemDesc refItemDesc)
    {
        log.info("Updating nav items to '" + (refItemDesc==null?"":refItemDesc.getTitle()+"'"));

        ConductorAccessRMI access = getConductorAccess();

        try {
            List<MediaItemDesc> items=access.getNavItems(mediaSource, refItemDesc);
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
            MediaItemDesc mediaItemDesc = currentMediaItems.get(refItem);
            log.info("Item desc="+mediaItemDesc+" ("+mediaItemDesc.getTitle()+")");
            access.addToQueue(mediaItemDesc);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    public void play(String item)
    {
        log.info("Received request to play "+item);
        try {
            MediaItemDesc mediaItemDesc = currentMediaItemsInQueue.get(item);
            log.info("Item desc="+mediaItemDesc+" ("+mediaItemDesc.getTitle()+")");
            getDeviceAccess().play(mediaItemDesc);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    public void command(Command command)
    {
        log.info("Received command request "+command);
        try {

            getDeviceAccess().command(convertToServer(command));
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

    private DeviceDesc.Command convertToServer(Command command)
    {
        DeviceDesc.Command serverCommand=null;
        switch(command)
        {
            case VOLUP:
                serverCommand=DeviceDesc.Command.VOLUP;
                break;
            case VOLDOWN:
                serverCommand=DeviceDesc.Command.VOLDOWN;
                break;
            case PAUSE:
                serverCommand=DeviceDesc.Command.PAUSE;
                break;
            case NEXT:
                serverCommand=DeviceDesc.Command.NEXT;
                break;
            case PREV:
                serverCommand=DeviceDesc.Command.PREV;
                break;
            case STOP:
                serverCommand=DeviceDesc.Command.STOP;
                break;
        }
        return serverCommand;
    }

}
