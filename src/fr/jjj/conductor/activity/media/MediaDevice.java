package fr.jjj.conductor.activity.media;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Jaunais on 01/07/2014.
 */
public class MediaDevice {

    enum Type {
        AUDIO,VIDEO,AUDIO_VIDEO;

        static private Map<String,Type> map;

        static private  Map<String,Type> getMap()
        {
            if(map==null)
            {
                map=new HashMap<String,Type>();
                map.put("audio",AUDIO);
                map.put("video",VIDEO);
                map.put("audio-video",AUDIO_VIDEO);
            }
            return map;
        }

        static public Type getType(String strType)
        {
            return getMap().get(strType);
        }

    }

    private Type type;

    private String label;

    public MediaDevice(String label, String type) {
        this.label=label;
        this.type=Type.getType(type);
    }
}
