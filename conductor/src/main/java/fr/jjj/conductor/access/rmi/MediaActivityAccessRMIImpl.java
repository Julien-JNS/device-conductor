package fr.jjj.conductor.access.rmi;

import fr.jjj.conductor.access.ActivityAccess;
import fr.jjj.conductor.activity.media.ActivityMedia;
import fr.jjj.conductor.activity.media.MediaDevice;
import fr.jjj.conductor.activity.media.MediaSource;

import java.io.Serializable;
import java.rmi.RemoteException;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

/**
 * Created by Jaunais on 02/07/2014.
 */
public class MediaActivityAccessRMIImpl extends ActivityAccess implements Serializable,MediaActivityAccessRMI {


    public MediaActivityAccessRMIImpl()
    {

    }

    public MediaActivityAccessRMIImpl(ActivityMedia mediaActivity)
    {
        super(mediaActivity);
    }

    @Override
    public Set<String> getMediaSourceList() throws RemoteException {
        Set<String> labels=new HashSet<String>();
        System.out.println("media activity="+activity);
        if(activity!=null) {
            Iterator<MediaSource> itSources = ((ActivityMedia) activity).getMediaSources().iterator();
            while (itSources.hasNext()) {
                labels.add(itSources.next().getType());
            }
        }
        return labels;
    }

    @Override
    public Set<String> getMediaDeviceList() throws RemoteException {
        Set<String> labels=new HashSet<String>();
        Iterator<MediaDevice> itDevices=((ActivityMedia)activity).getMediaDevices().iterator();
        while(itDevices.hasNext())
        {
            labels.add(itDevices.next().toString());
        }
        return labels;
    }
}
