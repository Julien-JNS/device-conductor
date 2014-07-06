package fr.jjj.sprite;


import fr.jjj.sprite.activity.media.ActivityMedia;

/**
 * Created by Jaunais on 01/07/2014.
 */
public interface ISprite {

    enum ActivityType{
        MEDIA,CAM;
    }

    public ActivityMedia getActivityMedia();

}
