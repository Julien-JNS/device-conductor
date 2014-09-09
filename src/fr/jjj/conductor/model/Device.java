package fr.jjj.conductor.model;

import fr.jjj.conductor.Conductor;

import java.io.Serializable;

/**
 * Created by Jaunais on 05/08/2014.
 */
public abstract class Device implements Serializable{

    private String label;

    private String type;

    private String status;

    protected Conductor conductor;

    public Device(Conductor conductor, String label, String type)
    {
        this.conductor=conductor;
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
