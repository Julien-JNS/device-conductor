package fr.jjj.conductor.access.rmi;

import fr.jjj.conductor.access.ActivityAccess;
import fr.jjj.conductor.activity.media.ActivityMedia;
import fr.jjj.conductor.activity.media.MediaDevice;
import fr.jjj.conductor.activity.media.MediaSource;

import java.io.Serializable;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

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
    public Collection<String> getMediaSourceList() throws RemoteException {
        Collection<String> labels=new ArrayList<String>();
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
    public Collection<String> getMediaDeviceList() throws RemoteException {
        Collection<String> labels=new ArrayList<String>();
        Iterator<MediaDevice> itDevices=((ActivityMedia)activity).getMediaDevices().iterator();
        while(itDevices.hasNext())
        {
            labels.add(itDevices.next().toString());
        }
        return labels;
    }
}
