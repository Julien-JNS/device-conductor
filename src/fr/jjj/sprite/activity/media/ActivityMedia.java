package fr.jjj.sprite.activity.media;


import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.Collection;

/**
 * Created by Jaunais on 26/06/2014.
 */
public class ActivityMedia extends fr.jjj.sprite.activity.Activity{


    private Collection<MediaSource> mediaSources;

    private Collection<MediaDevice> mediaDevices;


    public ActivityMedia()
    {
        mediaSources=new ArrayList<MediaSource>();
    }

    public ActivityMedia(Collection<MediaSource> mediaSources)
    {
        this.mediaSources=mediaSources;
    }

    public void addMediaSource(MediaSource mediaSource)
    {
        mediaSources.add(mediaSource);
    }

    public Collection<MediaSource> getMediaSources() {
        return mediaSources;
    }

    public Collection<MediaDevice> getMediaDevices() throws RemoteException {
        return mediaDevices;
    }
}
