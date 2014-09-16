package fr.jjj.conductor.model;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.io.IOException;

/**
 * Created by Jaunais on 02/09/2014.
 */
public class PlayerHandler {

    private Log log= LogFactory.getLog(this.getClass());

    private String playerCommand;

    private String defaultOptions;

    private Resource.ItemArgFormat itemArgFormat;

    public enum Action{
        VOLUP,VOLDOWN,NEXT,PREV,PAUSE,STOP;
    }

    public PlayerHandler(String playerCommand, String defaultOptions, Resource.ItemArgFormat itemArgFormat) {
        this.playerCommand = playerCommand;
        this.defaultOptions = defaultOptions;
        this.itemArgFormat=itemArgFormat;
    }

    public void play(MediaItem item)
    {
        String itemArg=item.getMediaSource().getItemArg(item.getDescription().getTitle(),itemArgFormat);
        String command=playerCommand+" "+defaultOptions+" \""+itemArg+"\"";
        log.info("Player command: "+command);
        try {
            Runtime.getRuntime().exec(command);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public void action(Action action)
    {
        switch(action)
        {
            case VOLDOWN:

                break;
            case VOLUP:
                break;
            case NEXT:
                break;
            case PREV:
                break;
            case PAUSE:

                break;
            case STOP:
                break;
            default:
                break;

        }
    }

}
