package fr.jjj.conductor.model;

import java.io.Serializable;

/**
 * Created by Jaunais on 10/08/2014.
 */
public class MediaItemDesc implements Serializable{

    private String title;

    private String location;

    public MediaItemDesc(String title) {
        this.title=title;

    }

    public String getTitle() {
        return title;
    }
}
