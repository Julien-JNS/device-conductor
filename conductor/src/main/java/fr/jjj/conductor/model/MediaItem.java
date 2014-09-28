package fr.jjj.conductor.model;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Jaunais on 09/09/2014.
 */
public class MediaItem {

    static private Map<String,MediaItem> registry;

    private static final Log log= LogFactory.getLog(MediaItem.class);

    static
    {
        registry=new HashMap<String, MediaItem>();
    }

    private MediaItemDesc description;

    private Resource mediaSource;

    private String location;

    public MediaItem(MediaItemDesc description, Resource mediaSource, String location) {
        this.description = description;
        this.mediaSource=mediaSource;
        this.location=location;
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
        log.info("Match description for "+description.getTitle()+" (id "+description.getId()+")?");
        log.info("Registry conains key? "+registry.containsKey(description.getId()));
        return registry.get(description.getId());
    }
}
