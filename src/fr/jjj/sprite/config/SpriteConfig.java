package fr.jjj.sprite.config;

import com.google.common.io.Files;
import com.google.gson.Gson;
import com.google.gson.JsonStreamParser;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.charset.Charset;

/**
 * Created by Jaunais on 28/06/2014.
 */
public class SpriteConfig {

    private static final String CONFIG_FILE="config.json";

    private NetworkConfig networkConfig;

    private MediaActivityConfig mediaActivityConfig;

    public static SpriteConfig getConfig()
    {
        SpriteConfig config=null;
        File configFile=new File(CONFIG_FILE);
        try {
            String json=Files.toString(configFile, Charset.forName("UTF-8"));
            config=new Gson().fromJson(json,SpriteConfig.class);

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    return config;
    }

    public NetworkConfig getNetworkConfig() {
        return networkConfig;
    }

    public MediaActivityConfig getMediaActivityConfig() {
        return mediaActivityConfig;
    }
}
