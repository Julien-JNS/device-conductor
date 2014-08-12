package fr.jjj.conductormanager.model;

/**
 * Created by Jaunais on 12/08/2014.
 */
public class ConductorDesc {

    private String host;

    private int port;

    private String label;


    public ConductorDesc(String host, int port)
    {
        this.label=label;
        this.host=host;
        this.port=port;
    }

    public void setLabel(String label)
    {
        this.label=label;
    }

    public String getLabel() {
        return label;
    }

    public String getHost() {
        return host;
    }

    public int getPort() {
        return port;
    }
}
