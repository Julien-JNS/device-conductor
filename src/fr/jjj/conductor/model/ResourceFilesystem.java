package fr.jjj.conductor.model;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.io.File;
import java.net.URI;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

/**
 * Created by Jaunais on 22/08/2014.
 */
public class ResourceFilesystem extends Resource{

    private static long idCounter=1;

    private Log log= LogFactory.getLog(this.getClass());

    private String defaultLocation;

    private String currentLocation;

    private List<String> currentItems;

    public ResourceFilesystem(String label, String defaultLocation)
    {
        super(label);
        this.defaultLocation=defaultLocation;
        currentLocation=defaultLocation;
    }

    public List<MediaItem> getMediaItems(MediaItem requestedItem)
    {
        List<MediaItem> items=new ArrayList<MediaItem>();
        File testDir=new File(currentLocation+"/"+requestedItem.getDescription().getTitle());
        if(testDir.isDirectory())
        {
            items=getMediaItems(requestedItem.getDescription().getTitle());
        }
        else
        {
            items.add(requestedItem);
        }
        return items;
    }

    public String getDefaultLocation() {
        return defaultLocation;
    }

    public List<MediaItem> getMediaItems(String reference)
    {
        List<MediaItem> items=new ArrayList<MediaItem>();
        String location=reference;
        if(location==null)
        {
            location="";
        }
        List<String> fileNames=getFileNamesInDir(location);
        log.info("Found "+fileNames.size()+" items in "+location);
        Iterator<String> it=fileNames.iterator();
        while(it.hasNext())
        {
            String fileName=it.next();
            log.info("name: "+currentLocation+"/"+fileName);
//            if(!new File(currentLocation+"/"+fileName).isDirectory()) {
                MediaItemDesc itemDescription = new MediaItemDesc(String.valueOf(idCounter++), fileName);
                MediaItem item = new MediaItem(itemDescription, this, location);
                log.info("Found: "+item.getDescription().getTitle());
                items.add(item);
//            }
        }
        return items;
    }

    private  List<String> getFileNamesInDir(String directory)
    {
        log.info("Checking directory '"+directory+"' in "+currentLocation);

        if(directory!=null) {
            String newLocation=currentLocation+"/"+directory;
            File dir = new File(newLocation);
            log.info("File dir="+newLocation);
            if (dir.isDirectory()) {
                currentLocation=dir.toPath().normalize().toString();
                log.info("Current directory is now: "+currentLocation);
                currentItems = Arrays.asList(dir.list());
            }
        }
        return currentItems;
    }

    @Override
    public String getItemArg(String item, ItemArgFormat format) {
        String itemArg = null;
        switch (format) {
            case URL:
                itemArg=currentLocation+"/"+item;
                break;
            default:
                break;

        }
        return itemArg;
    }
}
