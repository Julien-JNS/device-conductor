package fr.jjj.sprite;


import fr.jjj.sprite.activity.Activity;
import fr.jjj.sprite.activity.media.ActivityMedia;


import java.util.HashMap;
import java.util.Map;

/**
 * Created by Jaunais on 26/06/2014.
 */
public class Sprite implements ISprite {

    Map<ActivityType,Activity> activities;

    protected Sprite() {
    activities=new HashMap<ActivityType, Activity>();
    }

    public void setActivity(ActivityType type, Activity activity)
    {
        activities.put(type,activity);
    }

    @Override
    public ActivityMedia getActivityMedia() {
        return (ActivityMedia)activities.get(ActivityType.MEDIA);
    }


}
