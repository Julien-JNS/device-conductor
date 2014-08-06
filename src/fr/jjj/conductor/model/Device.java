package fr.jjj.conductor.model;

import java.io.Serializable;

/**
 * Created by Jaunais on 05/08/2014.
 */
public abstract class Device implements Serializable{

    private String label;

    public Device(String label)
    {
        this.label=label;
    }

    public String getLabel() {
        return label;
    }
}
