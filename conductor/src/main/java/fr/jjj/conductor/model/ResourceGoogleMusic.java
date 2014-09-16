package fr.jjj.conductor.model;

import java.util.List;

/**
 * Created by Jaunais on 02/09/2014.
 */
public class ResourceGoogleMusic extends Resource{

    public ResourceGoogleMusic(String label) {
        super(label);
    }

    @Override
    public List<MediaItem> getMediaItems(MediaItem requestedItem) {
        return null;
    }
}
