package fr.jjj.conductor.access;

import fr.jjj.conductor.Conductor;
import fr.jjj.conductor.access.rmi.ConductorAccessRMIImpl;
import fr.jjj.conductor.config.NetworkConfig;

/**
 * Created by Jaunais on 01/07/2014.
 */
public class AccessFactory {

    private NetworkConfig config;

    public AccessFactory(NetworkConfig networkConfig)
    {
        this.config=networkConfig;
    }

    public ConductorAccess getSpriteAccess(Conductor sprite)
    {
        return new ConductorAccessRMIImpl(sprite, config.getHost(),config.getPort());
    }
}
