package fr.jjj.conductor.model;

import java.io.Serializable;

/**
 * Created by Jaunais on 10/08/2014.
 */
public class DeviceDesc implements Serializable{

    public enum Command
    {
        PLAY,NEXT,PREV,PAUSE,STOP,VOLUP,VOLDOWN;
    }

    private String label;

    private String type;

    private String conductor;

    private String status;

    public DeviceDesc()
    {

    }

    public String getLabel()
    {
        return label;
    }

    public String getType()
    {
        return type;
    }

    public String getConductor()
    {
        return conductor;
    }

    public String getStatus()
    {
        return status;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setConductor(String conductor) {
        this.conductor = conductor;
    }

    public void setStatus(String status) {
        this.status = status;
    }

}
