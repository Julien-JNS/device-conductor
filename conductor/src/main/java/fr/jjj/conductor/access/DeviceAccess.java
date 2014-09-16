package fr.jjj.conductor.access;

import fr.jjj.conductor.model.Device;

/**
 * Created by Jaunais on 10/08/2014.
 */
public abstract class DeviceAccess {


    protected Device device;

    public DeviceAccess(Device device)
    {
        this.device=device;
    }
}
