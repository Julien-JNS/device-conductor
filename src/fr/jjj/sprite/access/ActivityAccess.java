package fr.jjj.sprite.access;

import fr.jjj.sprite.activity.Activity;

import java.rmi.Remote;

/**
 * Created by Jaunais on 26/06/2014.
 */
public class ActivityAccess {

    protected Activity activity;

    protected ActivityAccess()
    {

    }

    protected ActivityAccess(Activity activity)
    {
        this.activity=activity;
    }
}
