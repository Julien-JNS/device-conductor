package fr.jjj.conductor.model;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

/**
 * Created by Jaunais on 22/08/2014.
 */
public class ResourceFilesystem extends Resource{

    private Log log= LogFactory.getLog(this.getClass());

    private String defaultLocation;

    public ResourceFilesystem(String label, String defaultLocation)
    {
        super(label);
        this.defaultLocation=defaultLocation;
    }

    public String getDefaultLocation() {
        return defaultLocation;
    }

    public List<String> getNavItems(String reference)
    {
        String location=reference;
        if(location==null)
        {
            location=defaultLocation;
        }
        return getItemsInDir(location);
    }

    private  List<String> getItemsInDir(String directory)
    {
        log.info("Checking directory '"+directory+"'");
        List<String> items=new ArrayList<String>();
        if(directory!=null) {
            File dir = new File(directory);
            log.info("File dir="+dir);
            if (dir.isDirectory()) {
                items = Arrays.asList(dir.list());
                Iterator<String> it=items.iterator();
                while(it.hasNext()) {
                    log.info("Added '" + it.next() + "'");
                }
            }
        }
        return items;
    }
}
