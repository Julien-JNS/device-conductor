package fr.jjj.conductor.model;

/**
 * Created by Jaunais on 05/08/2014.
 */
public class DeviceAudioOut extends Device {

    private String bridge;

    public DeviceAudioOut(String label,String bridge)
    {
        super(label);
    }

    public String getBridge() {
        return bridge;
    }
}
