package fr.jjj.conductor.activity.media;


import java.util.ArrayList;
import java.util.Collection;

/**
 * Created by Jaunais on 26/06/2014.
 */
public class ActivityMedia extends fr.jjj.conductor.activity.Activity{


    private Collection<MediaSource> mediaSources;

    private Collection<MediaDevice> mediaDevices;


    public ActivityMedia()
    {
        mediaSources=new ArrayList<MediaSource>();
        mediaDevices=new ArrayList<MediaDevice>();
    }

    public ActivityMedia(Collection<MediaSource> mediaSources)
    {
        this.mediaSources=mediaSources;
    }

    public void addMediaSource(MediaSource mediaSource)
    {
        mediaSources.add(mediaSource);
    }

    public void addMediaDevice(MediaDevice mediaDevice)
    {
        mediaDevices.add(mediaDevice);
    }

    public Collection<MediaSource> getMediaSources() {
        return mediaSources;
    }

    public Collection<MediaDevice> getMediaDevices() {
        return mediaDevices;
    }
}
