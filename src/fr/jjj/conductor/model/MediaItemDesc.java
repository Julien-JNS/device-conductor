package fr.jjj.conductor.model;

import java.io.Serializable;

/**
 * Created by Jaunais on 10/08/2014.
 */
public class MediaItemDesc implements Serializable{

    private String mediaSource;

    private String title;

    private String location;

    public MediaItemDesc(String mediaSource, String title) {
        this.mediaSource=mediaSource;
        this.title=title;

    }

    public String getTitle() {
        return title;
    }

    public String getMediaSource() {
        return mediaSource;
    }
}
