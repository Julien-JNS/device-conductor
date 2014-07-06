package fr.jjj.sprite.access.rmi;

import fr.jjj.sprite.access.ActivityAccess;
import fr.jjj.sprite.activity.media.ActivityMedia;
import fr.jjj.sprite.activity.media.MediaDevice;
import fr.jjj.sprite.activity.media.MediaSource;

import java.io.Serializable;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

/**
 * Created by Jaunais on 02/07/2014.
 */
public class MediaActivityAccess extends ActivityAccess implements Serializable,MediaActivityAccessI {


    public MediaActivityAccess()
    {

    }

    public MediaActivityAccess(ActivityMedia mediaActivity)
    {
        super(mediaActivity);
    }

    @Override
    public Collection<String> getMediaSourceList() throws RemoteException {
        Collection<String> labels=new ArrayList<String>();
        System.out.println("media activity="+activity);
        Iterator<MediaSource> itSources=((ActivityMedia)activity).getMediaSources().iterator();
        while(itSources.hasNext())
        {
            labels.add(itSources.next().getType());
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
