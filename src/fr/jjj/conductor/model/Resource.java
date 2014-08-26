package fr.jjj.conductor.model;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Jaunais on 22/08/2014.
 */
public class Resource {

    private String label;

    public Resource(String label)
    {
        this.label=label;
    }

    public String getLabel() {
        return label;
    }

    public List<String> getNavItems(String reference)
    {
        List<String> items=new ArrayList<String>();
        return items;
    }

}
