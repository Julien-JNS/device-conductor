package fr.jjj.conductor.model;

import java.util.List;

/**
 * Created by Jaunais on 22/08/2014.
 */
public abstract class Resource {

    protected enum ItemArgFormat {
        NONE,URL;
    }

    private String label;

    public Resource(String label) {
        this.label = label;
    }

    public String getLabel() {
        return label;
    }

//    public List<MediaItem> getMediaItems(MediaItemDesc reference) {
//
//        return null;
//    }

    public abstract List<MediaItem> getMediaItems(MediaItem requestedItem);

    public String getItemArg(String item, ItemArgFormat format) {
        return null;
    }
}
