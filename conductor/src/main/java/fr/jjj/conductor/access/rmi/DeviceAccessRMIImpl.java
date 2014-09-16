package fr.jjj.conductor.access.rmi;

import fr.jjj.conductor.access.DeviceAccess;
import fr.jjj.conductor.model.Device;

import java.rmi.RemoteException;

/**
 * Created by Jaunais on 10/08/2014.
 */
public class DeviceAccessRMIImpl extends DeviceAccess implements DeviceAccessRMI {


    public DeviceAccessRMIImpl(Device device) {
        super(device);
    }

    @Override
    public String getLabel() throws RemoteException {
        return device.getLabel();
    }

    @Override
    public String getType() throws RemoteException {
        return device.getType();
    }
}
