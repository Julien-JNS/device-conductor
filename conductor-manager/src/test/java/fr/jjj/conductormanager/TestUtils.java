package fr.jjj.conductormanager;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * Created with IntelliJ IDEA.
 * User: jjaunais
 * Date: 18/09/14
 * Time: 12:27
 * To change this template use File | Settings | File Templates.
 */
public class TestUtils {

    public static String CONDUCTOR_LABEL="RPI-1 (Salon)";

    public static final String[] DEVICE_LABELS = {"enceinte salon","webcam salon"};

    public static final String[] RESOURCE_LABELS = {"Musique from file system","Musique GOOGLE"};

    public static final String CONFIG_MANAGER = "[{'host': '127.0.0.1','port': '4056'}]";

    public static final String FILESYSTEM_START="./tmp";

    public static final String CONFIG = "{'label':'"+CONDUCTOR_LABEL+"','networkConfig': {'host': '127.0.0.1','port': '4056'}," +
            "'devices': " +
            "[{'type': 'audio-out','label':'" + DEVICE_LABELS[0] + "','bridge':'omxplayer'}," +
            "{'type': 'video-in','label':'" + DEVICE_LABELS[1] + "','bridge': 'xxx'}]," +
            "'resources':" +
            "[{'type': 'filesystem','label': '"+RESOURCE_LABELS[0]+"','start': '"+"./tmp"+"'}," +
            "{'type': 'googlemusic','label': '"+RESOURCE_LABELS[1]+"','start': 'Maryse'" +
            "}]}";

    static public void initializeManagerConfig() {
        // create config file
        File configFile = new File("./config-manager.json");
        FileOutputStream fos = null;
        try {
            fos = new FileOutputStream(configFile);

            fos.write(CONFIG_MANAGER.getBytes());

            fos.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    static public void initializeConfig() {
        // create config file
        File configFile = new File("./config.json");
        FileOutputStream fos = null;
        try {
            fos = new FileOutputStream(configFile);

            fos.write(CONFIG.getBytes());

            fos.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    static public void initializeFileSystem() {
        try {
            new File(TestUtils.FILESYSTEM_START).mkdirs();
            new File(TestUtils.FILESYSTEM_START+File.separator+"test.mp3").createNewFile();
            new File(TestUtils.FILESYSTEM_START+File.separator+"dir").mkdirs();
            new File(TestUtils.FILESYSTEM_START+File.separator+"dir"+File.separator+"test1.mp3").createNewFile();
            new File(TestUtils.FILESYSTEM_START+File.separator+"dir"+File.separator+"test2.mp3").createNewFile();
        } catch (IOException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
    }
}
