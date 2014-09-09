package fr.jjj.conductor.model;

import java.util.Map;

/**
 * Created by Jaunais on 09/09/2014.
 */
public class MediaItem {

    static private Map<String,MediaItem> registry;

    private MediaItemDesc description;

    private Resource mediaSource;

    private String location;

    public MediaItem(MediaItemDesc description, Resource mediaSource, String location) {
        this.description = description;
    }

    public MediaItemDesc getDescription() {
        register(description, this);
        return description;
    }

    public Resource getMediaSource() {
        return mediaSource;
    }

    public String getLocation() {
        return location;
    }

    static private void register(MediaItemDesc desc, MediaItem item)
    {
        registry.put(desc.getId(),item);
    }

    static public MediaItem getMediaItem(MediaItemDesc description)
    {
        return registry.get(description.getId());
    }
}
