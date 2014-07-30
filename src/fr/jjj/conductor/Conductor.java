package fr.jjj.conductor;


import fr.jjj.conductor.activity.Activity;
import fr.jjj.conductor.activity.media.ActivityMedia;


import java.util.HashMap;
import java.util.Map;

/**
 * Created by Jaunais on 26/06/2014.
 */
public class Conductor implements IConductor {

    Map<ActivityType,Activity> activities;

    protected Conductor() {
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
