package fr.jjj.conductor.model;

import java.io.Serializable;

/**
 * Created by Jaunais on 10/08/2014.
 */
public class MediaItemDesc implements Serializable{

    private String title;

    private String id;

    public MediaItemDesc() {
    }

    public MediaItemDesc(String id,String title) {
        this.title=title;
        this.id=id;
    }

    public String getTitle() {
        return title;
    }

    public String getId() {
        return id;
    }
}
