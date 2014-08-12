package fr.jjj.conductor.model;

import java.io.Serializable;

/**
 * Created by Jaunais on 05/08/2014.
 */
public abstract class Device implements Serializable{

    private String label;

    private String type;

    private String status;

    public Device(String label, String type)
    {
        this.label=label;
        this.type=type;
    }

    public String getLabel() {
        return label;
    }

    public String getType() {return type;}

    public String getStatus() {
        return status;
    }
}
