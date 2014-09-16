package fr.jjj.conductor.config;

import com.google.common.io.Files;
import com.google.gson.Gson;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.Set;

/**
 * Created by Jaunais on 28/06/2014.
 */
public class ConductorConfig {

    private static final String CONFIG_FILE="config.json";

    private String label;

    private NetworkConfig networkConfig;

    private Set<DeviceConfig> devices;

    private Set<ResourceConfig> resources;

    //private MediaActivityConfig mediaActivityConfig;

    public static ConductorConfig getConfig()
    {
        ConductorConfig config=null;
        File configFile=new File(CONFIG_FILE);
        try {
            String json=Files.toString(configFile, Charset.forName("UTF-8"));
            config=new Gson().fromJson(json,ConductorConfig.class);

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    return config;
    }

    public String getLabel() {
        return label;
    }

    public NetworkConfig getNetworkConfig() {
        return networkConfig;
    }

    public Set<DeviceConfig> getDeviceConfigs() {
        return devices;
    }

    public Set<ResourceConfig> getResourceConfigs() {
        return resources;
    }
}
