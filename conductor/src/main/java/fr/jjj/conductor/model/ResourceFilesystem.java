package fr.jjj.conductor.model;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import javax.print.attribute.standard.Media;
import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

/**
 * Created by Jaunais on 22/08/2014.
 */
public class ResourceFilesystem extends Resource {

    private static long idCounter = 1;

    private Log log = LogFactory.getLog(this.getClass());

    private String defaultLocation;

    private String currentLocation;

    private List<String> currentItems;

    public ResourceFilesystem(String label, String defaultLocation) {
        super(label);
        this.defaultLocation = defaultLocation;
        currentLocation = defaultLocation;
    }

    public List<MediaItem> getMediaItems(MediaItem requestedItem) {
        List<MediaItem> items = new ArrayList<MediaItem>();
        String path;
        if (requestedItem == null) {
            path = defaultLocation;
        } else {
            path = currentLocation + "/" + requestedItem.getDescription().getTitle();
        }
        File testDir = new File(path);
        if (testDir.isDirectory()) {
            currentLocation = testDir.toPath().normalize().toString();
            log.info("Current directory is now: " + currentLocation);
            items = getMediaItems(testDir);

        }
        return items;
    }

    public String getDefaultLocation() {
        return defaultLocation;
    }

    private List<MediaItem> getMediaItems(File directory) {
        List<MediaItem> items = new ArrayList<MediaItem>();
        List<String> fileNames = getFileNamesInDir(directory);
        log.info("Found " + fileNames.size() + " items in " + directory.getAbsolutePath());
        Iterator<String> it = fileNames.iterator();
        while (it.hasNext()) {
            String fileName = it.next();
            log.info("name: " + currentLocation + "/" + fileName);
//            if(!new File(currentLocation+"/"+fileName).isDirectory()) {
            MediaItemDesc itemDescription = new MediaItemDesc(String.valueOf(idCounter++), fileName);
            MediaItem item = new MediaItem(itemDescription, this, currentLocation);
            log.info("Found: " + item.getDescription().getTitle());
            items.add(item);
//            }
        }
        return items;
    }

    private List<String> getFileNamesInDir(File directory) {
        log.info("Checking directory '" + directory + "'");

        if (directory != null && directory.isDirectory()) {

            currentItems = Arrays.asList(directory.list());
        }

        return currentItems;
    }

    @Override
    public String getItemArg(MediaItem item, ItemArgFormat format) {
        String itemArg = null;
        switch (format) {
            case URL:
                itemArg = currentLocation + "/" + item.getDescription().getTitle();
                break;
            case NONE:
                itemArg = "";
                break;
            default:
                break;

        }
        return itemArg;
    }
}
