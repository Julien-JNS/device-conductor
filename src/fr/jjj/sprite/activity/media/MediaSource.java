package fr.jjj.sprite.activity.media;

import java.io.Serializable;

/**
 * Created by Jaunais on 26/06/2014.
 */
public class MediaSource implements Serializable {

    private String type;

    public MediaSource(String type)
    {
this.type=type;
    }

    public String getType() {
        return type;
    }
}
