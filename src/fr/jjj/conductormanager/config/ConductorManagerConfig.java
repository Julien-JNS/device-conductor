package fr.jjj.conductormanager.config;

import fr.jjj.conductor.config.ConductorConfig;
import fr.jjj.conductor.config.NetworkConfig;

import java.util.List;

/**
 * Created by Jaunais on 26/08/2014.
 */
public class ConductorManagerConfig {

    private List<NetworkConfig> networkConfigs;

    public List<NetworkConfig> getConductorConfigs() {
        return networkConfigs;
    }
}
