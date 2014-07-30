package fr.jjj.conductor;


import fr.jjj.conductor.activity.media.ActivityMedia;

/**
 * Created by Jaunais on 01/07/2014.
 */
public interface IConductor {

    enum ActivityType{
        MEDIA,CAM;
    }

    public ActivityMedia getActivityMedia();

}
