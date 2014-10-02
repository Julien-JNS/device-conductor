package fr.jjj.conductor.model;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by Jaunais on 02/09/2014.
 */
public class PlayerHandler {

    private Log log= LogFactory.getLog(this.getClass());

    private String playerCommand;

    private String defaultOptions;

    private Resource.ItemArgFormat itemArgFormat;
    private OutputStream outputStream;

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
        List<String> command=new ArrayList<String>();
        command.add(playerCommand);
        command.addAll(Arrays.asList(defaultOptions.split(" ")));
        command.add(item.getMediaSource().getItemArg(item.getDescription().getTitle(),itemArgFormat));
        log.info("Player command: "+command.toArray());
        try {
            ProcessBuilder pb = new ProcessBuilder(command.toArray(new String[command.size()]));
            Process p = pb.start();
            outputStream = p.getOutputStream();
            final InputStream inputStream = p.getInputStream();
            new Thread(new Runnable(){
                @Override
                public void run() {
                    BufferedReader br = new BufferedReader(new InputStreamReader(inputStream));
                    String line = "";
                    try {
                        while ((line = br.readLine()) != null) {
                            log.info(line);
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }).start();
            p.waitFor();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
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
