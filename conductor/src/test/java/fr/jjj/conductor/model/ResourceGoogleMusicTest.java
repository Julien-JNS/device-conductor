package fr.jjj.conductor.model;

import junit.framework.Assert;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.mockito.MockitoAnnotations;

import java.util.List;

/**
 * Created by Jaunais on 08/11/2014.
 */
public class ResourceGoogleMusicTest {

    private ResourceGoogleMusic resource;

    List<MediaItem> root;

    @Before
    public void initialize() {
        resource = new ResourceGoogleMusic("Google Music");

    }

    @Ignore
    @Test
    public void testGetRootLevel()
    {
        root=resource.getMediaItems(null);

        Assert.assertEquals("Size should be 2",2,root.size());
    }

    @Ignore
    @Test
    public void testGetPlaylists()
    {
        root=resource.getMediaItems(null);
        MediaItem playlistsItem=root.get(1);
        List<MediaItem> results=resource.getMediaItems(playlistsItem);

        Assert.assertTrue("Should not be empty", results != null && !results.isEmpty());
    }
}
